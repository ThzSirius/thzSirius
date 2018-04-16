package kernel.Http;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.commons.fileupload.FileUploadException;

import java.io.IOException;

public interface RequestBuilder {
    HttpRequest buildUrl() throws IOException;

    HttpRequest buildHead() throws IOException;

    HttpRequest buildBody() throws FileUploadException,IOException;

}
