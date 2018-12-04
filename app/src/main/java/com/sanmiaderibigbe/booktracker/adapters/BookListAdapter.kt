package com.sanmiaderibigbe.booktracker.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.databinding.AdapterListBookItemBinding

class BookListAdapter(context: Context) : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    private var bookList: List<Book>? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val binding = AdapterListBookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return bookList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
       val book: Book? = bookList?.get(p1)
        holder.binding.book = book
    }



    fun setBooks(books: List<Book>?){
        bookList = books
        notifyDataSetChanged()
    }

    class ViewHolder( val binding: AdapterListBookItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}