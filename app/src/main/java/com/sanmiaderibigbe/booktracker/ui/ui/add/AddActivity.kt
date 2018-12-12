package com.sanmiaderibigbe.booktracker.ui.ui.add

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.sanmiaderibigbe.booktracker.R
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.BookState
import kotlinx.android.synthetic.main.add_activity.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() , Validator.ValidationListener, AdapterView.OnItemSelectedListener, DatePickerFragment.InterfaceCommunicator  {

    override fun sendRequestCode(date: Date, viewId: Int) {
        when(viewId) {
            start_date_btn.id -> {
                updateStartDate(date)
            }
            end_date_btn.id -> {
                updateEndDate(date)
            }
        }
    }


    @NotEmpty
    private lateinit var bookNameEditText: EditText

    @NotEmpty
    private lateinit var  authorNameEditText: EditText

    @NotEmpty
    private lateinit var genreEditText: EditText

    @NotEmpty
    private lateinit var currentPageEditText: EditText

    @NotEmpty
    private lateinit var lastPageEditText: EditText

    private lateinit var bookRatingBar: RatingBar

    private lateinit var bookStateSpinner: Spinner

    private lateinit var validator: Validator

    private lateinit var viewModel: AddViewModel

    private lateinit var currentBookState : BookState


    private val REQUEST_DATE: Int = 1

    private val REQUEST_END_DATE : Int = 2

    private var rating: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity)
        viewModel = ViewModelProviders.of(this).get(AddViewModel::class.java)
        initValidator()
        initViews()
        initListener()
        initViewModel()
        updateEndDate(Date())
        updateStartDate(Date())

    }

    private fun initValidator() {
        validator = Validator(this)
        validator.setValidationListener(this)
    }

    private fun initViews() {
        bookNameEditText = findViewById(R.id.book_name_editTxt)
        authorNameEditText = findViewById(R.id.book_author_edit)
        genreEditText = findViewById(R.id.genre_edit)
        currentPageEditText = findViewById(R.id.current_page_edit_txt)
        lastPageEditText = findViewById(R.id.total_pages_edit_text)
        bookRatingBar = findViewById(R.id.book_rt)
        bookStateSpinner = findViewById(R.id.book_state_spinner)

        ArrayAdapter.createFromResource(
                this, R.array.books_state_array, android.R.layout.simple_spinner_item
        ).also {
            adapter -> adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1)
            bookStateSpinner.adapter = adapter
        }


        bookRatingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            rating = fl.toInt()
            Toast.makeText(this, rating.toString(), Toast.LENGTH_SHORT).show()
        }


    }

    private fun initListener(){
        bookStateSpinner.onItemSelectedListener = this
        submit_btn.setOnClickListener {
            validator.validate()
        }

        initDateDilog(start_date_btn, START_DIALOG_DATE,Date() )
        initDateDilog(end_date_btn, END_DIALOG_DATE, Date())
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        errors?.forEach{
            val view = it.view
            val message = it.getCollatedErrorMessage(this)
            if(view is EditText) {
                view.error = message
            }
        }
    }

    override fun onValidationSucceeded() {
        Toast.makeText(this, "Yay, Validation works", Toast.LENGTH_SHORT).show()
        viewModel.saveBook(getUserInput())
    }

    companion object {
        private const val ARG_ID: String = " com.sanmiaderibigbe.booktracker.ui.read.ReadActivity"
        private const val START_DIALOG_DATE = "StartDialogDate"
        private const val END_DIALOG_DATE = "EndDialogDate"
        //Add order activity will be used for editing order and creating new order.
        fun newInstance(context: Context): Intent {
            return Intent(context, AddActivity::class.java)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AddViewModel::class.java)

    }

    private fun getUserInput(): Book {
        val bookName = bookNameEditText.text.toString()
        val author  =  authorNameEditText.text.toString()
        val genre = genreEditText.text.toString()
        val currentPage = currentPageEditText.text.toString().toInt()
        val bookPages = lastPageEditText.text.toString().toInt()

        val bookState = currentBookState
        val currentDate = convertStringToDate(start_date_btn.text.toString())
        val year = getYear(currentDate)
        val endDate: Date?= convertStringToDate(end_date_btn.text.toString())
        Toast.makeText(this, currentDate.year.toString(), Toast.LENGTH_SHORT ).show()

        val book = Book(0, bookName, author, genre, currentPage,  bookPages,rating.toDouble(), year.toString(), state = bookState, created_at = currentDate, end_date = endDate,  updated_at = Date())

        return book
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        currentBookState = when(parent?.getItemAtPosition(pos)){
            "READING" -> {
                BookState.READING
            }
            "READ" -> {
                BookState.READ
            }
            "READ LATER" -> {
                BookState.TO_READ
            }
            else ->{
                BookState.READING
            }
        }
        Toast.makeText(this, currentBookState.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun getYear(date: Date): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        return  cal.get(Calendar.YEAR)

    }


    private fun initDateDilog(view: View, dialogConst : String, date: Date){
        view.setOnClickListener {
            val manager = supportFragmentManager
            val dialog = DatePickerFragment.newInstance(date, it.id)
            dialog.show(manager, dialogConst)
        }
    }


   private fun convertDateToString(date: Date): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(date)
    }

    private   fun convertStringToDate(date: String): Date {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.parse(date)
    }


    private fun updateStartDate(date: Date) {
        start_date_btn.text = convertDateToString(date)
    }

    private fun updateEndDate(date: Date) {
        end_date_btn.text = convertDateToString(date)
    }
}
