package com.yessorae.data.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LocalDateTimeConverter @Inject constructor() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    fun toLocalDateTime(value: String): LocalDateTime {
        return value.let { LocalDateTime.parse(it, formatter) }
    }

    fun fromLocalDateTime(date: LocalDateTime): String {
        return date.format(formatter)
    }
}
