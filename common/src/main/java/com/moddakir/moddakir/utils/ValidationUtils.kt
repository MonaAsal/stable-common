package com.moddakir.moddakir.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

class ValidationUtils {
    val USERNAME_PATTERN: String = "^[a-zA-Z0-9_.-]{4,15}$"
    var pattern: Pattern? = null
    var matcher: Matcher? = null
    fun isNotEmpty(text: String): Boolean {
        return !text.trim { it <= ' ' }.isEmpty()
    }

    fun validname(text: String): Boolean {
        return text.length >= 4 && text.length <= 50
    }

    fun validusername(text: String?): Boolean {
        var pattern1= Pattern.compile(USERNAME_PATTERN)
        var  matcher1 = pattern1.matcher(text)
        return matcher1.matches()
    }

    fun MatchText(text1: String, text2: String): Boolean {
        return !text2.trim { it <= ' ' }.isEmpty() && text1 == text2
    }

    fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches() && !email.trim { it <= ' ' }.isEmpty()
    }

    fun isValidaName(name: String): Boolean {
        val regx = "^[\\p{L} .'-]+$"
        return name.matches(regx.toRegex())
    }
    fun isValidPassword(text: String): Boolean {
        val passregex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$"
        return text.matches(passregex.toRegex())
    }
    fun isValidMobile(phone: String): Boolean {
        val p = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$")
        val m = p.matcher(phone)
        return (m.find() && m.group() == phone)
    }
}