package com.github.calo001.fondo.util

import com.github.calo001.fondo.Fondo
import com.github.calo001.fondo.model.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.ClassCastException

class FondoJsonManager {
    private val file = File(getExternalDir(Fondo.getInstance()) + FILE_NAME)

    init {
        if (!file.exists()) {
            file.createNewFile()
            val gson: Gson = GsonBuilder().create()
            val jsonString = gson.toJson(EmptyJson())

            file.writeText(jsonString)
        }
    }

    fun addToHistory(photo: Photo): Boolean {
        return try {
            val list = getAllHistory() as MutableList

            val exist = list.find {
                it.id == photo.id
            }

            exist?.let {
                list.remove(it)
            }

            list.add(0, photo)
            saveHistory(list)
        } catch (e: JsonSyntaxException) {
            saveHistory(listOf(photo))
        } catch (e: ClassCastException) {
            saveHistory(listOf(photo))
        }
    }

    fun getHistory(page: Int, perPage: Int = 12): List<Photo> {
        val gson = Gson()
        val bufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        // Start = (page * perPage) - perPage
        // End = (page * perPage)
        // Sublis[Start, End)

        var start = (page * perPage) - perPage
        var end = (page * perPage) - 1

        return try {
            val listType = object : TypeToken<List<Photo>>() {}.type
            val result: List<Photo> = gson.fromJson(inputString, listType)
            when {
                start > result.size -> emptyList()
                end > result.size -> result.subList(start, result.size)
                else -> result.subList(start, end)
            }
        } catch (e: JsonSyntaxException) {
            emptyList()
        } catch (e: ClassCastException) {
            emptyList()
        }

    }

    private fun saveHistory(list: List<Photo>): Boolean {
        val gson: Gson = GsonBuilder().create()
        val jsonString = gson.toJson(list)

        file.writeText(jsonString)
        return true
    }

    private fun getAllHistory(): List<Photo> {
        val gson = Gson()
        val bufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        try {
            val listType = object : TypeToken<List<Photo>>() {}.type
            return gson.fromJson(inputString, listType)
        } catch (e: JsonSyntaxException) {
            throw JsonSyntaxException(e)
        }
    }

    companion object {
        const val FILE_NAME = "fondo_history.json"
    }
}