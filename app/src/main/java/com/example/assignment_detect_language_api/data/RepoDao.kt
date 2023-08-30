package com.example.assignment_detect_language_api.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RepoDao {
    @get:Query("SELECT * FROM responses")
    val allRepos: List<Response?>?

    @Insert
    fun insert(vararg responses: Response?)

    @Update
    fun update(vararg responses: Response?)

    @Delete
    fun delete(vararg responses: Response?)
}