package com.yessorae.common

fun String.replaceDomain(newDomain: String = "cdn2.stablediffusionapi.com"): String {
    return try {
        val url = java.net.URL(this)
        Logger.temp("url: $url")
        val protocol = url.protocol
        Logger.temp("protocol: $protocol")
        val path = url.path
        Logger.temp("path: $path")
        val query = url.query
        Logger.temp("query: $query")
        val newUrl = StringBuilder("$protocol://$newDomain$path")
        Logger.temp("newUrl: $newUrl")
        if (query != null) {
            newUrl.append("?$query")
        }
        Logger.temp("newUrl: $newUrl", true)
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
        Logger.temp("url: $url")
        val protocol = url.protocol
        Logger.temp("protocol: $protocol")
        val path = url.path
        Logger.temp("path: $path")
        val query = url.query
        Logger.temp("query: $query")
        val newUrl = StringBuilder("$protocol://$newDomain$path")
        Logger.temp("newUrl: $newUrl")
        if (query != null) {
            newUrl.append("?$query")
        }
        Logger.temp("newUrl: $newUrl", true)
        newUrl.toString()
    } catch (e: Exception) {
        // URL 형식이 아닐 경우 원래 문자열을 반환
        this
    }
}
