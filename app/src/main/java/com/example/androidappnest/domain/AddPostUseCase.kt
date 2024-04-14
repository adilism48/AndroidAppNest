package com.example.androidappnest.domain

class AddPostUseCase(private val postRepository: PostRepository) {
    fun addPost(post: Post) {
        postRepository.addPost(post)
    }
}