package com.example.android_chat_app.data.db.remote

import com.example.android_chat_app.data.model.CreateUser
import com.example.android_chat_app.data.model.Login
import com.example.android_chat_app.data.Result
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthSource {

    companion object{
        val authInstance: FirebaseAuth.getInstance()
    }

    private fun attachAuthStateListener(b: ((Result<FirebaseUser>) -> Unit)): FirebaseAuth.AuthStateListener{

        return FirebaseAuth.AuthStateListener {
            if (it.currentUser == null){
                b.invoke(Result.Error("No User"))
            }else {b.invoke(Result.Success(it.currentUser))}
        }
    }

    fun loginWithEmailAndPassword(login: Login): Task<AuthResult>{
        return authInstance.signInWithEmailAndPassword(login.email, login.password)
    }

    fun createUser(createUser: CreateUser): Task<AuthResult>{
        return authInstance.createUserWithEmailAndPassword(createUser.email, createUser.password)
    }

    fun logout(){
        authInstance.signOut()
    }


    fun attachAuthStateObserver(firebaseAuthStateObServer: FirebaseAuthStateObServer, b: ((Result<FirebaseUser>) -> Unit)){
        val listener = attachAuthStateObserver(b)
        firebaseAuthStateObServer.start(listener, authInstance)
    }

}

class FirebaseAuthStateObServer{
    private var authListener: FirebaseAuth.AuthStateListener? = null
    private var instance: FirebaseAuth? = null

    fun start(valueEventListener: FirebaseAuth.AuthStateListener, instance: FirebaseAuth){
        this.authListener = valueEventListener
        this.instance = instance
        this.instance!!.addAuthStateListener(authListener!!)
    }

    fun clear(){
        authListener?.let { instance?.removeAuthStateListener(it)!! }
    }

}