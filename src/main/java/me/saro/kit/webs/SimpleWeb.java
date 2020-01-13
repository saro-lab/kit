package me.saro.kit.webs;

import me.saro.kit.Texts;
import me.saro.kit.functions.ThrowableConsumer;
import me.saro.kit.functions.ThrowableFunction;

import java.io.InputStream;

/**
 * Web Client
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public interface SimpleWeb {
    /**
     * create get method Web
     * @param url
     * @return
     */
    static SimpleWeb get(String url) {
        return new SimpleWebImpl(url, "GET");
    }

    /**
     * create post method Web
     * @param url
     * @return
     */
    static SimpleWeb post(String url) {
        return new SimpleWebImpl(url, "POST");
    }

    /**
     * create put method Web
     * @param url
     * @return
     */
    static SimpleWeb put(String url) {
        return new SimpleWebImpl(url, "PUT");
    }

    /**
     * create patch method Web
     * @param url
     * @return
     */
    static SimpleWeb patch(String url) {
        return new SimpleWebImpl(url, "PATCH");
    }

    /**
     * create delete method Web
     * @param url
     * @return
     */
    static SimpleWeb delete(String url) {
        return new SimpleWebImpl(url, "DELETE");
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
    static SimpleWeb custom(String url, String method) {
        return new SimpleWebImpl(url, method);
    }
    
    /**
     * Connect Timeout
     * @param connectTimeout
     * @return
     */
    SimpleWeb setConnectTimeout(int connectTimeout);
    
    /**
     * Read Timeout
     * @param readTimeout
     * @return
     */
    SimpleWeb setReadTimeout(int readTimeout);
    
    /**
     * set request Charset
     * @param charset
     * @return
     */
    SimpleWeb setRequestCharset(String charset);
    
    /**
     * set response charset
     * @param charset
     * @return
     */
    SimpleWeb setResponseCharset(String charset);
    
    /**
     * ignore https certificate
     * <br>
     * this method not recommend
     * <br>
     * ignore certificate is defenseless the MITM(man-in-the-middle attack)
     * @param ignoreCertificate
     * @return
     */
    SimpleWeb setIgnoreCertificate(boolean ignoreCertificate);
    
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
    SimpleWeb addUrlParameter(String name, String value);
    
    /**
     * set header
     * @param name
     * @param value
     * @return
     */
    SimpleWeb setHeader(String name, String value);
    
    /**
     * write body binary
     * @param bytes
     * @return
     */
    SimpleWeb writeBody(byte[] bytes);
    
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
    SimpleWeb writeBodyParameter(String name, String value);
    
    /**
     * to Custom result
     * @param result
     * @param function
     * @return
     */
    <R> SimpleWebResult<R> toCustom(SimpleWebResult<R> result, ThrowableFunction<InputStream, R> function);
    
    /**
     * to Custom result
     * @param function
     * @return
     */
    default <R> SimpleWebResult<R> toCustom(ThrowableFunction<InputStream, R> function) {
        return toCustom(new SimpleWebResult<R>(), function);
    }

    /**
     * readRawResultStream
     * @param reader
     * @return it has Body
     */
    default SimpleWebResult<String> readRawResultStream(ThrowableConsumer<InputStream> reader) {
        return toCustom(is -> {
            reader.accept(is);
            return "OK";
        });
    }
    
    /**
     * set header ContentType
     * @param value
     * @return
     */
    default SimpleWeb setContentType(String value) {
        return setHeader("Content-Type", value);
    }
    
    /**
     * ContentType application/json
     * @return
     */
    default SimpleWeb setContentTypeApplicationJson() {
        return setHeader("Content-Type", "application/json");
    }
    
    /**
     * write Body text
     * @param text
     * @return
     */
    default SimpleWeb writeBody(String text) {
        return writeBody(Texts.getBytes(text, getRequestCharset()));
    }
}
