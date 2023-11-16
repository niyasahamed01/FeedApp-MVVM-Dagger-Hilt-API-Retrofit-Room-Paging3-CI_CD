package com.example.videofeed.dao

import androidx.lifecycle.LiveData
import javax.inject.Inject

class NoteRepository@Inject constructor(private val dao: NoteDao) {

    val allNotes:LiveData<List<Note>> = dao.getAllNote()

    suspend fun insert(note: Note){
        dao.insert(note)
    }

    suspend fun update(note: Note){
        dao.update(note)
    }

    fun get(id: Long):LiveData<Note> {
        return dao.get(id)
    }
}