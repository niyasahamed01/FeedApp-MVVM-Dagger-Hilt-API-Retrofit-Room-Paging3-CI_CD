package com.example.videofeed.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.videofeed.activity.DataRecordDetail
import com.example.videofeed.dao.Note
import com.example.videofeed.databinding.DatarecordViewholderBinding
import com.example.videofeed.utils.Constants

class DataRecordAdapter : RecyclerView.Adapter<DataRecordAdapter.DataRecordViewHolder>() {

    private var itemsList = emptyList<Note>().toMutableList()

    private val onClickListener: View.OnClickListener = View.OnClickListener { v ->
        val item = v.tag as Note
        val intent = Intent(v.context, DataRecordDetail::class.java).apply {
            putExtra(Constants.DATA_RECORD_ID, item.id)
            putExtra(Constants.DATA_RECORD_ISLIVE, item.isLive)
        }
        v.context.startActivity(intent)
    }

    inner class DataRecordViewHolder(val binding: DatarecordViewholderBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataRecordViewHolder {
        return DataRecordViewHolder(
            DatarecordViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DataRecordViewHolder, position: Int) {
        val current = itemsList[position]
        holder.itemView.tag = current

        with(holder) {
            binding.datarecordViewholderRecord.text = current.title
            binding.datarecordViewholderId.text = current.id.toString()
            binding.isLive.text = current.date.toString()
            if (current.isLive.toString() == "true") {
                binding.tvIsLive.visibility = View.VISIBLE
            } else {
                binding.tvIsLive.visibility = View.GONE
            }
            itemView.setOnClickListener(onClickListener)
        }

    }

    internal fun setItems(items: List<Note>) {
        this.itemsList = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemsList.size
}