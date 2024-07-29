package com.example.videofeed.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.videofeed.activity.DataRecordDetail
import com.example.videofeed.dao.Note
import com.example.videofeed.databinding.DatarecordViewholderBinding
import com.example.videofeed.utils.Constants

class DataRecordAdapter(private val context: Context) : RecyclerView.Adapter<DataRecordAdapter.DataRecordViewHolder>() {

    private var itemsList = listOf<Note>()

    inner class DataRecordViewHolder(val binding: DatarecordViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note, onClickListener: (Note) -> Unit) {
            with(binding) {
                datarecordViewholderRecord.text = note.title
                datarecordViewholderId.text = note.id.toString()
                isLive.text = note.date.toString()

                // Simplified visibility logic
                tvIsLive.visibility = if (note.isLive) View.VISIBLE else View.GONE

                root.setOnClickListener { onClickListener(note) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataRecordViewHolder {
        val binding = DatarecordViewholderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return DataRecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataRecordViewHolder, position: Int) {
        val currentNote = itemsList[position]
        holder.bind(currentNote, onClickListener)
    }

    override fun getItemCount() = itemsList.size

    fun setItems(newItems: List<Note>) {
        val diffResult = DiffUtil.calculateDiff(NoteDiffCallback(itemsList, newItems))
        itemsList = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    private val onClickListener: (Note) -> Unit = { note ->
        val intent = Intent(context, DataRecordDetail::class.java).apply {
            putExtra(Constants.DATA_RECORD_ID, note.id)
            putExtra(Constants.DATA_RECORD_ISLIVE, note.isLive)
        }
        context.startActivity(intent)
    }
}

class NoteDiffCallback(
    private val oldList: List<Note>,
    private val newList: List<Note>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}