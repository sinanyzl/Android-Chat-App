package com.example.android_chat_app.data.db.repository

import com.example.android_chat_app.data.db.remote.FirebaseAuthSource
import com.example.android_chat_app.data.db.remote.FirebaseAuthStateObServer
import com.example.android_chat_app.data.model.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.android_chat_app.data.Result
import com.example.android_chat_app.data.model.CreateUser


class AuthRepository {

    private val firebaseAuthService = FirebaseAuthSource()

    fun observeAuthState(stateObServer: FirebaseAuthStateObServer, b: ((Result<FirebaseUser>) -> Unit)){
        firebaseAuthService.attachAuthStateObserver(stateObServer, b)
    }

    fun loginUser(login: Login, b: ((Result<FirebaseUser>) -> Unit)){
        b.invoke(Result.loading)
        firebaseAuthService.loginWithEmailAndPassword(login).addOnSuccessListener{
            b.invoke(Result.Success(it.user))
        }.addOnFailureListener{
            b.invoke(Result.Error(msg = it.message))
        }
    }

    fun createUser(createUser: CreateUser, b: ((Result<FirebaseUser>) -> Unit)){
        b.invoke(Result.Loading)
        firebaseAuthService.createUser(createUser).addOnSuccessListener{
            b.invoke(Result.Success(it.user))
        }.addOnFailureListener{
            b.invoke(Result.Error(msg = it.message))
        }
    }

    fun logoutUser(){
        firebaseAuthService.logout()
    }

}