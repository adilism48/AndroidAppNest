package com.example.androidappnest.presentation.main

import androidx.recyclerview.widget.DiffUtil
import com.example.androidappnest.domain.Post

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}