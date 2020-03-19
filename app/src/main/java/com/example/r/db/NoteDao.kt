package com.example.r.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note: Note)


    @Query("select * from note ORDER BY id DESC")
    suspend fun getAllNote(): List<Note>

    @Update
    suspend fun updateNote(note: Note)


    //delete all database
    @Query("DELETE FROM note")
    suspend fun nukeTable()
}