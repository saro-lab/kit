package me.saro.kit.webs;

import me.saro.kit.Streams;
import me.saro.kit.functions.ThrowableFunction;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Web Client Builder
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public class SimpleWebImpl implements SimpleWeb {

    // url
    final String url;

    // method
    final String method;

    // chaset
    String requestCharset = "UTF-8";
    String responseCharset = "UTF-8";

    // url parameter
    StringBuilder urlParameter = new StringBuilder(100);

    // request header
    Map<String, String> header = new HashMap<>();

    // request body
    ByteArrayOutputStream body = new ByteArrayOutputStream(8192);

    // ignore certificate
    boolean ignoreCertificate = false;
    
    // connectTimeout
    int connectTimeout;
    
    // readTimeout
    int readTimeout;

    /**
     * private constructor
     * @param url
     * @param method
     */
    protected SimpleWebImpl(String url, String method) {
        int point;
        if ((point = url.indexOf('?')) > -1) {
            if ((point) < url.length()) {
                urlParameter.append(url.substring(point));
            }
            url = url.substring(0, point);
        } else {
            urlParameter.append('?');
        }
        this.url = url;
        this.method = method;
    }
    
    /**
     * Connect Timeout
     * @param connectTimeout
     * @return
     */
    public SimpleWeb setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }
    
    /**
     * Read Timeout
     * @param readTimeout
     * @return
     */
    public SimpleWeb setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * set request Charset
     * @param charset
     * @return
     */
    public SimpleWeb setRequestCharset(String charset) {
        this.requestCharset = charset;
        return this;
    }

    /**
     * set response charset
     * @param charset
     * @return
     */
    public SimpleWeb setResponseCharset(String charset) {
        this.responseCharset = charset;
        return this;
    }

    /**
     * ignore https certificate
     * <br>
     * this method not recommend
     * <br>
     * ignore certificate is defenseless the MITM(man-in-the-middle attack)
     * @param ignoreCertificate
     * @return
     */
    public SimpleWeb setIgnoreCertificate(boolean ignoreCertificate) {
        this.ignoreCertificate = ignoreCertificate;
        return this;
    }

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
    public SimpleWeb addUrlParameter(String name, String value) {
        if (urlParameter.length() > 1) {
            urlParameter.append('&');
        }
        try {
            urlParameter.append(name).append('=').append(URLEncoder.encode(value, requestCharset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return this;
    }

    /**
     * set header
     * @param name
     * @param value
     * @return
     */
    public SimpleWeb setHeader(String name, String value) {
        header.put(name, value);
        return this;
    }

    /**
     * write body binary
     * @param bytes
     * @return
     */
    public SimpleWeb writeBody(byte[] bytes) {
        try {
            body.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

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
    public SimpleWeb writeBodyParameter(String name, String value) {
        if (body.size() > 0) {
            body.write('&');
        }
        try {
            body.write(URLEncoder.encode(name, requestCharset).getBytes());
            body.write('=');
            body.write(URLEncoder.encode(value, requestCharset).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * to Custom result
     * @param result
     * @param function
     * @return
     */
    public <R> SimpleWebResult<R> toCustom(SimpleWebResult<R> result, ThrowableFunction<InputStream, R> function) {
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection)(new URL(urlParameter.length() > 1 ? (url + urlParameter.toString()) : url )).openConnection();

            if (ignoreCertificate) {
                SimpleWebIgnoreCertificate.ignoreCertificate(connection);
            }
            
            if (connectTimeout > 0) {
                connection.setConnectTimeout(connectTimeout);
            }
            if (readTimeout > 0) {
                connection.setReadTimeout(readTimeout);
            }

            header.forEach(connection::setRequestProperty);

            if (body.size() > 0) {
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(body.toByteArray());
                    os.flush();
                }
            }

            result.setStatus(connection.getResponseCode());
            result.setHeaders(connection.getHeaderFields());
            
            input : try {
                try (InputStream is = connection.getInputStream()) {
                    try {
                        result.setBody(function.apply(is));
                    } catch (Exception e) {
                        result.setException(new Exception("TYPE CAST ERROR : " + e.getMessage(), e));
                        break input;
                    }
                }
            } catch (IOException ie) {
                result.setErrorBody(Streams.toString(connection.getErrorStream(), getResponseCharset()));
            }

            
        } catch (Exception e) {
            result.setException(e);
        }

        return result;
    }

    @Override
    public String getRequestCharset() {
        return requestCharset;
    }

    @Override
    public String getResponseCharset() {
        return responseCharset;
    }
}
