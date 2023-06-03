package com.example.android_chat_app.data.db.repository

import com.example.android_chat_app.data.db.entity.*
import com.example.android_chat_app.data.db.remote.FirebaseDataSource
import com.example.android_chat_app.data.db.remote.FirebaseReferenceChildObserver
import com.example.android_chat_app.data.db.remote.FirebaseReferenceValueObserver
import com.example.android_chat_app.data.Result
import com.example.android_chat_app.util.wrapSnapshotToArrayList
import com.example.android_chat_app.util.wrapSnapshotToClass

class DatabaseRepository {

    private val firebaseDatabaseService = FirebaseDataSource()


    fun updateUserState(userID: String, status: String){
        firebaseDatabaseService.updateUserStatus(userID, status)
    }

    fun updateNewMessage(messagesID: String, message: Message) {
        firebaseDatabaseService.pushNewMessage(messagesID, message)
    }

    fun updateNewUser(user: User){
        firebaseDatabaseService.updateNewUser(user)
    }

    fun updateNewSentRequest(myUser: UserFriend, otherUser: UserFriend){
        firebaseDatabaseService.updateNewFriend(myUser, otherUser)
    }






}