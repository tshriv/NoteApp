package com.inventory.noteapp.ui

import android.app.Application
import com.inventory.noteapp.model.NotesDatabase

class NotesApplication : Application() {

    val database: NotesDatabase by lazy { NotesDatabase.getDataBase(this) }
}