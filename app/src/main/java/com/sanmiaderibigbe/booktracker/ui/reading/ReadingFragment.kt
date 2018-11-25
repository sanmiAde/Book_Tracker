package com.sanmiaderibigbe.booktracker.ui.reading

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanmiaderibigbe.booktracker.R

class ReadingFragment : Fragment() {

    companion object {
        fun newInstance() = ReadingFragment()
    }

    private lateinit var viewModel: ReadingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.reading_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ReadingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
