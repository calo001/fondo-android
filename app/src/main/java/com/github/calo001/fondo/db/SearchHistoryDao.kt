package com.github.calo001.fondo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.calo001.fondo.model.SearchHistory
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface SearchHistoryDao {
    @Query("SELECT DISTINCT value FROM search_history GROUP BY value ORDER BY date DESC")
    fun getAll(): Observable<List<String>>

    @Insert
    fun insert(search: SearchHistory): Completable

    @Update
    fun update(search: SearchHistory): Completable

    @Query("DELETE FROM search_history WHERE value = :item")
    fun delete(item: String): Completable
}