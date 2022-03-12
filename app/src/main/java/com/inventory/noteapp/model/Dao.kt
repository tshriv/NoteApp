package com.inventory.noteapp.model

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Query("SELECT * FROM Notes")
    fun getAllNotes(): LiveData<List<Notes>>

    @Insert
    suspend fun insert(notes: Notes)

    @Delete
    suspend fun delete(notes: Notes)

    @Update
    suspend fun update(notes: Notes)

    @Query("SELECT * FROM Notes WHERE id= :id")
    fun getNotesUsingId(id: Int): LiveData<Notes>

    @Query("SELECT * FROM Notes WHERE priority= :priority")
    fun getFilteredNotes(priority: Int): LiveData<List<Notes>>

}