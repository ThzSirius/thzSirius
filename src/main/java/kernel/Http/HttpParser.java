package kernel.Http;

import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class HttpParser {
    private  static  final org.slf4j.Logger logger = LoggerFactory.getLogger(HttpParser.class);

    public static HttpRequest generateHttpRequest(Socket socket){
            try {

                InputStream inputStream = socket.getInputStream();
                  new HttpReader(inputStream);

            }catch (IOException e){
                logger.error("Create request error", e);
            }
    }

    public static HttpRequest createRequest(SocketChannel channel){
            return directorRequest()
    }


    private static HttpRequest directorRequest(AbstractHttpReader readerFactory){

    }


}