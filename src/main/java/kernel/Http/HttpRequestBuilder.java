package kernel.Http;

import org.apache.commons.fileupload.FileUploadException;

import java.io.IOException;
import java.net.URLDecoder;

public class HttpRequestBuilder implements RequestBuilder {

    private AbstractHttpReader httpReader;
    private HttpRequest request;

    public HttpRequestBuilder(AbstractHttpReader readerFactory){
        this.httpReader = readerFactory;
    }


    @Override
    public HttpRequest buildUrl() throws IOException {
        String startLine = httpReader.readLine();
        String[] startLineArr = startLine.split(" ");
        if(startLineArr.length<3){
            return null;
        }
        String url = URLDecoder.decode(startLineArr[1],"utf-8");
        String[] pathArr = url.split("\\?");
        String path = pathArr[0];
        request = new HttpRequest();

        return null;
    }

    @Override
    public HttpRequest buildHead() throws IOException {
        return null;
    }

    @Override
    public HttpRequest buildBody() throws FileUploadException, IOException {
        return null;
    }
}
