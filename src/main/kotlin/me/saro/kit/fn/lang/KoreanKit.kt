package me.saro.kit.fn.lang

class KoreanKit {
    companion object {
        // 초성 19자
        private val KO_S: CharArray = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ".toCharArray()
        // 중성 21자
        private val KO_M: CharArray = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ".toCharArray()
        // 종성 28자
        private val KO_E: CharArray = charArrayOf(0.toChar()) + "ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ".toCharArray()

        // 초성 원자화 (ㄲ -> ㄱㄱ) 19자
        private val KO_ATOM_S: Array<CharArray> = "ㄱ ㄱㄱ ㄴ ㄷ ㄷㄷ ㄹ ㅁ ㅂ ㅂㅂ ㅅ ㅅㅅ ㅇ ㅈ ㅈㅈ ㅊ ㅋ ㅌ ㅍ ㅎ".split(" ").map { it.toCharArray() }.toTypedArray()
        // 중성 원자화 (ㅙ -> ㅗㅐ) 21자
        private val KO_ATOM_M: Array<CharArray> = "ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅗㅏ ㅗㅐ ㅗㅣ ㅛ ㅜ ㅜㅓ ㅜㅔ ㅜㅣ ㅠ ㅡ ㅡㅣ ㅣ".split(" ").map { it.toCharArray() }.toTypedArray()
        // 종성 원자화 (ㄵ -> ㄴㅈ) 28자
        private val KO_ATOM_E: Array<CharArray> = arrayOf(charArrayOf()) + ("ㄱ ㄱㄱ ㄱㅅ ㄴ ㄴㅈ ㄴㅎ ㄷ ㄹ ㄹㄱ ㄹㅁ ㄹㅂ ㄹㅅ ㄹㅌ ㄹㅍ ㄹㅎ ㅁ ㅂ ㅂㅅ ㅅ ㅅㅅ ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ".split(" ").map { it.toCharArray() }.toTypedArray())
        // 미완성글자 원자화 (ㅙㄵ -> ㅗㅐㄴㅈ)
        private val KO_ATOM_P: Array<CharArray> = "ㄱ ㄱㄱ ㄱㅅ ㄴ ㄴㅈ ㄴㅎ ㄷ ㄸ ㄹ ㄹㄱ ㄹㅁ ㄹㅂ ㄹㅅ ㄹㄷ ㄹㅍ ㄹㅎ ㅁ ㅂ ㅂㅂ ㅂㅅ ㅅ ㅅㅅ ㅇ ㅈ ㅈㅈ ㅊ ㅋ ㅌ ㅍ ㅎ ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅗㅏ ㅗㅐ ㅗㅣ ㅛ ㅜ ㅜㅓ ㅜㅔ ㅜㅣ ㅠ ㅡ ㅡㅣ ㅣ".split(" ").map { it.toCharArray() }.toTypedArray()

        // 초성 유사병합 원자화 19자
        // ㄲ -> ㅋ, ㅆ -> ㅅ
        private val KO_SIM_ATOM_S: Array<CharArray> = "ㄱ ㅋ ㄴ ㄷ ㅌ ㄹ ㅁ ㅂ ㅂㅂ ㅅ ㅅ ㅇ ㅈ ㅈㅈ ㅊ ㅋ ㅌ ㅍ ㅎ".split(" ").map { it.toCharArray() }.toTypedArray()
        // 중성 유사병합 원자화 21자
        // ㅐ -> ㅔ, ㅒ -> ㅖ, ㅙ,ㅞ -> ㅚ
        private val KO_SIM_ATOM_M: Array<CharArray> = "ㅏ ㅔ ㅑ ㅖ ㅓ ㅔ ㅕ ㅖ ㅗ ㅗㅏ ㅗㅣ ㅗㅣ ㅛ ㅜ ㅜㅓ ㅜㅣ ㅜㅣ ㅠ ㅡ ㅡㅣ ㅣ".split(" ").map { it.toCharArray() }.toTypedArray()
        // 종성 유사병합 원자화 28자
        // ㄲ -> ㄱ, ㅆ -> ㅅ
        private val KO_SIM_ATOM_E: Array<CharArray> = arrayOf(charArrayOf()) + ("ㄱ ㄱ ㄱㅅ ㄴ ㄴㅈ ㄴㅎ ㄷ ㄹ ㄹㄱ ㄹㅁ ㄹㅂ ㄹㅅ ㄹㅌ ㄹㅍ ㄹㅎ ㅁ ㅂ ㅂㅅ ㅅ ㅅ ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ".split(" ").map { it.toCharArray() }.toTypedArray())


        /**
         * korean to chosung<br/>
         * ex) 사로 = ㅅㄹ
         */
        @JvmStatic
        fun toChosung(text: String?): String = text?.run {
            var i = 0
            val arr = CharArray(length)
            for (ch in this) {
                arr[i++] = if (ch in '가'..'힣') KO_S[(ch - '가') / 588] else ch
            }
            String(arr)
        } ?: ""

        /**
         * korean to ja/so<br/>
         * 많다 = ㅁㅏㄶㄷㅏ
         */
        @JvmStatic
        fun toJaso(text: String?): String = text?.run {
            var i = 0
            val arr = CharArray(length * 3) // maximum ja/so length is korean length * 3
            for (ch in this) {
                if (ch in '가'..'힣') {
                    var ce = ch - '가' // get index start of korean
                    arr[i++] = KO_S[ce / 588]; ce %= 588 // 21 * 28 - S
                    arr[i++] = KO_M[ce / 28]; ce %= 28 // 21 * 28 - M
                    if (ce != 0) { arr[i++] = KO_E[ce] } // E
                } else {
                    arr[i++] = ch
                }
            }
            String(arr, 0, i)
        } ?: ""

        /**
         * korean to ja/so atom<br/>
         * 많다 = ㅁㅏㄴㅎㄷㅏ
         */
        @JvmStatic
        fun toJasoAtom(text: String?): String = text?.takeIf { it.isNotEmpty() }
            ?.let {
                StringBuilder(text.length * 6).apply { // maximum ja/so atom length is korean length * 6
                    it.forEach { ch ->
                        if (ch in '가'..'힣') {
                            var ce = ch - '가' // get index start of korean
                            this.append(KO_ATOM_S[ce / 588]); ce %= 588 // 21 * 28 - S
                            this.append(KO_ATOM_M[ce / 28]); ce %= 28 // 21 * 28 - M
                            if (ce != 0) { this.append(KO_ATOM_E[ce]) } // E
                        } else if (ch in 'ㄱ'..'ㅣ') {
                            this.append(KO_ATOM_P[ch - 'ㄱ'])
                        } else {
                            this.append(ch)
                        }
                    }
                }.toString()
            } ?: ""

        /**
         * korean to ja/so atom<br/>
         * 많다 = ㅁㅏㄴㅎㄷㅏ
         */
        @JvmStatic
        fun toJasoSimAtom(text: String?): String = text?.takeIf { it.isNotEmpty() }
            ?.let {
                StringBuilder(text.length * 6).apply { // maximum ja/so atom length is korean length * 6
                    it.forEach { ch ->
                        if (ch in '가'..'힣') {
                            var ce = ch - '가' // get index start of korean
                            this.append(KO_SIM_ATOM_S[ce / 588]); ce %= 588 // 21 * 28 - S
                            this.append(KO_SIM_ATOM_M[ce / 28]); ce %= 28 // 21 * 28 - M
                            if (ce != 0) { this.append(KO_SIM_ATOM_E[ce]) } // E
                        } else if (ch in 'ㄱ'..'ㅣ') {
                            this.append(KO_ATOM_P[ch - 'ㄱ'])
                        } else {
                            this.append(ch)
                        }
                    }
                }.toString()
            } ?: ""
    }
}
