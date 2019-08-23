package com.github.calo001.fondo.util

import com.github.calo001.fondo.Fondo
import com.github.calo001.fondo.model.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.io.File

class FondoJsonManager {
    private val file = File(getExternalDir(Fondo.getInstance()) + FILE_NAME)

    fun addToHistory(photo: Photo): Boolean {
        return try {
            val list = getAllHistory() as MutableList
            list.add(photo)
            saveHistory(list)
        } catch (e: JsonSyntaxException) {
            false
        }
    }

    fun getHistory(page: Int, perPage: Int = 12): List<Photo> {
        val gson = Gson()
        val bufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        // Start = (page * perPage) - perPage
        // End = (page * perPage)
        // Sublis[Start, End)

        val start = (page * perPage) - perPage
        val end = (page * perPage) - 1

        try {
            return gson.fromJson<List<Photo>>(inputString, Photo::class.java)
                .subList(start, end)
        } catch (e: JsonSyntaxException) {
            return emptyList()
        }

    }

    private fun saveHistory(list: List<Photo>): Boolean {
        val gson: Gson = GsonBuilder().create()
        val jsonString = gson.toJson(list)

        if (!file.exists()) {
            if (!file.createNewFile()) return false
        }

        file.writeText(jsonString)
        return true
    }

    private fun getAllHistory(): List<Photo> {
        val gson = Gson()
        val bufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        try {
            return gson.fromJson<List<Photo>>(inputString, Photo::class.java)
        } catch (e: JsonSyntaxException) {
            throw JsonSyntaxException(e)
        }
    }

    companion object {
        const val FILE_NAME = "fondo_history.json"
    }
}

/*fun main() {
    val list = listOf<Photo>(
        Photo(
            listOf("a", "b", "c"),
            "#DDD",
            "created",
            listOf("a", "b", "c", "d", "e"),
            "description",
            2300,
            "ID234",
            true,
            12,
            LinksPhoto("link", "link", "link", "link"),
            true,
            "Yo",
            "id",
            "updatedat",
            Urls("urls", "urls", "urls", "urls", "urls"),
            User(
                true,
                "bio",
                "first",
                "id",
                "insta",
                "last",
                Links("links", "links", "links", "links", "links", "links", "links"),
                "location",
                "name",
                "portfolio",
                ProfileImage("img", "img", "img"),
                12,
                1,
                2,
                "twitter",
                "updated at",
                "usermane"
            ),
            123
        ),
        Photo(
            listOf("z", "x", "y"),
            "#DDD",
            "created",
            listOf("a", "b", "c", "d", "e"),
            "description",
            2300,
            "ID234",
            true,
            12,
            LinksPhoto("link", "link", "link", "link"),
            true,
            "Yo",
            "id",
            "updatedat",
            Urls("urls", "urls", "urls", "urls", "urls"),
            User(
                true,
                "bio",
                "first",
                "id",
                "insta",
                "last",
                Links("links", "links", "links", "links", "links", "links", "links"),
                "location",
                "name",
                "portfolio",
                ProfileImage("img", "img", "img"),
                12,
                1,
                2,
                "twitter",
                "updated at",
                "usermane"
            ),
            123
        )
    )
    val jsoninstring = FondoJsonManager().saveHistory(list)
    println(jsoninstring)
}*/