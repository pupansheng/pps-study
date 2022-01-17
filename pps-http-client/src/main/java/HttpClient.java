import util.BufferUtil;
import util.HttpUtil;
import util.PpsInputSteram;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author Pu PanSheng, 2022/1/17
 * @version v1.0
 */
public class HttpClient {

    private Selector[] selectors;

    private int parallel = 1;

    private AtomicLong counter = new AtomicLong();

    private ExecutorService executorService;

    private ConcurrentHashMap<SocketChannel, PpsSocketChannel> map = new ConcurrentHashMap<>();

    public HttpClient(int parallel) {

        try {
            this.parallel = parallel;
            executorService = Executors.newFixedThreadPool(parallel);
            selectors = new Selector[parallel];
            for (int i = 0; i < parallel; i++) {
                selectors[i] = Selector.open();
            }

            for (int i = 0; i < parallel; i++) {

                int finalI = i;
                executorService.submit(() -> {

                    while (true) {


                        try {

                            int v = selectors[finalI].select(500);


                            if (v <= 0) {
                                continue;
                            }


                            Set<SelectionKey> selectionKeys = selectors[finalI].selectedKeys();

                            Iterator<SelectionKey> iterator = selectionKeys.iterator();

                            while (iterator.hasNext()) {

                                SelectionKey next = iterator.next();

                                if (!next.isReadable()) {
                                    iterator.remove();
                                    break;
                                }

                                SelectableChannel channel = next.channel();
                                PpsSocketChannel channelPps = map.get(channel);
                                PpsInputSteram ppsInputSteram = new PpsInputSteram(channelPps.getSocketChannel(), next);
                                //读取数据 触发回调
                                byte[] buffer = new byte[1024];

                                int count = 0;

                                while (true) {

                                    int read = ppsInputSteram.read();

                                    if (read <= -1000) {

                                        //异常关闭
                                        if (read == -1001) {
                                            if(count==0){
                                                break;
                                            }
                                        }

                                        channelPps.addContent(buffer);

                                        //判度是否结束
                                        if(true){

                                            if (channelPps.getContent().isEmpty()) {
                                                break;
                                            }
                                            Response response = new Response();
                                            response.setContent(BufferUtil.listToArray(channelPps.getContent()));
                                            channelPps.exeCallback(response);
                                            break;

                                        }


                                    }

                                    buffer[count++] = (byte) read;

                                    //扩容
                                    if (count >= buffer.length) {
                                        byte[] newLine = new byte[buffer.length * 2];
                                        System.arraycopy(count, 0, newLine, 0, count);
                                        buffer = newLine;
                                    }

                                }



                                iterator.remove();

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }


                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void execute(Request request, Consumer<Response> consumer) {
        try {
            long l = counter.addAndGet(1);
            Long index = l % parallel;
            Selector selector = selectors[index.intValue()];
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(request.getIp(), request.getPort()));
            PpsSocketChannel ppsSocketChannel = new PpsSocketChannel(socketChannel, consumer);

            map.put(socketChannel, ppsSocketChannel);

            ppsSocketChannel.configureBlocking(false);
            ppsSocketChannel.register(selector, SelectionKey.OP_READ);
            BufferUtil.write(request.getBody(), (byteBuffer) -> {
                try {
                    socketChannel.write(byteBuffer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
