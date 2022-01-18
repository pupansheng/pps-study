package core;

import task.DataReadStrategy;
import task.DefaultReadStrategy;
import task.SocketTask;
import util.BufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author Pu PanSheng, 2022/1/17
 * @version v1.0
 */
public class DataClient {

    private Selector[] selectors;

    private int parallel = 1;

    private AtomicLong counter = new AtomicLong();

    private ExecutorService executorService;

    private ConcurrentHashMap<SocketChannel, Context> contextMap = new ConcurrentHashMap<>();

    private DataReadStrategy dataReadStrategy=new DefaultReadStrategy();

    public DataClient(int parallel) {

        try {
            this.parallel = parallel;
            executorService = Executors.newFixedThreadPool(parallel);
            selectors = new Selector[parallel];
            for (int i = 0; i < parallel; i++) {
                selectors[i] = Selector.open();
            }
            for (int i = 0; i < parallel; i++) {
                Selector selector=selectors[i];
                executorService.submit(new SocketTask(selector, contextMap, dataReadStrategy));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public void execute(Request request, Consumer<Response> consumer) {

        SocketChannel socketChannel=null;
        try {

            long l = counter.addAndGet(1);
            Long index = l % parallel;
            Selector selector = selectors[index.intValue()];
            socketChannel = SocketChannel.open(new InetSocketAddress(request.getIp(), request.getPort()));
            Context context=new PpsContext(socketChannel, consumer);
            contextMap.put(socketChannel,  context);
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);

            SocketChannel finalSocketChannel = socketChannel;
            BufferUtil.write(request.getBody(), (byteBuffer) -> {
                try {
                    finalSocketChannel.write(byteBuffer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });


        } catch (IOException e) {
            contextMap.remove(socketChannel);
            throw new RuntimeException(e);
        }
    }


}
