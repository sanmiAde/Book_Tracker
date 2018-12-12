package com.sanmiaderibigbe.booktracker.ui.ui.toread

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
import android.widget.PopupMenu
import android.widget.Toast
import com.sanmiaderibigbe.booktracker.R
import com.sanmiaderibigbe.booktracker.adapters.BookListAdapter
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.ui.read.ReadActivity


class ToReadFragment : Fragment(), BookListAdapter.OnMenuClickHandler {

    override fun onClick(book: Book) {
        Toast.makeText(activity, book.author, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(view: View?, book: Book) {
        val popUp : PopupMenu = PopupMenu(activity!!, view )
        popUp.inflate(R.menu.to_read_item_menu)

        popUp.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_move_to_reading -> {
                    viewModel.moveToReading(book)
                    return@setOnMenuItemClickListener true
                }
                R.id.action_move_to_read-> {
                    viewModel.moveToReadLater(book)
                    return@setOnMenuItemClickListener true
                }
                R.id.action_book_delete -> {
                    viewModel.deleteBook(book)
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener true
                }
            }

        }
        popUp.show()

    }

    companion object {
        fun newInstance() = ToReadFragment()
    }

    private lateinit var viewModel: ToReadViewModel
    private val TAG: String  = ToReadFragment::class.java.name

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = initRecyclerView(view)
        //getBooks(initRecyclerView())
        getBooks(adapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ToReadViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.to_read_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    private fun initRecyclerView(view: View): BookListAdapter {
        val adapter = BookListAdapter(activity!!, this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return adapter
    }

    private fun getBooks(adapter: BookListAdapter) {
        viewModel.getBookList().observe(this, Observer { it ->
            Log.d(TAG, it.toString())
            adapter.setBooks(it)

        })
    }

}
