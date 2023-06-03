package com.example.android_chat_app.data.db.repository

import com.example.android_chat_app.data.db.remote.FirebaseAuthSource
import com.example.android_chat_app.data.db.remote.FirebaseAuthStateObServer
import com.example.android_chat_app.data.model.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.android_chat_app.data.Result



class AuthRepository {

    private val firebaseAuthServices = FirebaseAuthSource()

    fun observeAuthState(stateObServer: FirebaseAuthStateObServer, b: ((Result<FirebaseUser>) -> Unit)){
        firebaseAuthServices.attachAuthStateObserver(stateObServer, b)
    }

    fun loginUser(login: Login, b: ((Result<FirebaseUser>) -> Unit)){
        b.invoke(Result.loading)
    }

}