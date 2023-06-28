package com.marwendevs.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null
    private var tvAgeInHours : TextView? = null
    private var tvAgeInDays : TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnDatePicker : Button = findViewById(R.id.btn_Date)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvAgeInHours = findViewById(R.id.tvAgeInHours)
        tvAgeInDays = findViewById(R.id.tvAgeInDays)
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }

    }

    private fun clickDatePicker(){
        //instance of the calendar
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val mon = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
        {
                _, selectedYear, selectedMonth, selectedDayOfMonth ->

            val selectedDate = "${selectedMonth+1},$selectedDayOfMonth,$selectedYear"
            tvSelectedDate?.text = selectedDate

            val sdf = SimpleDateFormat("MM,dd,yyyy", Locale.ENGLISH)

            val theDate = sdf.parse(selectedDate)
            theDate?.let {
                val selectedDateInMinutes = theDate.time / 60000
                val selectedDateInHours = selectedDateInMinutes / 60
                val selectedDateInDays = (selectedDateInHours / 24) + 1

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                currentDate?.let {
                    val currentDateInMinutes = currentDate.time / 60000
                    val currentDateInHours = currentDateInMinutes / 60
                    val currentDateInDay = (currentDateInHours / 24) +1
                    val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                    val differenceInHours = currentDateInHours - selectedDateInHours
                    val differenceInDays = currentDateInDay - selectedDateInDays
                    tvAgeInMinutes?.text = differenceInMinutes.toString()
                    tvAgeInHours?.text = differenceInHours.toString()
                    tvAgeInDays?.text = differenceInDays.toString()
                }

            }


        },year,mon,day
            )
    dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
    dpd.show()
    }
}