package com.example.androidappnest.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidappnest.data.AuthRepositoryImpl
import com.example.androidappnest.domain.SignInUseCase
import com.example.androidappnest.domain.SignUpUseCase
import com.google.firebase.auth.AuthResult

class AuthViewModel : ViewModel() {
    private val repository = AuthRepositoryImpl
    private val signInUseCase = SignInUseCase(repository)
    private val signUpUseCase = SignUpUseCase(repository)

    fun signIn(email: String, password: String): LiveData<Result<AuthResult>> {
        val resultLiveData = MutableLiveData<Result<AuthResult>>()
        signInUseCase.signIn(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                resultLiveData.postValue(Result.success(it.result))
            } else {
                resultLiveData.postValue(Result.failure(it.exception!!))
            }
        }
        return resultLiveData
    }

    fun signUp(email: String, password: String): LiveData<Result<AuthResult>> {
        val resultLiveData = MutableLiveData<Result<AuthResult>>()
        signUpUseCase.signUp(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                resultLiveData.postValue(Result.success(it.result))
            } else {
                resultLiveData.postValue(Result.failure(it.exception!!))
            }
        }
        return resultLiveData
    }
}