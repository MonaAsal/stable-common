package com.moddakir.moddakir.utils

import java.util.Locale

enum class Language(val code: String) {

    english("English"),
    arabic("العربية"),
    indonesia("Bahasa Indonesia"),
    urdu("اُرْدُوْ"),
    french("Français"),
    empty(""),;


}

fun getListOfLanguages(): List<Language> {
    return listOf(
        Language.arabic,
        Language.english,
        Language.indonesia,
        Language.urdu,
        Language.french
    )
}


fun getLanguageCode(code: Language): String {
    return when (code) {
        Language.arabic -> "ar"
        Language.english -> "en"
        Language.indonesia -> "in"
        Language.urdu -> "ur"
        Language.french -> "fr"
        else -> ""
    }
}

fun getLanguageCodeApp(code: Language): Locale {
    val Arabic: Locale by lazy { Locale("ar", "SA") }
    val English: Locale by lazy { Locale("en", "US") }
    val French: Locale by lazy { Locale("fr", "FR") }
    val Indonesian: Locale by lazy { Locale("id", "ID") }
    val Urdu: Locale by lazy { Locale("ur", "IN") }
    return when (code) {
        Language.arabic ->Arabic
        Language.english -> English
        Language.indonesia -> Indonesian
        Language.urdu -> Urdu
        Language.french -> French
        else ->Arabic
    }
}

fun getLanguage(code: String): Language {
    return when (code) {
        "ar" -> Language.arabic
        "en" -> Language.english
        "in" -> Language.indonesia
        "ur" -> Language.urdu
        "fr" -> Language.french

        else -> {
            Language.empty
        }
    }
}

fun String.getAppLanguage(): Boolean {
    return when (this) {
        "ar" -> true
        "en" -> true
        "id" -> true
        "ur"-> true
        "fr" -> true
        else -> {
            false
        }
    }

}

