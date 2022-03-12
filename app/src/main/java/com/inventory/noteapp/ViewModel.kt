package com.inventory.noteapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inventory.noteapp.model.Dao
import com.inventory.noteapp.model.Notes

class viewModel(val dao: Dao) : ViewModel() {

    var selectednote: Notes = Notes(10, "temp", "temp", "temp", 1, "temp")
    var homeFragmentVisited = false
    fun getallNotes() = dao.getAllNotes()
    suspend fun insert(note: Notes) = dao.insert(note)
    suspend fun delete(note: Notes) = dao.delete(note)
    suspend fun update(note: Notes) = dao.update(note)
    fun getNoteUsingId(id: Int) = dao.getNotesUsingId(id)
    fun getFilteredNotes(priority: Int) = dao.getFilteredNotes(priority)

}

class viewModelFactory(val dao: Dao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return viewModel(dao) as T
        } else
            throw IllegalArgumentException("Class Unknown")
    }
}