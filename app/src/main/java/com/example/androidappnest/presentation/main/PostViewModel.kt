package com.example.androidappnest.presentation.main

import androidx.lifecycle.ViewModel
import com.example.androidappnest.data.PostRepositoryImpl
import com.example.androidappnest.domain.AddPostUseCase
import com.example.androidappnest.domain.GetPostsUseCase
import com.example.androidappnest.domain.Post
import com.google.firebase.database.ValueEventListener

class PostViewModel: ViewModel() {
    private val repository = PostRepositoryImpl
    private val addPostUseCase = AddPostUseCase(repository)
    private val getPostsUseCase = GetPostsUseCase(repository)

    fun addPost(post: Post) {
        addPostUseCase.addPost(post)
    }

    fun getPost(listener: ValueEventListener) {
        getPostsUseCase.getPosts(listener)
    }
}