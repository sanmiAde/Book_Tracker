package com.sanmiaderibigbe.booktracker.ui.read

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sanmiaderibigbe.booktracker.R
import com.sanmiaderibigbe.booktracker.adapters.BookListAdapter
import com.sanmiaderibigbe.booktracker.data.model.Book


class ReadFragment : Fragment(), BookListAdapter.OnMenuClickHandler {
    override fun onClick(book: Book) {
        Toast.makeText(activity, book.name, Toast.LENGTH_SHORT).show()
    }

    override fun onClick() {
        Toast.makeText(activity, "Hello", Toast.LENGTH_SHORT).show()
    }


    private lateinit var mViewModel: ReadViewModel
    //private lateinit var adapter: BookListAdapter
    private val TAG: String  = ReadActivity::class.java.name

    companion object {
        fun newInstance() = ReadFragment()
    }

    private lateinit var viewModel: ReadViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.read_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(ReadViewModel::class.java)
        val adapter = initRecyclerView(view)
        //getBooks(initRecyclerView())
        getBooks(adapter)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewMode

    }


    private fun initRecyclerView(view: View): BookListAdapter {
        val adapter = BookListAdapter(activity!!, this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return adapter
    }

    private fun getBooks(adapter: BookListAdapter) {
        mViewModel.getBookList().observe(this, Observer { it ->
            Log.d(TAG, it.toString())
            adapter.setBooks(it)

        })
    }

}
