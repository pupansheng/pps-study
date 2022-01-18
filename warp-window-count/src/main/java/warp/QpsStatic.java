/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package warp;



import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pu PanSheng, 2021/7/28
 * @version OPRA v1.0
 */
public class QpsStatic{


    private AtomicReferenceArray<Node> nodes;
    long windowLengthInMs;

    private ReentrantLock updateLock=new ReentrantLock();
    Long perid;
    long timeRange;


    public void click(int type){

        Node node = currentWindow(TimeUtil.currentTimeMillis(),type);
        node.getCountTyps()[type].addAndGet(1);

    }

    /**
     * 统计当前时间的qps  把时间窗加起来 就是
     * @param timeMillis
     * @return
     */
    public int staticQps(long timeMillis,int type){

        if (timeMillis < 0) {
            return 0;
        }
        int size = nodes.length();
        int count=0;

        for (int i = 0; i < size; i++) {
            Node windowWrap = nodes.get(i);
            if (windowWrap == null || isWindowDeprecated(timeMillis, windowWrap)) {
                continue;
            }
            count+=windowWrap.getCountTyps()[type].get();
        }
        return count;
    }
    public boolean isWindowDeprecated(long time, Node windowWrap) {
        return time - windowWrap.getStart() > timeRange;
    }
    /**
     *
     * @param timeRange 时间范围         1    1     1    1
     * @param interminal 滑动时间窗范围  1    1
     */
    public QpsStatic(long timeRange,long interminal){
        Long perid=timeRange/interminal;
        this.perid=perid;
        this.timeRange=timeRange;
        windowLengthInMs=interminal;//时间窗宽度
        nodes=new AtomicReferenceArray<>(perid.intValue());
    }

    /**
     * 找起点 找当前时间对应的时间区间的起点
     * @param timeMillis
     * @return
     */
    protected long calculateWindowStart(/*@Valid*/ long timeMillis) {
        return timeMillis - timeMillis % windowLengthInMs;
    }
    private int calculateTimeIdx(/*@Valid*/ long timeMillis) {
        long timeId = timeMillis / windowLengthInMs;
        // Calculate current index so we can map the timestamp to the leap array.
        return (int)(timeId % nodes.length());
    }
    public Node currentWindow(long timeMillis,int type) {
        if (timeMillis < 0) {
            return null;
        }

        int idx = calculateTimeIdx(timeMillis);
        // Calculate current bucket start time.
        long windowStart = calculateWindowStart(timeMillis);

        /*
         * Get bucket item at given time from the array.
         *
         * (1) Bucket is absent, then just create a new bucket and CAS update to circular array.
         * (2) Bucket is up-to-date, then just return the bucket.
         * (3) Bucket is deprecated, then reset current bucket and clean all deprecated buckets.
         */
        while (true) {
            Node old = nodes.get(idx);
            if (old == null) {
                /*
                 *     B0       B1      B2    NULL      B4
                 * ||_______|_______|_______|_______|_______||___
                 * 200     400     600     800     1000    1200  timestamp
                 *                             ^
                 *                          time=888
                 *            bucket is empty, so create new and update
                 *
                 * If the old bucket is absent, then we create a new bucket at {@code windowStart},
                 * then try to update circular array via a CAS operation. Only one thread can
                 * succeed to update, while other threads yield its time slice.
                 */
                Node node=new Node();
                node.setStart(windowStart);
                if (nodes.compareAndSet(idx, null, node)) {
                    // Successfully updated, return the created bucket.
                    return node;
                } else {
                    // Contention failed, the thread will yield its time slice to wait for bucket available.
                    Thread.yield();
                }
            } else if (windowStart == old.getStart()) {
                /*
                 *     B0       B1      B2     B3      B4
                 * ||_______|_______|_______|_______|_______||___
                 * 200     400     600     800     1000    1200  timestamp
                 *                             ^
                 *                          time=888
                 *            startTime of Bucket 3: 800, so it's up-to-date
                 *
                 * If current {@code windowStart} is equal to the start timestamp of old bucket,
                 * that means the time is within the bucket, so directly return the bucket.
                 */
                return old;
            } else if (windowStart > old.getStart()) {
                /*
                 *   (old)
                 *             B0       B1      B2    NULL      B4
                 * |_______||_______|_______|_______|_______|_______||___
                 * ...    1200     1400    1600    1800    2000    2200  timestamp
                 *                              ^
                 *                           time=1676
                 *          startTime of Bucket 2: 400, deprecated, should be reset
                 *如果旧桶的开始时间戳落后于提供的时间，
                 * 则意味着 * 该桶已被弃用。我们必须将存储桶重置为当前的 {@code windowStart}。
                 * 注意reset 和clean-up 操作很难是原子的， * 所以我们需要一个update lock 来保证bucket update 的正确性。
                 * 更新锁是有条件的（小范围），只有当桶被弃用时才会生效，所以在大多数情况下它不会导致性能损失
                 *
                 * If the start timestamp of old bucket is behind provided time, that means
                 * the bucket is deprecated. We have to reset the bucket to current {@code windowStart}.
                 * Note that the reset and clean-up operations are hard to be atomic,
                 * so we need a update lock to guarantee the correctness of bucket update.
                 *
                 * The update lock is conditional (tiny scope) and will take effect only when
                 * bucket is deprecated, so in most cases it won't lead to performance loss.
                 */
                if (updateLock.tryLock()) {
                    try {
                        // Successfully get the update lock, now we reset the bucket.
                        old.setStart(windowStart);
                        old.getCountTyps()[type].set(0);
                        return  old;
                    } finally {
                        updateLock.unlock();
                    }
                } else {
                    // Contention failed, the thread will yield its time slice to wait for bucket available.
                    Thread.yield();
                }
            } else if (windowStart < old.getStart()) {
                // Should not go through here, as the provided time is already behind.
                Node node=new Node();
                node.setStart(windowStart);

                return  node;
            }
        }
    }

}
