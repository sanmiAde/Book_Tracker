package com.sanmiaderibigbe.booktracker.ui.read

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sanmiaderibigbe.booktracker.R

class ReadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.read_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ReadFragment.newInstance())
                    .commitNow()
        }
    }

    companion object {

        private const val ARG_ID: String = " com.sanmiaderibigbe.booktracker.ui.read.ReadActivity"
        //Add order activity will be used for editing order and creating new order.
        fun newInstance(context: Context): Intent {
            return Intent(context, ReadActivity::class.java)
        }

    }
}
