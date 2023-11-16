package com.example.videofeed.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.videofeed.dao.Note
import com.example.videofeed.databinding.ActivityDatarecordDetailBinding
import com.example.videofeed.utils.Constants
import com.example.videofeed.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class DataRecordDetail : AppCompatActivity() {

    private lateinit var datarecordViewModel: NoteViewModel
    private var recordId: Long = 0L
    private var recordIsLive: Boolean = false
    private var isEdit: Boolean = false
    private lateinit var binding: ActivityDatarecordDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDatarecordDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        setOnClick()
        getVisibility()

    }

    private fun getData() {

        datarecordViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        if (intent.hasExtra(Constants.DATA_RECORD_ID)) {
            recordId = intent.getLongExtra(Constants.DATA_RECORD_ID, 0L)
            recordIsLive = intent.getBooleanExtra(Constants.DATA_RECORD_ISLIVE, false)

            datarecordViewModel.get(recordId).observe(this, Observer {

                if (it != null) {
                    binding.datarecordId.text = it.id.toString()
                    binding.datarecordRecord.setText(it.title)
                    binding.checkRecord.isChecked = recordIsLive
                }
            })
            isEdit = true
        }
    }

    private fun setOnClick() {

        binding.btnSave.setOnClickListener { view ->
            val id = 0L
            val record = binding.datarecordRecord.text.toString()
            val isLive = binding.checkRecord.isChecked

            if (record.isBlank() or record.isEmpty()) {
                Snackbar.make(view, "Empty data is not allowed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                try {

                    val sdf = SimpleDateFormat("dd MMMM,yyyy - HH:mm", Locale.getDefault())

                    // Format the current date
                    val currentDate: String = sdf.format(Date())

                    val date = Date(System.currentTimeMillis())

                    // Format the date obtained from System.currentTimeMillis()
                    val simpleDateFormat =
                        SimpleDateFormat("dd MMMM,yyyy - HH:mm", Locale.getDefault())
                    simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
                    val formattedDate: String = simpleDateFormat.format(date)

                    // Parse the formatted date to obtain a Date object without crash
                    val parsedDate: Date = simpleDateFormat.parse(formattedDate)

                    // Use the parsed Date object in the Note object
                    val item =
                        Note(id = id, title = record, isLive = isLive, currentDate, parsedDate)
                    datarecordViewModel.insert(item)
                    finish()


                } catch (e: ParseException) {
                    e.printStackTrace()

                }
            }
        }


        binding.btnUpdate.setOnClickListener { view ->

            val id = binding.datarecordId.text.toString().toLong()
            val record = binding.datarecordRecord.text.toString()
            val isLive = binding.checkRecord.isChecked

            if (record.isBlank() or record.isEmpty()) {
                Snackbar.make(view, "Empty data is not allowed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                try {
                    val sdf = SimpleDateFormat("dd MMMM,yyyy - HH:mm", Locale.getDefault())

                    // Format the current date
                    val currentDate: String = sdf.format(Date())

                    val date = Date(System.currentTimeMillis())

                    // Format the date obtained from System.currentTimeMillis()
                    val simpleDateFormat =
                        SimpleDateFormat("dd MMMM,yyyy - HH:mm", Locale.getDefault())
                    simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
                    val formattedDate: String = simpleDateFormat.format(date)

                    // Parse the formatted date to obtain a Date object without crash
                    val parsedDate: Date = simpleDateFormat.parse(formattedDate)

                    // Use the parsed Date object in the Note object
                    val item =
                        Note(id = id, title = record, isLive = isLive, currentDate, parsedDate)
                    datarecordViewModel.update(item)
                    finish()

                } catch (e: ParseException) {
                    e.printStackTrace()

                }
            }
        }
    }

    private fun getVisibility() {
        if (isEdit) {
            binding.btnSave.visibility = View.GONE
        } else {
            binding.btnUpdate.visibility = View.GONE
        }
    }
}
