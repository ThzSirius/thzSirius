package kernel.Server;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


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
            if(selector.selectNow() == 0)
                continue;
            try {
               Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
               while (ite.hasNext()){
                   SelectionKey key = ite.next();
                   ite.remove();
                   if(key.isAcceptable()){
                       //todo
                   }else if(key.isReadable()){
                       //todo
                   }else if(key.isWritable()){
                       //todo
                   }
               }
            }catch (IOException e){
                logger.error("Handle request error");
            }
        }
    }



    private  void acceptKey(SelectionKey key) throws IOException {
         SocketChannel channel = (SocketChannel) key.channel();

    }



}
