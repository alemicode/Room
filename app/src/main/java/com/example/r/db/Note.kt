package com.example.r.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val title : String,
    var note : String
)