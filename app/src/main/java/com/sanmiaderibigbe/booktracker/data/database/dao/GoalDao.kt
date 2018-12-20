package com.sanmiaderibigbe.booktracker.data.database.dao

import android.arch.persistence.room.*
import com.sanmiaderibigbe.booktracker.data.model.Goal
import com.sanmiaderibigbe.booktracker.data.model.GoalWithBooks

@Dao
interface GoalDao {

    @Query("SELECT * FROM goals_table ORDER BY id")
    fun getGoals(): List<Goal>

    @Query("SELECT * FROM goals_table WHERE id =:goalid ")
    fun getGoal(goalid: Long): Goal

    @Query("SELECT * FROM goals_table WHERE year =:goalYear ")
    fun getGoalByYear(goalYear: String): Goal

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