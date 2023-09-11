package com.yessorae.data

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.yessorae.data.local.database.converter.ListStringConverter
import org.junit.Assert.assertEquals
import org.junit.Test

class ListStringConverterTest {

    private val converter = ListStringConverter()

    @Test
    fun testFromListString() {
        val list = listOf("item1", "item2", "item3")
        val expectedJson = Gson().toJson(list)

        val actualJson = converter.fromListString(list)

        assertEquals(expectedJson, actualJson)
    }

    @Test
    fun testToListString() {
        val list = listOf("item1", "item2", "item3")
        val json = Gson().toJson(list)

        val expectedList: List<String> = Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
        val actualList = converter.toListString(json)

        assertEquals(expectedList, actualList)
    }
}