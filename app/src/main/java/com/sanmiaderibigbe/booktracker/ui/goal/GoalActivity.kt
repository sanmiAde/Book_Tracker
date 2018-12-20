package com.sanmiaderibigbe.booktracker.ui.goal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sanmiaderibigbe.booktracker.R

class GoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, GoalFragment.newInstance())
                    .commitNow()
        }
    }

    companion object {

        private const val ARG_ID: String = " com.sanmiaderibigbe.booktracker.ui.goal.GoalActivity"
        //Add order activity will be used for editing order and creating new order.
        fun newInstance(context: Context): Intent {
            return Intent(context, GoalActivity::class.java)
        }

    }


}
