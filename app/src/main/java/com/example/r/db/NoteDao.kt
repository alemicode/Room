package com.example.r.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface NoteDao {

    @Insert
    fun addNote(note : Note)


    @Query("select * from note")
    fun getAllNote(): List<Note>


}