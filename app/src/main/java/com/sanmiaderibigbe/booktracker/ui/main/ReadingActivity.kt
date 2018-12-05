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
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.sanmiaderibigbe.booktracker.R
import com.sanmiaderibigbe.booktracker.adapters.BookListAdapter
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.ui.read.ReadActivity
import com.sanmiaderibigbe.booktracker.ui.toread.ToReadActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class ReadingActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, BookListAdapter.OnMenuClickHandler {

    override fun onClick(book: Book) {
        Toast.makeText(this, book.author, Toast.LENGTH_SHORT).show()
    }

    override fun onClick() {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var mViewModel: ReadingViewModel
    private val TAG: String = ReadingActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViewModel()
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        getBooks(initRecyclerView())

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
            adapter.setBooks(it)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
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
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initRecyclerView(): BookListAdapter {
        val adapter = BookListAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
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
            Toast.makeText(this,searchQuery.text, Toast.LENGTH_SHORT).show()
        }.setNegativeButton(getString(R.string.manual)) { dialogInterface: DialogInterface?, i: Int ->
            dialogInterface?.cancel()
        }.setNeutralButton( getString(R.string.cancel)) { dialogInterface: DialogInterface?, i: Int ->
            dialogInterface?.cancel()
        }.setView(searchDialogView)

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
