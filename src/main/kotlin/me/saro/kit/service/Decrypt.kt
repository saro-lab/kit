package me.saro.kit.service

class Decrypt private constructor() {
    companion object {
        @JvmStatic
        fun encrypt(data: String): String = data

        @JvmStatic
        fun decrypt(data: String): String = data
    }
}