package com.example.androidappnest.domain

import com.google.firebase.database.ValueEventListener

class GetPostsUseCase(private val postRepository: PostRepository) {
    fun getPosts(listener: ValueEventListener) {
        postRepository.getPosts(listener)
    }
}