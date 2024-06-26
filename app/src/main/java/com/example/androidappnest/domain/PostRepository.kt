package com.example.androidappnest.domain

import com.google.firebase.database.ValueEventListener

interface PostRepository {
    fun addPost(post: Post)
    fun getPosts(listener: ValueEventListener)
}