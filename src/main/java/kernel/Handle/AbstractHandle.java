package kernel.Handle;

import kernel.Http.Constants;
import kernel.Http.HttpContext;
import kernel.Http.HttpRequest;
import kernel.Http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
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

}
