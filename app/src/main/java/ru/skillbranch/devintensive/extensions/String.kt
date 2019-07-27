package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String {
    var result = this
    var truncated = false
    if (this.trimEnd().length > length) {
        result = this.substring(0, length)
        truncated = true
    }
    return "${result.trimEnd()}${if (truncated) "..." else ""}"
}

fun String.stripHtml(): String {
    return this
            .replace(Regex("(?:<[^>]*>)|(?:&[#a-z0-9]+;)|(" + ")"), "")
            .replace(Regex("\\s+"), " ")
}
