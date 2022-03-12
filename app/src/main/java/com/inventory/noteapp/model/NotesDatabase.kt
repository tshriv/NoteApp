package com.inventory.noteapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Notes::class), version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun dao(): Dao

    companion object {

        @Volatile
        var INSTANCE: NotesDatabase? = null

        fun getDataBase(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context, NotesDatabase::class.java,
                        "Notes"
                    ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}