package com.sanmiaderibigbe.booktracker.data.database.dao

import android.arch.persistence.room.*
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes_table ORDER BY id")
    fun getNotes(): List<Note>

    @Query("SELECT * FROM notes_table WHERE id =:noteid ")
    fun getNote(noteid: Long): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(note: Note)

    @Delete
    fun deleteOrder(note: Note)

}