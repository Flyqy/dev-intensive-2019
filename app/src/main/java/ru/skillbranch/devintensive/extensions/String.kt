package ru.skillbranch.devintensive.extensions

fun String.truncate(symbolNumber: Int = 16): String {
    val croppedString = this.trimEnd()
    return if (croppedString.count() > symbolNumber){
        croppedString.substring(0, symbolNumber) + "..."
    } else {
        croppedString
    }
}

fun String.stripHtml() = this
    .replace("<.*?>".toRegex(),"")
    .replace("\\s+".toRegex(), " ")
    .replace("['&]".toRegex(),"")

