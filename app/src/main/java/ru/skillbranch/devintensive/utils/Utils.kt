package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val source: List<String>? = fullName?.trimStart()?.split(" ")
        return source?.getOrNull(0)?.ifBlank { null } to
                source?.getOrNull(1)?.ifBlank { null }
    }

    fun transliteration(payload: String?, divider: String = " "): String? {
        val vocabulary: HashMap<String, String> = hashMapOf(
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
            "я" to "ya",
            " " to divider
        )
        val result = StringBuilder()
        payload?.let {
            it.forEach { char ->
                val value = if (char.isUpperCase()) {
                    val temp = vocabulary[char.toString().toLowerCase()]?.toUpperCase()
                    if (temp?.length == 2) {
                        "${temp[0]}" + "${temp[1].toLowerCase()}"
                    } else {
                        temp
                    }
                } else {
                    vocabulary[char.toString()]
                }
                result.append(
                    if (value.isNullOrEmpty()) char else value
                )
            }
        }
        return result.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val name = firstName?.getOrNull(0)?.toUpperCase() ?: ""
        val surname = lastName?.getOrNull(0)?.toUpperCase() ?: ""
        return "$name$surname".ifBlank { null }
    }
}