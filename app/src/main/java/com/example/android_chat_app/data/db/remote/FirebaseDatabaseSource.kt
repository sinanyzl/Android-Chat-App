package com.example.android_chat_app.data.db.remote

import android.database.CursorJoiner
import android.os.Message
import com.example.android_chat_app.data.db.entity.Chat
import com.example.android_chat_app.data.db.entity.User
import com.example.android_chat_app.data.db.entity.UserFriend
import com.example.android_chat_app.data.db.entity.UserNotification
import com.example.android_chat_app.data.db.entity.UserRequest
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*

class FirebaseDatabaseSource {

    companion object{
        val dbInstance = FirebaseDatabase.getInstance()
    }

    private fun refToPath(path: String): DatabaseReference{
        return dbInstance.reference.child(path)
    }


    private fun attachValueListenerToTaskCompletion(src: TaskCompletionSource<DataSnapshot>): ValueEventListener{
        return (object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                src.setException(Exception(error.message))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                src.setResult(snapshot)
            }
        })
    }


    private fun <T> attachValueListenerToBlock(resultClassName: Class<T>, b: ((Result<T>) -> Unit)): ValueEventListener{
        return (object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                b.invoke(Result.Error(error.message))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (wrapSnapshotToClass(resultClassName, snapshot) == null){
                    b.invoke(Result.Error(msg = snapshot.key))
                }else{
                    b.invoke(Result.Success(wrapSnapshotToClassName, snapshot))
                }
            }
        })
    }

    private fun <T> attachValueListenerToBlockWithList(resultClassName: Class<T>, b: ((Result<MutableList<T>>)-> Unit)): ValueEventListener{

        return (object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                b.invoke(Result.Error(error.message))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                b.invoke(Result.success(wrapSnapshotToArrayList(resultClassName, snapshot)))
            }
        })
    }

    private fun <T> attachChildListenerToBlock(resultClassName: Class<T>, b: ((Result<MutableList<T>>) -> Unit)): ValueEventListener{
        return (object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                b.invoke(Result.Success(wrapSnapshotToClass, snapshot))
            }

            override fun onCancelled(error: DatabaseError) {
                b.invoke(Result.Error(error.message))
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) { }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) { }
            override fun onChildRemoved(snapshot: DataSnapshot) { }
        })
    }


    fun updateUserProfileImageUrl(userID: String, url: String){
        refToPath("users/$userID/info/profileImageUrl").setValue(url)
    }

    fun updateUserStatus(userID: String, url: String){
        refToPath("users/$userID/info/status").setValue(url)
    }

    fun updateLastMessage(chatId: String, message: Message){
        refToPath("chats/$chatId/lastMessage").setValue(message)
    }


    fun updateNewFriend(myUser: UserFriend, otherUser: UserFriend){
        refToPath("users/${myUser.userID}/friends/${otherUser.userID}").setValue(otherUser)
        refToPath("users/${otherUser.userID}/friends/${myUser.userID}").setValue(myUser)
    }

    fun updateNewSentRequest(userID: String, userRequest: UserRequest){
        refToPath("users/${userID}/sentRequest/${userRequest}").setValue(userRequest)
    }

    fun updateNewNotification(otherUserID: String, userNotification: UserNotification){
        refToPath("users/${otherUserID}/sentRequest/${userNotification.userID}").setValue(userNotification)
    }


    fun updateNewUser(user: String){
        refToPath("users/${user.info.id}").setValue(user)
    }

    fun updateNewChat(chat: Chat){
        refToPath("chats/${chat.info.id}").setValue(chat)
    }



















}