package kernel.Http;

import kernel.Common.MultipartFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.List;

public class HttpRequestBuilder implements RequestBuilder {

    private AbstractHttpReader httpReader;
    private HttpRequest request;

    public HttpRequestBuilder(AbstractHttpReader readerFactory) {
        this.httpReader = readerFactory;
    }


    @Override
    public HttpRequest buildUrl() throws IOException {
        String startLine = httpReader.readLine();
        String[] startLineArr = startLine.split(" ");
        if (startLineArr.length < 3) {
            return null;
        }
        String url = URLDecoder.decode(startLineArr[1], "utf-8");
        String[] pathArr = url.split("\\?");
        String path = pathArr[0];
        request = new HttpRequest();
        request.setMethod(startLineArr[0]);
        request.setProtocol(startLineArr[2]);
        request.setPath(path);
        if (pathArr.length > 1) {
            String[] pathParameter = pathArr[1].split("&");
            for (String param : pathParameter) {
                String[] keyValue = param.split("=");
                request.putParameter(keyValue[0], keyValue.length > 1 ? keyValue[1] : "");

            }
        }
        return request;
    }

    @Override
    public HttpRequest buildHead() throws IOException {
        String headLine = httpReader.readLine();
        while (!headLine.trim().equals("")) {
            if (headLine.startsWith("Cookie")) {
                String cookieString = headLine.substring(headLine.indexOf(':') + 2);
                String[] cookieArr = cookieString.split(";");

                for (String cookie : cookieArr) {
                    String[] keyValue = cookie.split("=");
                    request.addCookie(new Cookie(keyValue[0].trim(), keyValue[1].trim());
                }
            } else {
                String headKey = headLine.substring(0, headLine.indexOf(':'));
                String headvalue = headLine.substring(headLine.indexOf(':') + 2);
                request.putHeader(headKey, headvalue);
            }
            headLine = httpReader.readLine();
        }
        return request;
    }

    @Override
    public HttpRequest buildBody() throws FileUploadException, IOException {
        if (request.getMethod().equals(RequestType.POST) || request.getMethod().equals(RequestType.DELETE) || request.getMethod().equals(RequestType.PUT)) {
            String contentType = request.getHeader(HttpHeaderType.CONTENT_TYPE);
            int contentLength = Integer.parseInt(request.getHeader(HttpHeaderType.CONTENT_LENGTH).trim());
            handleRequestBody(contentType, contentLength);
        }
        return request;
    }

    private void handleRequestBody(String contentType, int contentLength) throws FileUploadException, IOException {
        if (contentType.startsWith("multipart/form-data")) {
            InputStream inputStream = httpReader.readFileInputStream(contentLength);
            MultiFileRequest fileRequest = new MultiFileRequest(inputStream, contentType, contentLength);
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> fileItems = upload.parseRequest(fileRequest);
            fileItems.forEach(fileItem -> {
                request.addMultipartFile(new MultipartFile(fileItem));
            });
            return;
        }

        String bodyContext = httpReader.readLimitSize(contentLength);
        if (contentType.startsWith("application/x-www-form-urlencoded")) {
            String[] paramStringArr = bodyContext.split("&");
            for (String paramString : paramStringArr) {
                String[] param = paramString.split("=");
                request.setAttribute(param[0], param[1]);
            }
        } else if (contentType.startsWith("text/plain") || contentType.startsWith("application/json")) {
            request.setRequestContext(bodyContext);
        }

    }


}
