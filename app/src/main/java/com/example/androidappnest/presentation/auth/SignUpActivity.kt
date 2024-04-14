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
import com.example.androidappnest.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.bSignUp.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(baseContext, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signUp(email, password).observe(this) {
                    if (it.isSuccess) {
                        Toast.makeText(
                            baseContext,
                            "Account created",
                            Toast.LENGTH_SHORT,
                        ).show()
                        startActivity(Intent(this, SignInActivity::class.java))
                    } else if (it.isFailure) {
                        Toast.makeText(
                            baseContext,
                            "Already been registered",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }
        binding.bBack.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}