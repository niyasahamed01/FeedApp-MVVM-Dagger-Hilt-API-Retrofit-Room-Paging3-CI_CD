package com.example.videofeed.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: Note)

    @Query("SELECT * from note  order by id ASC")
    fun getAllNote(): LiveData<List<Note>>

    @Update
    suspend fun update(vararg items: Note)

    @Query("SELECT * FROM note WHERE note.iD == :id")
    fun get(id: Long): LiveData<Note>
}