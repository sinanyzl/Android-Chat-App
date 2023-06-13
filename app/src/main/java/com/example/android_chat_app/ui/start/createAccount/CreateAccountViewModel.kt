package com.example.android_chat_app.ui.start.createAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_chat_app.data.Event
import com.example.android_chat_app.data.Result
import com.example.android_chat_app.data.db.entity.User
import com.example.android_chat_app.data.db.repository.AuthRepository
import com.example.android_chat_app.data.db.repository.DatabaseRepository
import com.example.android_chat_app.data.model.CreateUser
import com.example.android_chat_app.ui.DefaultViewModel
import com.example.android_chat_app.util.isEmailValid
import com.example.android_chat_app.util.isTextValid
import com.google.firebase.auth.FirebaseUser


class CreateAccountViewModel : DefaultViewModel(){

    private val dbRepository = DatabaseRepository()
    private val authRepository = AuthRepository()
    private val mIsCreatedEvent = MutableLiveData<Event<FirebaseUser>>()

    val isCreatedEvent: LiveData<Event<FirebaseUser>> = mIsCreatedEvent
    val displayNameText = MutableLiveData<String>()
    val emailText = MutableLiveData<String>()
    val passwordText = MutableLiveData<String>()
    val isCreatingAccount = MutableLiveData<Boolean>()

    private fun createdAccount(){
        isCreatingAccount.value = true
        val createUser =
            CreateUser(displayNameText.value!!, emailText.value!!, passwordText.value!!)

        authRepository.createUser(createUser) {
            result: Result<FirebaseUser> ->
            onResult(null, result)
            if (result is Result.Success){
                mIsCreatedEvent.value = Event(result.data!!)
                dbRepository.updateNewUser(User().apply {
                    info.id = result.data.uid
                    info.displayName = createUser.displayName
                })
            }
            if (result is Result.Success || result is Result.Error) isCreatingAccount.value = false
        }
    }

    fun createAccountPressed(){
        if (!isEmailValid(2, displayNameText.value)){
            mSnackbarText.value = Event("Display name is too short")
            return
        }

        if (!isEmailValid(emailText.value.toString())){
            mSnackbarText.value = Event("Invalid email format")
            return
        }

        if (!isTextValid(6, passwordText.value)){
            mSnackbarText.value = Event("Password is too short")
            return
        }
        createdAccount()
    }


}