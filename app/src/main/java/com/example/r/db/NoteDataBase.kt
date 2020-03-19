package com.example.r.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

@Database(

    entities = [Note::class],
    version = 1
)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun getNote(): NoteDao


    companion object {


        //be available for all Threads
        @Volatile
        private var instance: NoteDataBase? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(Lock) {
            instance ?: buildDatabase(context)
        }


        private fun buildDatabase(context: Context) = Room.databaseBuilder(

            context.applicationContext,
            NoteDataBase::class.java,
            "noteDataBase"
        ).build()

    }
}