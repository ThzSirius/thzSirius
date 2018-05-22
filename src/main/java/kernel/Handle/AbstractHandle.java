package kernel.Handle;

import kernel.Http.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.Cookie;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;


public abstract  class AbstractHandle implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(AbstractHandle.class);
    protected HttpRequest request;
    protected HttpResponse response;

    private boolean pathVariable = false;

    protected  abstract void respond();

    @Override
    public void run() {
        if(request == null){
            return;
        }
        response = new HttpResponse();
        response.setProtocol(request.getProtocol());
        checkSession();

        try {
            Object returnData;
            if (request.getPath().equals("/")) {
                returnData = getProjectResourceWithHandleContentType("/" + FastRestApplication.getServerConfig().getIndexPage(), true);
            } else if (request.getPath().equals("/favicon.ico")) {
                returnData = getProjectResourceWithHandleContentType(request.getPath(), true);
            } else if (StringUtils.isNoneBlank(FastRestApplication.getStaticPath()) && request.getPath().startsWith("/static/")) {
                returnData = Resources.getResourceAsStreamByStatic(request.getPath().substring(7));
                handleContentType(request.getPath());
            } else {
                Method method = getMethodByRequestPath(request.getPath());
                if (method != null) {
                    try {
                        returnData = getInvokeResult(method);
                    } catch (InvocationTargetException ite) {
                        throw ite.getTargetException();
                    }
                } else {
                    returnData = getProjectResourceWithHandleContentType(request.getPath(), false);
                }
            }
            response.setResult(returnData);
        } catch (FileNotFoundException e) {
            response.setStatus(HttpStatus.NotFound);
        } catch (RequestNotAllowException e) {
            response.setStatus(HttpStatus.MethodNotAllowed);
        } catch (Throwable e) {
            logger.error("Request action error", e);
            response.setStatus(HttpStatus.InternalServerError);
        } finally {
            respond();
        }

    }

    private void checkSession(){
        if(request.getRequestedSessionId() == null){
            String sessionId = UUID.randomUUID().toString();
            Cookie sessionCookie = new Cookie(Constants.FAST_REST_SESSION, sessionId);
            sessionCookie.setMaxAge(Long.valueOf(7200000L/1000).intValue());
            sessionCookie.setSecure(false);
            sessionCookie.setPath("/");
            request.addCookie(sessionCookie);
            response.addCookie(sessionCookie);
        }
        HttpContext.refreshSession(request.getRequestedSessionId());
    }

    private Object getProjectResourceWithHandleContentType(String path,boolean findDefault) throws FileNotFoundException{
        InputStream inputStream = null;
        inputStream =
    }


}
