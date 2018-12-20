package com.sanmiaderibigbe.booktracker.ui.goal

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.sanmiaderibigbe.booktracker.data.Repository
import com.sanmiaderibigbe.booktracker.data.model.Goal
import java.util.*

class GoalViewModel(application: Application) : AndroidViewModel(application) {
// TODO: Implement the ViewModel

    private val repository: Repository = Repository.getRepository(application)

    fun saveGoal(numberOfBooks: Int, year: String, createdDate: Date) {
        repository.saveGoal(Goal(numberOfBooks, year, true, createdDate, Date()))
    }

    fun getGoal(year: String): Goal? {
        return repository.getGoal(year)
    }
}
