package com.example.videofeed.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.videofeed.dao.Note
import com.example.videofeed.dao.NoteDatabase
import com.example.videofeed.dao.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel@Inject constructor(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    val allItems: LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDatabase(application).datarecordDao()
        repository = NoteRepository(dao)
        allItems = repository.allNotes
    }

    fun insert(item: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    fun update(item: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(item)
    }

    fun get(id: Long) = repository.get(id)



}