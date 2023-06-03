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

    fun updateNewUser(user: User) {
        firebaseDatabaseService.updateNewUser(user)
    }
    fun updateNewFriend(myUser: UserFriend, otherUser: UserFriend) {
        firebaseDatabaseService.updateNewFriend(myUser, otherUser)
    }

    fun updateNewSentRequest(userID: String, userRequest: UserRequest){
        firebaseDatabaseService.updateNewSentRequest(userID, userRequest)
    }


    fun updateNewNotification(otherUserID: String, userNotification: UserNotification){
        firebaseDatabaseService.updateNewNotification(otherUserID, userNotification)
    }

    fun updateChatLastMessage(chatID: String, message: Message){
        firebaseDatabaseService.updateLastMessage(chatID, message)
    }


    fun updateNewChat(chat: Chat){
        firebaseDatabaseService.updateNewChat(chat)
    }

    fun updateUserProfileImageUrl(userID: String, url: String){
        firebaseDatabaseService.updateUserProfileImageUrl(userID, url)
    }

    fun removeNotification(userID: String, notificationID: String) {
        firebaseDatabaseService.removeNotification(userID, notificationID)
    }

    fun removeFriend(userID: String, friendID: String) {
        firebaseDatabaseService.removeFriend(userID, friendID)
    }

    fun removeSentRequest(otherUserID: String, myUserID: String) {
        firebaseDatabaseService.removeSentRequest(otherUserID, myUserID)
    }

    fun removeChat(chatID: String) {
        firebaseDatabaseService.removeChat(chatID)
    }

    fun removeMessages(messagesID: String){
        firebaseDatabaseService.removeMessages(messagesID)
    }


}