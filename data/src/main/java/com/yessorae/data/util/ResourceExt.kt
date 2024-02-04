package com.yessorae.data.util

fun String.replaceDomain(newDomain: String = "cdn2.stablediffusionapi.com"): String {
    return try {
        val url = java.net.URL(this)
        val protocol = url.protocol
        val path = url.path
        val query = url.query
        val newUrl = StringBuilder("$protocol://$newDomain$path")
        if (query != null) {
            newUrl.append("?$query")
        }
        newUrl.toString()
    } catch (e: Exception) {
        // URL 형식이 아닐 경우 원래 문자열을 반환
        this
    }
}

fun String.replacePubDomain(newDomain: String = "cdn2.stablediffusionapi.com"): String {
    if (!this.contains("//pub-")) {
        return this
    }
    return try {
        val url = java.net.URL(this)
        val protocol = url.protocol
        val path = url.path
        val query = url.query
        val newUrl = StringBuilder("$protocol://$newDomain$path")
        if (query != null) {
            newUrl.append("?$query")
        }
        newUrl.toString()
    } catch (e: Exception) {
        // URL 형식이 아닐 경우 원래 문자열을 반환
        this
    }
}
