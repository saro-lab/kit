package me.saro.test.legacy.webs

import java.util.*
import java.util.function.Predicate

/**
 * web result
 * @author PARK Yong Seo
 * @since 1.0.0
 */
class WebResult internal constructor() {
    // http status
    var status = -1

    // exception
    var exception: Exception? = null

    // headers
    var headers = emptyMap<String, List<String>>()

    // response body data
    private var body: String? = null

    /**
     * is status 2xx + have not exception
     * @return
     */
    val isSuccess: Boolean
        get() = isStatus2xx && exception == null

    /**
     * is status 2xx
     * @return
     */
    val isStatus2xx: Boolean
        get() = status >= 200 && status < 300

    /**
     * is status 3xx
     * @return
     */
    val isStatus3xx: Boolean
        get() = status >= 300 && status < 400

    /**
     * is status 4xx
     * @return
     */
    val isStatus4xx: Boolean
        get() = status >= 400 && status < 500

    /**
     * is status 5xx
     * @return
     */
    val isStatus5xx: Boolean
        get() = status >= 500 && status < 600

    /**
     * has body
     * @return
     */
    fun hasBody(): Boolean {
        return body != null
    }

    fun getBody(): Optional<String> {
        return Optional.ofNullable(body)
    }

    fun getBody(filter: Predicate<WebResult?>): Optional<String> {
        return Optional.ofNullable(if (filter.test(this)) body else null)
    }

    fun getBody(orElse: String?): String {
        return if (body != null) body!! else orElse!!
    }

    fun setBody(body: String?) {
        this.body = body
    }

    override fun toString(): String {
        return "WebResult{" +
                "status=" + status +
                ", exception=" + exception +
                ", headers=" + headers +
                ", body=" + body +
                '}'
    }
}