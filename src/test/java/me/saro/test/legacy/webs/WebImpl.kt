package me.saro.test.legacy.webs

import me.saro.test.legacy.webs.WebIgnoreCertificate.ignoreCertificate
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/**
 * Web Client Builder
 * @author PARK Yong Seo
 * @since 1.0.0
 */
class WebImpl protected constructor(url: String, method: String) : Web {
    // url
    val url: String

    // method
    val method: String

    // chaset
    override var requestCharset: String? = "UTF-8"
    override var responseCharset: String? = "UTF-8"

    // url parameter
    var urlParameter = StringBuilder(100)

    // request header
    var header: MutableMap<String?, String?> = HashMap()

    // request body
    var body = ByteArrayOutputStream(8192)

    // ignore certificate
    var ignoreCertificate = false

    // connectTimeout
    var connectTimeout = 0

    // readTimeout
    var readTimeout = 0

    /**
     * Connect Timeout
     * @param connectTimeout
     * @return
     */
    override fun setConnectTimeout(connectTimeout: Int): Web? {
        this.connectTimeout = connectTimeout
        return this
    }

    /**
     * Read Timeout
     * @param readTimeout
     * @return
     */
    override fun setReadTimeout(readTimeout: Int): Web? {
        this.readTimeout = readTimeout
        return this
    }

    /**
     * set request Charset
     * @param charset
     * @return
     */
    override fun setRequestCharset(charset: String?): Web? {
        requestCharset = charset
        return this
    }

    /**
     * set response charset
     * @param charset
     * @return
     */
    override fun setResponseCharset(charset: String?): Web? {
        responseCharset = charset
        return this
    }

    /**
     * ignore https certificate
     * <br></br>
     * this method not recommend
     * <br></br>
     * ignore certificate is defenseless the MITM(man-in-the-middle attack)
     * @param ignoreCertificate
     * @return
     */
    override fun setIgnoreCertificate(ignoreCertificate: Boolean): Web? {
        this.ignoreCertificate = ignoreCertificate
        return this
    }

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
    override fun addUrlParameter(name: String?, value: String?): Web? {
        if (urlParameter.length > 1) {
            urlParameter.append('&')
        }
        try {
            urlParameter.append(name).append('=').append(URLEncoder.encode(value, requestCharset))
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e.message, e)
        }
        return this
    }

    /**
     * set header
     * @param name
     * @param value
     * @return
     */
    override fun setHeader(name: String?, value: String?): Web? {
        header[name] = value
        return this
    }

    /**
     * write body binary
     * @param bytes
     * @return
     */
    override fun writeBody(bytes: ByteArray?): Web? {
        try {
            body.write(bytes)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return this
    }

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
    override fun writeBodyParameter(name: String?, value: String?): Web? {
        if (body.size() > 0) {
            body.write('&'.toInt())
        }
        try {
            body.write(URLEncoder.encode(name, requestCharset).toByteArray())
            body.write('='.toInt())
            body.write(URLEncoder.encode(value, requestCharset).toByteArray())
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return this
    }

    @Throws(Exception::class)
    override fun result(reader: WebReader?) {
        try {
            val connection =
                URL(if (urlParameter.length > 1) url + urlParameter.toString() else url).openConnection() as HttpURLConnection
            if (ignoreCertificate) {
                ignoreCertificate(connection)
            }
            if (connectTimeout > 0) {
                connection.connectTimeout = connectTimeout
            }
            if (readTimeout > 0) {
                connection.readTimeout = readTimeout
            }
            header.forEach { (key: String?, value: String?) -> connection.setRequestProperty(key, value) }
            if (body.size() > 0) {
                connection.doOutput = true
                connection.outputStream.use { os ->
                    os.write(body.toByteArray())
                    os.flush()
                }
            }
            val code = connection.responseCode
            val headers = connection.headerFields
            try {
                connection.inputStream.use { `is` ->
                    try {
                        reader!!.read(code, headers, `is`)
                    } catch (e: Exception) {
                        throw e
                    }
                }
            } catch (ie: IOException) {
                connection.errorStream.use { `is` -> reader!!.read(code, headers, `is`) }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * private constructor
     * @param url
     * @param method
     */
    init {
        var url = url
        var point: Int
        if (url.indexOf('?').also { point = it } > -1) {
            if (point < url.length) {
                urlParameter.append(url.substring(point))
            }
            url = url.substring(0, point)
        } else {
            urlParameter.append('?')
        }
        this.url = url
        this.method = method
    }
}