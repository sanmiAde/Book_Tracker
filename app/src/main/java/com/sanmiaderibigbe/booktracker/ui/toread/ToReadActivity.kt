package com.sanmiaderibigbe.booktracker.ui.toread

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sanmiaderibigbe.booktracker.R
import com.sanmiaderibigbe.booktracker.ui.read.ReadActivity
import com.sanmiaderibigbe.booktracker.ui.ui.toread.ToReadFragment

class ToReadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.to_read_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ToReadFragment.newInstance())
                    .commitNow()
        }
    }

    companion object {

        private const val ARG_ID: String = " com.sanmiaderibigbe.booktracker.ui.read.TOReadActivity"
        //Add order activity will be used for editing order and creating new order.
        fun newInstance(context: Context): Intent {
            return Intent(context, ToReadActivity::class.java)
        }

    }

}
