package com.example.videofeed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.videofeed.adapter.UserAdapter.ImageViewHolder
import com.example.videofeed.databinding.UserLayoutBinding
import com.example.videofeed.model.UserData

class UserAdapter : PagingDataAdapter<UserData, ImageViewHolder>(diffCallback) {

    inner class ImageViewHolder(val binding: UserLayoutBinding) : ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<UserData>() {
            override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            UserLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val list = getItem(position)

        holder.binding.apply {
            holder.itemView.apply {
                tvDescription.text = list?.firstName +" "+ list?.lastName
                tvEmail.text = list?.email

                Glide.with(rootView).load(list?.avatar).into(imageView)

            }
        }
    }

}