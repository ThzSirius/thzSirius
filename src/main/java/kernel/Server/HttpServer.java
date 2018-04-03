package kernel.Server;

import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;


public class HttpServer  extends Server{

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private Selector selector;

    public HttpServer() throws Exception {
        super();
    }

    @Override
    public String getServerName() {
        return "Http";
    }

    @Override
    protected void init() throws Exception {
        selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(8080));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector,SelectionKey.OP_ACCEPT);
    }


    @Override
    public void start() throws Exception {
        while (true){

        }
    }
}
