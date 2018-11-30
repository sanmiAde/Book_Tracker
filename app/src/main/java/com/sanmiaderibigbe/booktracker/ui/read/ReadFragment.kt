package com.sanmiaderibigbe.booktracker.ui.read

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanmiaderibigbe.booktracker.R

class ReadFragment : Fragment() {

    companion object {
        fun newInstance() = ReadFragment()
    }

    private lateinit var viewModel: ReadViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.read_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ReadViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
