package com.example.r.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class Note(


    val title : String,
    var note : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}