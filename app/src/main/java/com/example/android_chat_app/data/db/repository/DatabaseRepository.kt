package com.example.android_chat_app.data.db.repository

import com.example.android_chat_app.data.db.entity.*
import com.example.android_chat_app.data.db.remote.FirebaseDataSource
import com.example.android_chat_app.data.db.remote.FirebaseReferenceChildObserver
import com.example.android_chat_app.data.db.remote.FirebaseReferenceValueObserver
import com.example.android_chat_app.data.Result
import com.example.android_chat_app.util.wrapSnapshotToArrayList
import com.example.android_chat_app.util.wrapSnapshotToClass
import com.example.android_chat_app.data.db.entity.*
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

    fun loadUser(userID: String, b: ((Result<User>) -> Unit)){
        firebaseDatabaseService.loadUserTask(userID).addOnSuccessListener{
            b.invoke(Result.Success(wrapSnapshotToClass(User::class.java, it)))
        }.addOnFailureListener{ b.invoke(Result.Error(it.message)) }
    }

    fun loadUserInfo(userID: String, b: ((Result<UserInfo>) -> Unit)){
        firebaseDatabaseService.loadUserInfoTask(userID).addOnSuccessListener{
            b.invoke(Result.Success(wrapSnapshotToClass(Chat::class.java, it)))
        }.addOnFailureListener{b.invoke(Result.Error(it.message))}
    }

    fun loadChat(chatID: String, b: ((Result<Chat>) -> Unit)){
        firebaseDatabaseService.loadChatTask(chatID).addOnSuccessListener{
            b.invoke(Result.Success(wrapSnapshotToClass(Chat::class.java, it)))
        }.addOnFailureListener{b.invoke(Result.Error(it.message))}
    }


    fun loadUsers(b: ((Result<MutableList<User>) -> Unit)){
        b.invoke(Result.Loading)
        firebaseDatabaseService.loadUserTask().addOnSuccessListener{
            val userList = wrapSnapshotToArrayList(User::class.java, it)
            b.invoke(Result.Success(userList))
        }.addOnFailureListener{ b.invoke(Result.Error(it.message))}
    }


    fun loadFriends(userID: String, b: ((Result<List<UserFriend>>) -> Unit)){
        b.invoke(Result.Loading)
        firebaseDatabaseService.loadFriendsTask(userID).addOnSuccessListener{
            var friendList = wrapSnapshotToArrayList(UserFriend::class.java, it)
            b.invoke(Result.Success(friendList))
        }.addOnFailureListener{b.invoke(Result.Error(it.message))}
    }


    fun loadNotifications(userID: String, b: ((Result<MutableList<UserNotification>>) -> Unit)){
        b.invoke(Result.Loading)
        firebaseDatabaseService.loadNotificationsTask(userID).addOnSuccessListener{
            val notificationsList = wrapSnapshotToArrayList(UserNotification::class.java, it)
            b.invoke(Result.Success(notificationsList))
        }.addOnFailureListener{b.invoke(Result.Error(it.message))}
    }


    fun loadAndOberveUser(userID: String, observe: FirebaseReferenceValueObserver, b: ((Result<User>) -> Unit)){
        firebaseDatabaseService.attachUserObserver(User::class.java, userID, observe, b)
    }
    
    fun loadAndObserveUserInfo(userID: String, observe: FirebaseReferenceValueObserver, b: ((Result<UserInfo>) -> Unit)){
        firebaseDatabaseService.attachUserInfoObserver(UserInfo::class.java, userID, observe, b)
    }


    fun loadAndObserveUserNotifications(userID: String, observe: FirebaseReferenceValueObserver, b: ((Result<MutableList<UserNotification>>) -> Unit)){
        firebaseDatabaseService.attachUserNotificationsObserver(UserNotification::class.java, userID, observe, b)
    }


    fun loadAndObserveMessagesAdded(messagesID: String, observer: FirebaseReferenceChildObserver, b: ((Result<Message>) -> Unit)){
        firebaseDatabaseService.attachMessagesObserver(Message::class.java, messagesID, observer, b)
    }

    fun  loadAndObserveChat(chatID: String, observe: FirebaseReferenceValueObserver, b: ((Result<Chat>) -> Unit)){
        firebaseDatabaseService.attachChatObserver(Chat::class.java, chatID, observe, b)
    }

}