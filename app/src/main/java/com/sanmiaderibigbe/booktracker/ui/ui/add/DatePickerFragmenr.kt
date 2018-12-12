package com.sanmiaderibigbe.booktracker.ui.ui.add

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import com.sanmiaderibigbe.booktracker.R
import java.util.*

class DatePickerFragment : DialogFragment() {

    private lateinit var datePicker: DatePicker
    private lateinit var interfaceCommunicator: InterfaceCommunicator

    interface  InterfaceCommunicator{
        fun sendRequestCode(date: Date, viewId: Int)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        fun getData(): Triple<Int, Int, Int> {
            val date: Date = arguments?.getSerializable(ARG_DATE) as Date


            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date
            val year: Int = calendar.get(Calendar.YEAR)
            val month: Int = calendar.get(Calendar.MONTH)
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
            return Triple(year, month, day)
        }

        val (year: Int, month: Int, day: Int) = getData()

        val view: View = LayoutInflater.from(activity!!).inflate(R.layout.dialog_date, null)
        datePicker = view.findViewById(R.id.dialog_date_picker)
        datePicker.init(year, month, day, null)

        return AlertDialog.Builder(activity!!).setView(view).setPositiveButton(android.R.string.ok) { dialogInterface, i ->
            val dueYear: Int = datePicker.year
            val dueMonth: Int = datePicker.month
            val dueDay: Int = datePicker.dayOfMonth

            val date: Date = GregorianCalendar(dueYear, dueMonth, dueDay).time
            val viewId = arguments?.getInt(ARG_VIEW_ID)
            val interfaceCommunicator = activity as InterfaceCommunicator
            interfaceCommunicator.sendRequestCode(date, viewId!!)

        }.create()
    }



    private fun sendResult(resultCode: Int, date: Date, viewId: Int) {
        if (targetFragment == null) {
            return
        }

        val intent: Intent = Intent()
        intent.putExtra(EXTRA_DATE, date)
        intent.putExtra(EXTRA_VIEW_ID, viewId)

        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }

    companion object {
        const val EXTRA_DATE: String = "com.sanmiaderibigbe.booktracker.datepicker.startDate"
        const val EXTRA_VIEW_ID: String = "com.sanmaderibigbe.booktracker.datepicker.endDate"
        private  const val ARG_DATE = "date"
        private const  val ARG_VIEW_ID = "viewId"

        fun newInstance(date: Date, viewID: Int): DatePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_DATE, date)

            args.putInt(ARG_VIEW_ID, viewID)
            val fragment = DatePickerFragment()
            fragment.arguments = args
            return fragment
        }

    }
}