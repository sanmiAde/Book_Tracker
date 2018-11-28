package com.sanmiaderibigbe.booktracker.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class BookWithNotes{
    @Embedded
    var book : Book? = null

    @Relation(parentColumn = "id",
            entityColumn = "bookId",
            entity = Note::class)
    var notes : List<Note>? = null



}
