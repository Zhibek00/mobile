package com.example.restaurantapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.R
import com.example.restaurantapp.adapters.TimeAdapter
import com.example.restaurantapp.models.DataXXXXX
import com.example.restaurantapp.models.ReservationRequest
import com.example.restaurantapp.models.Row
import com.example.restaurantapp.viewmodels.ReservationViewModel
import com.example.restaurantapp.viewmodels.RestaurantViewModel
import java.text.SimpleDateFormat
import java.util.*


class ReservationFragment : Fragment() {
    var ocassion: String = ""
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ReservationViewModel
    private lateinit var guestsTextView: TextView
    private lateinit var imgTable: ImageView
    private lateinit var localStorage: LocalStorage
    private lateinit var reserveInfo: ReservationRequest
    var tableType:Int = 0
    var timeReserve:Int = 0
    lateinit var tableList :List<DataXXXXX>
    var uid = ""
    var tid: String = ""

    val drawableList = listOf(
        R.drawable.table_1,
        R.drawable.table_2,
        R.drawable.table_3,
        R.drawable.table_4,
        R.drawable.table_5,
        R.drawable.table_6,
        R.drawable.table_7,
        R.drawable.table_8
    )
    private lateinit var adapter: TimeAdapter
    lateinit var timeList: List<String>
    var maxGuests = 0
    var numGuests = 1
    var calendar = Calendar.getInstance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize your localStorage here when the Fragment is attached to an activity
        localStorage = LocalStorage(requireActivity().application)
        uid= localStorage.getString("uid","null")
        Log.i("g", uid)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(com.example.restaurantapp.R.layout.fragment_reservation, container, false)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(
            ReservationViewModel::class.java)


        val receivedData: Row? = arguments?.getParcelable("data_key")
        viewModel.tableList(receivedData?.rid!!)
        viewModel.tableData.observe(viewLifecycleOwner, Observer { data->
            if (data != null && data.isNotEmpty()) {
                // Assuming data is a list of some type, check if it's not null and not empty
                maxGuests = data[data.size - 1].type
                tableList = data
            } else {
                // Handle the case when tableData is null or empty
            }
        })
        val plusImageView = view.findViewById<ImageView>(R.id.plus)
        val minusImageView = view.findViewById<ImageView>(R.id.minus)
        guestsTextView = view.findViewById(R.id.guest_num)
        imgTable = view.findViewById(R.id.img_table)
        val textView1 = view.findViewById<TextView>(R.id.text1)
        val textView2 = view.findViewById<TextView>(R.id.text2)
        val textView3 = view.findViewById<TextView>(R.id.text3)
        val textView4 = view.findViewById<TextView>(R.id.text4)
        val textView5 = view.findViewById<TextView>(R.id.text5)

        val calendarView: CalendarView = view.findViewById(com.example.restaurantapp.R.id.calendarView)

        val editText1 = view.findViewById<EditText>(R.id.name)
        val editText2 = view.findViewById<EditText>(R.id.phone)
        val editText3 = view.findViewById<EditText>(R.id.comment)
        val nameValue = localStorage.getString("fullName", "")
        val phoneValue = localStorage.getString("phone", "")

        // Update UI with user data
        editText1.setText(nameValue)
        editText2.setText(phoneValue)

        val preOrderBtn = view.findViewById<AppCompatButton>(R.id.pre_order_btn)

        adapter = TimeAdapter(requireContext(), emptyList())

        timeListGenerator(receivedData!!.workstarttime, receivedData.workendtime)
        recyclerView = view.findViewById(R.id.recycler_time)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        preOrderBtn.setOnClickListener{

            val timeString = timeList[adapter.selectedItemPosition]
            val timeComponents = timeString.split(":")
            if (timeComponents.size == 2) {
                val hour = timeComponents[0].toInt()
                val minute = timeComponents[1].toInt()

                // Set the time components
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)

                // Convert to seconds
                val timeInSeconds = calendar.timeInMillis / 1000
                timeReserve = timeInSeconds.toInt()
                Log.i("TimeInSeconds", timeInSeconds.toString())
            }
            val fragmentB = ReservationFinalFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(
                R.id.frame_main,
                fragmentB
            ) // R.id.fragment_container is the id of the container in your layout

            val uid = localStorage.getString("uid") ?: ""
            val rid = receivedData.rid.toString()
            val contacts = editText2.text.toString()
            val message = editText3.text.toString()
            val occasion = ocassion
            val name = editText1.text.toString()
            val reservationStartTime = timeReserve
            val guestsCount = guestsTextView.text.toString().toInt()
            val tableTypeInfo = tableTypeIdentifier(guestsCount)
            val tableType = tableTypeInfo?.type ?: 0
            val tid = tableTypeInfo?.tid ?: ""

            val reserveInfo = ReservationRequest(
                uid = uid,
                rid = rid,
                contacts = contacts,
                message = message,
                occasion = occasion,
                name = name,
                reservationStartTime = reservationStartTime,
                tableType = tableType,
                tid = tid
            )



