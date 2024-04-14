package com.example.androidappnest.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.androidappnest.R
import com.example.androidappnest.databinding.ActivitySignInBinding
import com.example.androidappnest.presentation.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.bSignIn.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(baseContext, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signIn(email, password).observe(this) {
                    if (it.isSuccess) {
                        Toast.makeText(
                            baseContext,
                            "Signed In.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        checkAuthState()
                    } else if (it.isFailure) {
                        Toast.makeText(
                            baseContext,
                            "Incorrect Email or Password.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        checkAuthState()
    }


    private fun checkAuthState() {
        if (auth.currentUser != null) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}