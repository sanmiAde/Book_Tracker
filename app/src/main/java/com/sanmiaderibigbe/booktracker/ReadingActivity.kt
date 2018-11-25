package com.sanmiaderibigbe.booktracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sanmiaderibigbe.booktracker.ui.reading.ReadingFragment

class ReadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reading_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ReadingFragment.newInstance())
                    .commitNow()
        }
    }

}
