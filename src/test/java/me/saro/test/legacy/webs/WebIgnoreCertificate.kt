package me.saro.test.legacy.webs

import java.net.HttpURLConnection
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Web Ignore Certificate
 * @author PARK Yong Seo
 * @since 1.0.0
 */
object WebIgnoreCertificate {
    private val ALL_TRUST_MANAGER = arrayOf<TrustManager>(
        object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
        }
    )

    /**
     * casing ignore certificate to HttpURLConnection
     * @param connection
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    @JvmStatic
    @Throws(KeyManagementException::class, NoSuchAlgorithmException::class)
    fun ignoreCertificate(connection: HttpURLConnection) {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, ALL_TRUST_MANAGER, SecureRandom())
        (connection as HttpsURLConnection).sslSocketFactory = sslContext.socketFactory
        connection.hostnameVerifier = HostnameVerifier { hostname: String?, session: SSLSession? -> true }
    }

    /**
     * apply global ignore certificate
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    @Throws(KeyManagementException::class, NoSuchAlgorithmException::class)
    fun applyGlobalIgnoreCertificate() {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, ALL_TRUST_MANAGER, SecureRandom())
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
        HttpsURLConnection.setDefaultHostnameVerifier { hostname: String?, session: SSLSession? -> true }
    }
}