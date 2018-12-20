package com.sanmiaderibigbe.booktracker.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.RatingBar
import android.widget.Toast
import com.sanmiaderibigbe.booktracker.R
import com.sanmiaderibigbe.booktracker.adapters.BookListAdapter
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.ui.read.ReadActivity
import com.sanmiaderibigbe.booktracker.ui.toread.ToReadActivity
import com.sanmiaderibigbe.booktracker.ui.ui.add.AddActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class ReadingActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, BookListAdapter.OnMenuClickHandler {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mViewModel: ReadingViewModel
    private lateinit var adapter: BookListAdapter
    private val TAG: String = ReadingActivity::class.java.name


    override fun onClick(book: Book) {
        startActivity(AddActivity.newInstance(this, true, book))
        Toast.makeText(this, book.name, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(view: View?, book: Book) {
        val popUp : PopupMenu = PopupMenu(this, view )
        popUp.inflate(R.menu.reading_item_menu)
        popUp.setOnMenuItemClickListener {
           when(it.itemId){
                R.id.action_move_to_read -> {
                    initBookRating(book)
                    //Todo ask for rating

                    return@setOnMenuItemClickListener true
                }
               R.id.action_move_to_read_later -> {
                   mViewModel.moveToReadLater(book)

                   return@setOnMenuItemClickListener true
               }

               R.id.action_book_delete -> {
                   mViewModel.deleteBook(book)
                   return@setOnMenuItemClickListener true
               }
//               R.id.action_share_book -> {
//
//               }

               R.id.action_book_progress -> {
                   initUpdateBookDialog(book)

                   return@setOnMenuItemClickListener true
               }

                else -> {
                    return@setOnMenuItemClickListener true
                }
            }
        }

        popUp.show()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViewModel()
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter =initRecyclerView()
        getBooks(adapter)

        fab.setOnClickListener { view ->
            initSearchDialog()
        }

        initDrawer()

    }

    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ReadingViewModel::class.java)

    }

    private fun getBooks(adapter: BookListAdapter) {
        mViewModel.getBookList().observe(this, Observer { it ->
            adapter.setBooks(it, true)

        })
    }

    private fun initDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_read -> {
                startActivity(ReadActivity.newInstance(this))
            }

            R.id.nav_to_read -> {
                startActivity(ToReadActivity.newInstance(this))
            }


//            R.id.nav_goals -> {
//                startActivity(GoalActivity.newInstance(this))
//            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initRecyclerView(): BookListAdapter {
        val adapter = BookListAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.visibility == View.VISIBLE) {
                    fab.hide()
                } else if (dy < 0 && fab.visibility != View.VISIBLE) {
                    fab.show()
                }
            }
        })

        return adapter
    }

    /***
     * Shows search dialog. User call search throuh api or manually add books.
     */
    private fun initSearchDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        val searchDialogView  = this.layoutInflater.inflate(R.layout.dialog_seach, null)
        val searchQuery = searchDialogView.findViewById<EditText>(R.id.search_text)
        alertDialogBuilder.setTitle(getString(R.string.search_for_book))


        alertDialogBuilder.setPositiveButton(getString(R.string.search)) { dialogInterface, i ->

            //Repository.getRepository(application).getBookDataOnline("The book thief")
        }.setNegativeButton(getString(R.string.manual)) { dialogInterface: DialogInterface?, i: Int ->
            startActivity(AddActivity.newInstance(this, false, null))
        }.setNeutralButton( getString(R.string.cancel)) { dialogInterface: DialogInterface?, i: Int ->
            dialogInterface?.cancel()
        }.setView(searchDialogView)

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun initUpdateBookDialog(book: Book) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        val updateProgressDialogView = this.layoutInflater.inflate(R.layout.dialog_book_progress, null)
        val currentPage = updateProgressDialogView.findViewById<EditText>(R.id.current_book_page)
        alertDialogBuilder.setTitle(getString(R.string.update_book_progress))



        alertDialogBuilder.setPositiveButton(getString(R.string.okay)) { dialogInterface, i ->
            mViewModel.updateBookProgress(currentPage.text.toString().toInt(), book)
            dialogInterface?.cancel()


        }.setNegativeButton(getString(R.string.cancel_move_to_read)) { dialogInterface: DialogInterface?, i: Int ->
            dialogInterface?.cancel()
        }.setView(updateProgressDialogView)

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun initBookRating(book: Book) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        val ratingDialogView = this.layoutInflater.inflate(R.layout.dialog_rating, null)
        val ratingBar = ratingDialogView.findViewById<RatingBar>(R.id.book_rating)
        var updatedBook: Book = Book(0, "", "", "", 0, 0, 0.0, "", null, null, null, null, Date())

        ratingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            updatedBook = book.copy(rating = fl.toDouble(), currentPage = book.numberOfPages)
            //pass this to viewmodel.

        }
        ratingBar.rating
        alertDialogBuilder.setTitle("Rate ${book.name}")

        alertDialogBuilder.setPositiveButton(getString(R.string.okay)) { dialogInterface, i ->
            mViewModel.moveToRead(updatedBook)
            dialogInterface?.cancel()


        }.setNegativeButton(getString(R.string.cancel_move_to_read)) { dialogInterface: DialogInterface?, i: Int ->
            dialogInterface?.cancel()
        }.setView(ratingDialogView)

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}
