package com.sanmiaderibigbe.booktracker.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.sanmiaderibigbe.booktracker.data.model.Goal
import com.sanmiaderibigbe.booktracker.data.model.GoalWithBooks

@Dao
interface GoalDao {

    @Query("SELECT * FROM goals_table ORDER BY id")
    fun getNotes(): List<Goal>

    @Query("SELECT * FROM goals_table WHERE id =:goalid ")
    fun getNote(goalid: Long): Goal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(goal: Goal) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(goal: Goal)

    @Delete
    fun deleteOrder(goal: Goal)

    @Query("SELECT * FROM goals_table WHERE year=:yr")
    @Transaction
    fun getGoalWithBooks(yr: String) : GoalWithBooks

    @Query("SELECT * FROM goals_table ")
    @Transaction
    fun getGoalsWithBooks() : List<GoalWithBooks>
}