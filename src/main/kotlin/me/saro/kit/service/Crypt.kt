package me.saro.kit.service

import me.saro.kit.SecurityKit
import me.saro.kit.enums.Transformation
import java.io.InputStream
import java.io.OutputStream
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Crypt private constructor(
    private val transformation: String,
) {
    private var key = ByteArray(0)
    private var iv = ByteArray(0)

    companion object {
        @JvmStatic
        fun create(transformation: String): Crypt {
            if (transformation.split('/').size != 3) {
                throw IllegalArgumentException("transformation must be 3 parts. ex) AES/CBC/PKCS5Padding")
            }
            return Crypt(transformation)
        }
        @JvmStatic
        fun create(transformation: Transformation): Crypt = create(transformation.value)
    }

    val algorithm get() = transformation.split("/")[0]
    val mode get() = transformation.split("/")[1]
    val padding get() = transformation.split("/")[2]

    fun key(key: String): Crypt {
        this.key = key.toByteArray()
        return this
    }

    fun key(key: ByteArray): Crypt {
        this.key = key
        return this
    }

    fun iv(iv: String): Crypt {
        this.iv = iv.toByteArray()
        return this
    }

    fun iv(iv: ByteArray): Crypt {
        this.iv = iv
        return this
    }

    fun encrypt(): Encrypt =
        Encrypt(Cipher.getInstance(transformation).also {
            if (iv.isNotEmpty()) {
                it.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, algorithm), IvParameterSpec(iv))
            } else {
                it.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, algorithm))
            }
        })

    fun decrypt(): Decrypt =
        Decrypt(Cipher.getInstance(transformation).also {
            if (iv.isNotEmpty()) {
                it.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, algorithm), IvParameterSpec(iv))
            } else {
                it.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, algorithm))
            }
        })

    class Encrypt internal constructor(private val cipher: Cipher) {
        fun encrypt(data: ByteArray): ByteArray = cipher.doFinal(data)
        fun encrypt(data: String): ByteArray = cipher.doFinal(data.toByteArray())
        fun encryptBase64(data: ByteArray): String = SecurityKit.enBase64(cipher.doFinal(data))
        fun encryptBase64(data: String): String = SecurityKit.enBase64(cipher.doFinal(data.toByteArray()))
        fun encryptBase64Url(data: ByteArray): String = SecurityKit.enBase64Url(cipher.doFinal(data))
        fun encryptBase64Url(data: String): String = SecurityKit.enBase64Url(cipher.doFinal(data.toByteArray()))
        fun encryptByteStream(inputStream: InputStream, outputStream: OutputStream) {
            val buffer = ByteArray(8192)
            var len: Int
            while (inputStream.read(buffer).also { len = it } > 0) {
                outputStream.write(cipher.update(buffer, 0, len))
            }
            outputStream.write(cipher.doFinal())
        }
    }

    class Decrypt internal constructor(private val cipher: Cipher) {
        fun decrypt(data: ByteArray): ByteArray = cipher.doFinal(data)
        fun decryptBase64ToByte(data: String): ByteArray = cipher.doFinal(SecurityKit.deBase64(data))
        fun decryptBase64ToString(data: String): String = String(cipher.doFinal(SecurityKit.deBase64(data)), Charsets.UTF_8)
        fun decryptBase64UrlToByte(data: String): ByteArray = cipher.doFinal(SecurityKit.deBase64Url(data))
        fun decryptBase64UrlToString(data: String): String = String(cipher.doFinal(SecurityKit.deBase64Url(data)), Charsets.UTF_8)
        fun decryptByteStream(inputStream: InputStream, outputStream: OutputStream) {
            val buffer = ByteArray(8192)
            var len: Int
            while (inputStream.read(buffer).also { len = it } > 0) {
                outputStream.write(cipher.update(buffer, 0, len))
            }
            outputStream.write(cipher.doFinal())
        }
    }
}
