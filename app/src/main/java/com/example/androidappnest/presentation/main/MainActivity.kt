package com.example.androidappnest.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidappnest.R
import com.example.androidappnest.databinding.ActivityMainBinding
import com.example.androidappnest.domain.Post
import com.example.androidappnest.presentation.auth.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: PostViewModel
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[PostViewModel::class.java]
        auth = Firebase.auth

        binding.bSend.setOnClickListener {
            val email = auth.currentUser?.email
            val text = binding.edPost.text.toString()

            viewModel.addPost(Post(email, text))
        }

        binding.bLogOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
        }

        viewModel.getPost(postsListener)
        initRcView()
    }

    override fun onRestart() {
        super.onRestart()
        checkAuthState()
    }

    private fun initRcView() = with(binding) {
        adapter = PostAdapter()
        rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        rcView.adapter = adapter
    }

    private val postsListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val posts = mutableListOf<Post>()
            for (postSnapshot in snapshot.children) {
                val post = postSnapshot.getValue(Post::class.java)
                post?.let { posts.add(it) }
            }
            adapter.submitList(posts)
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    private fun checkAuthState() {
        if (auth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}