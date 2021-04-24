package me.saro.test.legacy.webs

import me.saro.kit.Streams
import me.saro.kit.Texts
import java.io.InputStream

/**
 * Web Client
 * @author PARK Yong Seo
 * @since 1.0.0
 */
interface Web {
    /**
     * request charset
     * @return
     */
    val requestCharset: String?

    /**
     * response charset
     * @return
     */
    val responseCharset: String?

    /**
     * Connect Timeout
     * @param connectTimeout
     * @return
     */
    fun setConnectTimeout(connectTimeout: Int): Web?

    /**
     * Read Timeout
     * @param readTimeout
     * @return
     */
    fun setReadTimeout(readTimeout: Int): Web?

    /**
     * set request Charset
     * @param charset
     * @return
     */
    fun setRequestCharset(charset: String?): Web?

    /**
     * set response charset
     * @param charset
     * @return
     */
    fun setResponseCharset(charset: String?): Web?

    /**
     * ignore https certificate
     * <br></br>
     * this method not recommend
     * <br></br>
     * ignore certificate is defenseless the MITM(man-in-the-middle attack)
     * @param ignoreCertificate
     * @return
     */
    fun setIgnoreCertificate(ignoreCertificate: Boolean): Web?

    /**
     * add url parameter
     * <br></br>
     * always append url parameter even post method
     * <br></br>
     * is not body write
     * @param name
     * @param value
     * @return
     */
    fun addUrlParameter(name: String?, value: String?): Web?

    /**
     * set header
     * @param name
     * @param value
     * @return
     */
    fun setHeader(name: String?, value: String?): Web?

    /**
     * write body binary
     * @param bytes
     * @return
     */
    fun writeBody(bytes: ByteArray?): Web?

    /**
     * writeBodyParameter
     * <br></br>
     * **WARNING : ** is not json type
     * <br></br>
     * <br></br>
     * web
     * <br></br>
     * .writeBodyParameter("aa", "11")
     * <br></br>
     * .writeBodyParameter("bb", "22");
     * <br></br>
     * **equals**
     * <br></br>
     * aa=11&amp;bb=22
     * @param name
     * @param value
     * @return
     */
    fun writeBodyParameter(name: String?, value: String?): Web?

    /**
     * bind result
     * @param reader
     */
    @Throws(Exception::class)
    fun result(reader: WebReader?)

    /**
     * get result
     * @return
     */
    fun result(): WebResult? {
        val result = WebResult()
        try {
            result { status: Int, headers: Map<String?, List<String?>?>?, `is`: InputStream? ->
                result.status = status
                result.headers = headers
                result.setBody(Streams.toString(`is`, responseCharset))
            }
        } catch (e: Exception) {
            result.exception = e
        }
        return result
    }

    /**
     * set header ContentType
     * @param value
     * @return
     */
    fun setContentType(value: String?): Web? {
        return setHeader("Content-Type", value)
    }

    /**
     * ContentType application/json
     * @return
     */
    fun setContentTypeApplicationJson(): Web? {
        return setHeader("Content-Type", "application/json")
    }

    /**
     * write Body text
     * @param text
     * @return
     */
    fun writeBody(text: String?): Web? {
        return writeBody(Texts.getBytes(text, requestCharset))
    }

    companion object {
        /**
         * create get method Web
         * @param url
         * @return
         */
        @JvmStatic
        operator fun get(url: String?): Web? {
            return WebImpl(url, "GET")
        }

        /**
         * create post method Web
         * @param url
         * @return
         */
        fun post(url: String?): Web? {
            return WebImpl(url, "POST")
        }

        /**
         * create put method Web
         * @param url
         * @return
         */
        fun put(url: String?): Web? {
            return WebImpl(url, "PUT")
        }

        /**
         * create patch method Web
         * @param url
         * @return
         */
        fun patch(url: String?): Web? {
            return WebImpl(url, "PATCH")
        }

        /**
         * create delete method Web
         * @param url
         * @return
         */
        fun delete(url: String?): Web? {
            return WebImpl(url, "DELETE")
        }

        /**
         * apply global ignore certificate
         * @return is success
         */
        @JvmStatic
        fun applyGlobalIgnoreCertificate(): Boolean {
            return try {
                WebIgnoreCertificate.applyGlobalIgnoreCertificate()
                true
            } catch (e: Exception) {
                false
            }
        }

        /**
         * create custom method Web
         * @param url
         * @return
         */
        fun custom(url: String?, method: String?): Web? {
            return WebImpl(url, method)
        }
    }
}