package com.sanmiaderibigbe.booktracker.ui.goal

import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.sanmiaderibigbe.booktracker.R
import kotlinx.android.synthetic.main.goal_fragment.*
import java.util.*

class GoalFragment : Fragment() {

    companion object {
        fun newInstance() = GoalFragment()
    }

    private lateinit var viewModel: GoalViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.goal_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GoalViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_goal_fab.setOnClickListener { initAddGoalDialog() }

    }

    private fun initAddGoalDialog() {

        val alertDialogBuilder = AlertDialog.Builder(activity!!)
        val addDialogView = this.layoutInflater.inflate(R.layout.dialog_add_goal, null)
        val addGoal = addDialogView.findViewById<EditText>(R.id.num_of_books_to_read)
        alertDialogBuilder.setTitle(getString(R.string.search_for_book))


        alertDialogBuilder.setPositiveButton(getString(R.string.okay)) { dialogInterface, i ->
            val numberOfBooks = addGoal.text.toString().toInt()
            if (numberOfBooks < 0) {
                Toast.makeText(activity, "Error less than 0", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, addGoal.text.toString(), Toast.LENGTH_SHORT).show()
                viewModel.saveGoal(numberOfBooks, getYear(Date())!!, Date())
            }

            dialogInterface?.cancel()

        }.setNegativeButton(getString(R.string.cancel_move_to_read)) { dialogInterface: DialogInterface?, i: Int ->
            dialogInterface?.cancel()
        }.setView(addDialogView)

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()


    }

    private fun getYear(date: Date?): String? {
        if (date != null) {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal.get(Calendar.YEAR).toString()
        }
        return null
    }
}
