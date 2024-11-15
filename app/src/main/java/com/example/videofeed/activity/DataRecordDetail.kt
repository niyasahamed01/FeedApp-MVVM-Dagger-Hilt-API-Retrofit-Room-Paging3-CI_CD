package com.example.videofeed.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.videofeed.dao.Note
import com.example.videofeed.databinding.ActivityDatarecordDetailBinding
import com.example.videofeed.utils.Constants
import com.example.videofeed.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class DataRecordDetail : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    private var recordId: Long = 0L
    private var recordIsLive: Boolean = false
    private var isEdit: Boolean = false
    lateinit var binding: ActivityDatarecordDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDatarecordDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeViewModel()
        handleIntentData()
        setupClickListeners()
        updateVisibility()
    }

    private fun initializeViewModel() {
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
    }

    private fun handleIntentData() {
        intent?.let {
            recordId = it.getLongExtra(Constants.DATA_RECORD_ID, 0L)
            recordIsLive = it.getBooleanExtra(Constants.DATA_RECORD_ISLIVE, false)

            if (recordId != 0L) {
                noteViewModel.get(recordId).observe(this) { note ->
                    note?.let {
                        binding.dataRecordId.text = it.id.toString()
                        binding.dataRecordRecord.setText(it.title)
                        binding.checkRecord.isChecked = it.isLive
                        isEdit = true
                        updateVisibility()
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener { view ->
            val record = binding.dataRecordRecord.text.toString()
            if (record.isBlank()) {
                showSnackbar(view, "Empty data is not allowed")
            } else {
                saveNote(record, binding.checkRecord.isChecked)
            }
        }

        binding.btnUpdate.setOnClickListener { view ->
            val record = binding.dataRecordRecord.text.toString()
            if (record.isBlank()) {
                showSnackbar(view, "Empty data is not allowed")
            } else {
                updateNote(
                    id = binding.dataRecordId.text.toString().toLong(),
                    title = record,
                    isLive = binding.checkRecord.isChecked
                )
            }
        }
    }

    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    fun saveNote(title: String, isLive: Boolean) {
        val currentDate = getCurrentFormattedDate()
        val item = Note(
            id = 0L,
            title = title,
            isLive = isLive,
            timeStamp = currentDate.time.toString(), // Use timestamp for storage
            date = currentDate
        )
        noteViewModel.insert(item)
        finish()
    }

    private fun updateNote(id: Long, title: String, isLive: Boolean) {
        val currentDate = getCurrentFormattedDate()
        val item = Note(
            id = id,
            title = title,
            isLive = isLive,
            timeStamp = currentDate.time.toString(), // Use timestamp for storage
            date = currentDate
        )
        noteViewModel.update(item)
        finish()
    }

    fun getCurrentFormattedDate(): Date {
        val dateFormat = SimpleDateFormat("dd MMMM,yyyy - HH:mm", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        return Date() // Return current date and time
    }

    private fun updateVisibility() {
        binding.btnSave.visibility = if (isEdit) View.GONE else View.VISIBLE
        binding.btnUpdate.visibility = if (isEdit) View.VISIBLE else View.GONE
    }
}