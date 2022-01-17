import java.io.IOException;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Pu PanSheng, 2022/1/17
 * @version v1.0
 */
public class PpsSocketChannel extends SelectableChannel {

    private SocketChannel socketChannel;


    private Consumer<Response> consumer;

    private List<byte[]> content=new ArrayList<>();

    public List<byte[]> getContent() {
        return content;
    }

    public void addContent(byte[] bytes){
        content.add(bytes);
    }
    public void setContent(List<byte[]> content) {
        this.content = content;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public Consumer<Response> getConsumer() {
        return consumer;
    }

    public PpsSocketChannel(SocketChannel socketChannel, Consumer<Response> consumer) {
        this.socketChannel = socketChannel;
        this.consumer = consumer;
    }

    public void exeCallback(Response response){
        System.out.println("执行回调");
        if(response.getContent()!=null) {
            consumer.accept(response);
        }
    }
    @Override
    public SelectorProvider provider() {
        return socketChannel.provider();
    }

    @Override
    public int validOps() {
        return socketChannel.validOps();
    }

    @Override
    public boolean isRegistered() {
        return socketChannel.isRegistered();
    }

    @Override
    public SelectionKey keyFor(Selector sel) {
        return socketChannel.keyFor(sel);
    }

    @Override
    public SelectionKey register(Selector sel, int ops, Object att) throws ClosedChannelException {
        return socketChannel.register(sel,ops,att);
    }

    @Override
    public SelectableChannel configureBlocking(boolean block) throws IOException {
        return socketChannel.configureBlocking(block);
    }

    @Override
    public boolean isBlocking() {
        return socketChannel.isBlocking();
    }

    @Override
    public Object blockingLock() {
        return socketChannel.blockingLock();
    }

    @Override
    protected void implCloseChannel() throws IOException {

    }
}
