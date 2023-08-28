package com.yessorae.imagefactory

import com.yessorae.imagefactory.ui.util.isMultiLanguage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class StringExtUnitTest {
    @Test
    fun testIsMultiLanguage() {
        // 영어 알파벳만 포함
        assertFalse("Hello".isMultiLanguage())

        // 숫자만 포함
        assertFalse("12345".isMultiLanguage())

        // 특수문자만 포함
        assertFalse("!@#$%".isMultiLanguage())

        // 영어 알파벳, 숫자, 특수문자 혼합
        assertFalse("Hello123!".isMultiLanguage())

        // 한글 포함
        assertTrue("안녕하세요".isMultiLanguage())

        // 다른 언어의 문자 포함 (예: 일본어)
        assertTrue("こんにちは".isMultiLanguage())

        // 공백 포함
        assertFalse(" ".isMultiLanguage())

        // 빈 문자열
        assertFalse("".isMultiLanguage())
    }
}
