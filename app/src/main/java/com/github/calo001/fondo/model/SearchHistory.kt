package com.github.calo001.fondo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "search_history")
data class SearchHistory(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "value") val value: String?,
    @ColumnInfo(name= "date") val date: Date = Date(System.currentTimeMillis())
)