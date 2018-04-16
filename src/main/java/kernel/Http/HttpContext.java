package kernel.Http;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpContext {
    private static Map<String, Object> attributes = new ConcurrentHashMap<>();
    private static ThreadLocal<Connection> transaction = new ThreadLocal<>();
    private static ThreadLocal<HttpRequest> request = new ThreadLocal<>();
    private static ThreadLocal<HttpResponse> response = new ThreadLocal<>();
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static Object getAttribute(String s) {
        return attributes.get(s);
    }

    public static void setAttribute(String s, Object o) {
        attributes.put(s, o);
    }

    public static void removeAttribute(String s) {
        attributes.remove(s);
    }

    public static Connection getTransaction() {
        return transaction.get();
    }

    public static void setTransaction(Connection connection) {
        transaction.set(connection);
    }

    public static HttpRequest getRequest() {
        return request.get();
    }

    public static void setRequest(HttpRequest request) {
        HttpContext.request.set(request);
    }

    public static HttpResponse getResponse() {
        return response.get();
    }

    public static void setResponse(HttpResponse response) {
        HttpContext.response.set(response);
    }

    public static Session getSession(String sessionId) {
        return sessions.computeIfAbsent(sessionId, Session::new);
    }

    public static Map<String, Session> getSessions() {
        return sessions;
    }

    public static void refreshSession(String sessionId) {
        if (sessions.get(sessionId) != null) {
            sessions.get(sessionId).setLastAccessedTime(System.currentTimeMillis());
        }
    }

    private HttpContext() {

    }
}
