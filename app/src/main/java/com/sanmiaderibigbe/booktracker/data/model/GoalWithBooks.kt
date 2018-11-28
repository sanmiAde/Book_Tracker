package com.sanmiaderibigbe.booktracker.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class GoalWithBooks{
    @Embedded
    var goal: Goal? = null

    @Relation(parentColumn = "id", entityColumn = "goalId", entity = Book::class)
    var books: List<Book>? = null
}


