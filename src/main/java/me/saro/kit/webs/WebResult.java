package me.saro.kit.webs;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * web result
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public class WebResult {

    WebResult() {
    }

    // http status
    private int status = -1;

    // exception
    private Exception exception;

    // headers
    private Map<String, List<String>> headers = Collections.emptyMap();

    // response body data
    private String body;

    /**
     * is status 2xx + have not exception
     * @return
     */
    public boolean isSuccess() {
        return isStatus2xx() && exception == null;
    }
    
    /**
     * is status 2xx
     * @return
     */
    public boolean isStatus2xx() {
        return status >= 200 && status < 300;
    }

    /**
     * is status 3xx
     * @return
     */
    public boolean isStatus3xx() {
        return status >= 300 && status < 400;
    }

    /**
     * is status 4xx
     * @return
     */
    public boolean isStatus4xx() {
        return status >= 400 && status < 500;
    }

    /**
     * is status 5xx
     * @return
     */
    public boolean isStatus5xx() {
        return status >= 500 && status < 600;
    }
    
    /**
     * has body
     * @return
     */
    public boolean hasBody() {
        return body != null;
    }

    public Optional<String> getBody() {
        return Optional.ofNullable(body);
    }

    public String getBody(String orElse) {
        return body != null ? body : orElse;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "WebResult{" +
                "status=" + status +
                ", exception=" + exception +
                ", headers=" + headers +
                ", body=" + body +
                '}';
    }
}