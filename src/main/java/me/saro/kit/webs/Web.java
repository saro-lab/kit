package me.saro.kit.webs;

import me.saro.kit.Files;
import me.saro.kit.Texts;
import me.saro.kit.functions.ThrowableConsumer;
import me.saro.kit.functions.ThrowableFunction;

import java.io.File;
import java.io.InputStream;

/**
 * Web Client
 * @author      PARK Yong Seo
 * @since       0.1
 */
public interface Web {
    /**
     * create get method Web
     * @param url
     * @return
     */
    public static Web get(String url) {
        return new BasicWeb(url, "GET");
    }

    /**
     * create post method Web
     * @param url
     * @return
     */
    public static Web post(String url) {
        return new BasicWeb(url, "POST");
    }

    /**
     * create put method Web
     * @param url
     * @return
     */
    public static Web put(String url) {
        return new BasicWeb(url, "PUT");
    }

    /**
     * create patch method Web
     * @param url
     * @return
     */
    public static Web patch(String url) {
        return new BasicWeb(url, "PATCH");
    }

    /**
     * create delete method Web
     * @param url
     * @return
     */
    public static Web delete(String url) {
        return new BasicWeb(url, "DELETE");
    }
    
    /**
     * request charset
     * @return
     */
    public String getRequestCharset();
    
    /**
     * response charset
     * @return
     */
    public String getResponseCharset();

    /**
     * create custom method Web
     * @param url
     * @return
     */
    public static Web custom(String url, String method) {
        return new BasicWeb(url, method);
    }
    
    /**
     * Connect Timeout
     * @param connectTimeout
     * @return
     */
    public Web setConnectTimeout(int connectTimeout);
    
    /**
     * Read Timeout
     * @param readTimeout
     * @return
     */
    public Web setReadTimeout(int readTimeout);
    
    /**
     * set request Charset
     * @param charset
     * @return
     */
    public Web setRequestCharset(String charset);
    
    /**
     * set response charset
     * @param charset
     * @return
     */
    public Web setResponseCharset(String charset);
    
    /**
     * ignore https certificate
     * <br>
     * this method not recommend
     * <br>
     * ignore certificate is defenseless the MITM(man-in-the-middle attack)
     * @param ignoreCertificate
     * @return
     */
    public Web setIgnoreCertificate(boolean ignoreCertificate);
    
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
    public Web addUrlParameter(String name, String value);
    
    /**
     * set header
     * @param name
     * @param value
     * @return
     */
    public Web setHeader(String name, String value);
    
    /**
     * write body binary
     * @param bytes
     * @return
     */
    public Web writeBody(byte[] bytes);
    
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
    public Web writeBodyParameter(String name, String value);
    
    /**
     * to Custom result
     * @param result
     * @param function
     * @return
     */
    public <R> WebResult<R> toCustom(WebResult<R> result, ThrowableFunction<InputStream, R> function);
    
    /**
     * to Custom result
     * @param function
     * @return
     */
    default public <R> WebResult<R> toCustom(ThrowableFunction<InputStream, R> function) {
        return toCustom(new WebResult<R>(), function);
    }

    /**
     * save file and return WebResult
     * @return WebResult[WebResult]
     */
    default public WebResult<File> saveFile(File file, boolean overwrite) {
        return toCustom(is -> Files.createFile(file, overwrite, is));
    }

    /**
     * readRawResultStream
     * @param reader
     * @return it has Body
     */
    default public WebResult<String> readRawResultStream(ThrowableConsumer<InputStream> reader) {
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
    default public Web setContentType(String value) {
        return setHeader("Content-Type", value);
    }
    
    /**
     * ContentType application/json
     * @return
     */
    default public Web setContentTypeApplicationJson() {
        return setHeader("Content-Type", "application/json");
    }
    
    /**
     * write Body text
     * @param text
     * @return
     */
    default public Web writeBody(String text) {
        return writeBody(Texts.getBytes(text, getRequestCharset()));
    }
}
