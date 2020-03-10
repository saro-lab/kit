package me.saro.kit.webs;

import me.saro.kit.Streams;
import me.saro.kit.Texts;

/**
 * Web Client
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public interface Web {
    /**
     * create get method Web
     * @param url
     * @return
     */
    static Web get(String url) {
        return new WebImpl(url, "GET");
    }

    /**
     * create post method Web
     * @param url
     * @return
     */
    static Web post(String url) {
        return new WebImpl(url, "POST");
    }

    /**
     * create put method Web
     * @param url
     * @return
     */
    static Web put(String url) {
        return new WebImpl(url, "PUT");
    }

    /**
     * create patch method Web
     * @param url
     * @return
     */
    static Web patch(String url) {
        return new WebImpl(url, "PATCH");
    }

    /**
     * create delete method Web
     * @param url
     * @return
     */
    static Web delete(String url) {
        return new WebImpl(url, "DELETE");
    }

    /**
     * apply global ignore certificate
     * @return is success
     */
    static boolean applyGlobalIgnoreCertificate() {
        try {
            WebIgnoreCertificate.applyGlobalIgnoreCertificate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * request charset
     * @return
     */
    String getRequestCharset();
    
    /**
     * response charset
     * @return
     */
    String getResponseCharset();

    /**
     * create custom method Web
     * @param url
     * @return
     */
    static Web custom(String url, String method) {
        return new WebImpl(url, method);
    }
    
    /**
     * Connect Timeout
     * @param connectTimeout
     * @return
     */
    Web setConnectTimeout(int connectTimeout);
    
    /**
     * Read Timeout
     * @param readTimeout
     * @return
     */
    Web setReadTimeout(int readTimeout);
    
    /**
     * set request Charset
     * @param charset
     * @return
     */
    Web setRequestCharset(String charset);
    
    /**
     * set response charset
     * @param charset
     * @return
     */
    Web setResponseCharset(String charset);
    
    /**
     * ignore https certificate
     * <br>
     * this method not recommend
     * <br>
     * ignore certificate is defenseless the MITM(man-in-the-middle attack)
     * @param ignoreCertificate
     * @return
     */
    Web setIgnoreCertificate(boolean ignoreCertificate);
    
    /**
     * add url parameter
     * <br>
     * always append url parameter even post method
     * <br>
     * is not body write
     * @param name
     * @param value
     * @return
     */
    Web addUrlParameter(String name, String value);
    
    /**
     * set header
     * @param name
     * @param value
     * @return
     */
    Web setHeader(String name, String value);
    
    /**
     * write body binary
     * @param bytes
     * @return
     */
    Web writeBody(byte[] bytes);
    
    /**
     * writeBodyParameter
     * <br>
     * <b>WARNING : </b> is not json type
     * <br>
     * <br>
     * web
     * <br>
     *  .writeBodyParameter("aa", "11")
     * <br>
     * .writeBodyParameter("bb", "22");
     * <br>
     * <b>equals</b>
     * <br>
     * aa=11&amp;bb=22
     * @param name
     * @param value
     * @return
     */
    Web writeBodyParameter(String name, String value);

    /**
     * bind result
     * @param reader
     */
    void result(WebReader reader) throws Exception;

    /**
     * get result
     * @return
     */
    default WebResult result() {
        final WebResult result = new WebResult();

        try {
            result((status, headers, is) -> {
                result.setStatus(status);
                result.setHeaders(headers);
                result.setBody(Streams.toString(is, getResponseCharset()));
            });
        } catch (Exception e) {
            result.setException(e);
        }

        return result;
    }
    
    /**
     * set header ContentType
     * @param value
     * @return
     */
    default Web setContentType(String value) {
        return setHeader("Content-Type", value);
    }
    
    /**
     * ContentType application/json
     * @return
     */
    default Web setContentTypeApplicationJson() {
        return setHeader("Content-Type", "application/json");
    }
    
    /**
     * write Body text
     * @param text
     * @return
     */
    default Web writeBody(String text) {
        return writeBody(Texts.getBytes(text, getRequestCharset()));
    }
}
