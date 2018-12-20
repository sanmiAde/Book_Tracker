package com.sanmiaderibigbe.booktracker.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.databinding.AdapterListBookItemBinding

class BookListAdapter(context: Context, val clickHandler: OnMenuClickHandler) : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    private var bookList: List<Book>? = null
    private var shouldHideRating: Boolean = false

   interface OnMenuClickHandler{
       fun onClick(view: View?, book: Book)

       fun onClick(book: Book)
   }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val binding = AdapterListBookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return bookList?.size ?: 0
    }

    private var shouldHideProgress: Boolean = false

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val book: Book? = bookList?.get(p1)
        holder.binding.book = book
        if (shouldHideProgress) {
            holder.binding.bookProgressTxt.visibility = View.INVISIBLE
        } else {
            val progress = book?.currentPage?.toFloat()?.div(book.numberOfPages.toFloat())?.times(100.00)
            holder.binding.bookProgressTxt.text = "${Math.round(progress?.times(100.0)?.div(100.0)!!)}% complete"
        }
        if (shouldHideRating) {
            holder.binding.ratingBar.visibility = View.INVISIBLE
        }
    }


    fun setBooks(books: List<Book>?, hideRating: Boolean = false, hideProgress: Boolean = false) {
        shouldHideRating = hideRating
        shouldHideProgress = hideProgress
        bookList = books
        notifyDataSetChanged()
    }

    inner class ViewHolder( val binding: AdapterListBookItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.itemMenu.setOnClickListener(this)
            binding.bookNameTxt.setOnClickListener(this)
        }

        override fun onClick(view: View?) {

            when(view?.id)  {
                binding.itemMenu.id -> {
                    clickHandler.onClick(view,binding.book!!)
                }
                binding.bookNameTxt.id -> {
                    clickHandler.onClick(binding.book!!)
                }

            }
        }

    }
}