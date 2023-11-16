package com.example.videofeed.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "note")
class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "isLive") val isLive: Boolean,
    @ColumnInfo(name = "timestamp") val timeStamp: String,
    @ColumnInfo(name = "date") val date: Date? = null

)