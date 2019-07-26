package ru.skillbranch.devintensive.utils

import java.util.stream.Stream

object Utils {
    val CHAR_MAPING = hashMapOf(
        "а" to "a",
        "б" to "b",
        "в" to "v",
        "г" to "g",
        "д" to "d",
        "е" to "e",
        "ё" to "e",
        "ж" to "zh",
        "з" to "z",
        "и" to "i",
        "й" to "i",
        "к" to "k",
        "л" to "l",
        "м" to "m",
        "н" to "n",
        "о" to "o",
        "п" to "p",
        "р" to "r",
        "с" to "s",
        "т" to "t",
        "у" to "u",
        "ф" to "f",
        "х" to "h",
        "ц" to "c",
        "ч" to "ch",
        "ш" to "sh",
        "щ" to "sh'",
        "ъ" to "",
        "ы" to "i",
        "ь" to "",
        "э" to "e",
        "ю" to "yu",
        "я" to "ya"
    )

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName.isNullOrBlank()) {
            return null to null
        }
        val parts: List<String>? = fullName.split(" ")
        val firstName = parts?.getOrNull(0)?.trim()
        val lastName = parts?.getOrNull(1)?.trim()
        return firstName to lastName
    }

    fun transliteration(payload: String?, divider: String = " "): String {
        if (payload.isNullOrBlank()) {
            return payload ?: ""
        }
        var result = payload.toLowerCase()
        for ((key, value) in CHAR_MAPING) {
            result = result.replace(key.toRegex(), value)
        }
        result = result.split(" ").joinToString(divider) { it.capitalize() }
        return result
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) {
            return null
        }
        return "${firstName?.getOrNull(0)?.toUpperCase() ?: ""}${lastName?.get(0)?.toUpperCase() ?: ""}"
    }
}