            val bundle = Bundle().apply {
                putParcelable("data_key", receivedData)
                putParcelable("data_key1", reserveInfo)
                putString("data", guestsTextView.text as String?)

            }
            fragmentB.arguments = bundle
            transaction.addToBackStack(null) // Optionally add to back stack, so the user can navigate back to FragmentA
            transaction.commit()
        }
        plusImageView.setOnClickListener {
            incrementGuests()
        }

        minusImageView.setOnClickListener {
            decrementGuests()
        }

        textView1.setOnClickListener { selectTextView(it) }
        textView2.setOnClickListener { selectTextView(it) }
        textView3.setOnClickListener { selectTextView(it) }
        textView4.setOnClickListener { selectTextView(it) }
        textView5.setOnClickListener { selectTextView(it) }

        val locale = Locale("en")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context!!.applicationContext.resources.updateConfiguration(config, null)


        calendarView.setOnDateChangeListener { calView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->
            // Create a Calendar instance
            // Set the selected date components
            calendar.set(year, month, dayOfMonth)
            calView.setDate(calendar.timeInMillis, true, true)
            Log.d("SelectedDate", "$dayOfMonth/${month + 1}/$year")
            // Check if adapter and timeList are properly initialized

            if (adapter != null && timeList.isNotEmpty() && adapter.selectedItemPosition >= 0 && adapter.selectedItemPosition < timeList.size) {
                val timeString = timeList[adapter.selectedItemPosition]
                val timeComponents = timeString.split(":")
                if (timeComponents.size == 2) {
                    val hour = timeComponents[0].toInt()
                    val minute = timeComponents[1].toInt()

                    // Set the time components
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)

                    // Convert to seconds
                    val timeInSeconds = calendar.timeInMillis / 1000
                    Log.i("TimeInSeconds", timeInSeconds.toString())
                } else {
                    Log.e("Error", "Invalid time format")
                }
            } else {
                Log.e("Error", "Adapter not initialized, time list is empty, or selected item position is invalid")
            }
        }

        return view
    }




    @SuppressLint("ResourceAsColor")
    private fun selectTextView(view: View) {
        // Find the parent view that contains all the TextViews
        val parentView = view.parent as ViewGroup

        // Reset background tint and text color for all TextViews
        parentView.findViewById<TextView>(R.id.text1).apply {
            backgroundTintList = null
            setTextColor(resources.getColor(R.color.black))

        }
        parentView.findViewById<TextView>(R.id.text2).apply {
            backgroundTintList = null
            setTextColor(resources.getColor(R.color.black))
        }
        parentView.findViewById<TextView>(R.id.text3).apply {
            backgroundTintList = null
            setTextColor(resources.getColor(R.color.black))
        }
        parentView.findViewById<TextView>(R.id.text4).apply {
            backgroundTintList = null
            setTextColor(resources.getColor(R.color.black))
        }
        parentView.findViewById<TextView>(R.id.text5).apply {
            backgroundTintList = null
            setTextColor(resources.getColor(R.color.black))
        }

        // Set background tint and text color for the selected TextView
        (view as TextView).apply {
            backgroundTintList = resources.getColorStateList(R.color.blue)
            setTextColor(resources.getColor(R.color.white))
            ocassion = text as String

        }
    }
    private fun incrementGuests() {
        val currentGuests = guestsTextView.text.toString().toInt()
        if (currentGuests < maxGuests) {
            guestsTextView.text = (currentGuests + 1).toString()
            imgTable.setImageResource(drawableList.get(currentGuests ))

        }
    }

    private fun decrementGuests() {
        val currentGuests = guestsTextView.text.toString().toInt()
        if (currentGuests > 1) {
            guestsTextView.text = (currentGuests - 1).toString()
            imgTable.setImageResource(drawableList.get(currentGuests - 2))

        }
    }
    fun timeListGenerator(startTimeMillis: Int, endTimeMillis: Int) {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val endTimeCalendar = Calendar.getInstance()
        endTimeCalendar.timeInMillis = endTimeMillis.toLong()*1000

        calendar.timeInMillis = startTimeMillis.toLong()*1000
        val intervals = mutableListOf<String>()
        if (calendar.timeInMillis >= endTimeMillis.toLong() * 1000) {
            // Reset minutes and add one day if needed
            endTimeCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        // Loop until we reach the end time
        while (calendar.timeInMillis < endTimeCalendar.timeInMillis) {
            intervals.add(dateFormat.format(calendar.time))
            calendar.add(Calendar.MINUTE, 30)


        }

        // Add the end time to the intervals
        timeList = intervals
        adapter.updateData(timeList)
        // Print the intervals array convert from utc to localletimezone
    }
    fun tableTypeIdentifier(guests: Int): DataXXXXX?{
        val validTableTypes = tableList.filter { table -> table.type >= guests }
        Log.i("validTableTypes", validTableTypes.toString())
        // If there are no valid table types, return null
        if (validTableTypes.isEmpty()) return null

        // Sort valid table types by table count in ascending order
        val sortedTableTypes = validTableTypes.sortedBy { it.tableCount }

        // Return the table type with the smallest table count
        return sortedTableTypes.first()

    }

}