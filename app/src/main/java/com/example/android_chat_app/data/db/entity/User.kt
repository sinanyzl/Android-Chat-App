package com.example.android_chat_app.data.db.entity

import com.google.firebase.database.PropertyName

data class User (

    @get:PropertyName("info") @set:PropertyName("info") var info: UserInfo = UserInfo(),
    @get:PropertyName("friends") @set:PropertyName("friends") var friends: HashMap<String, UserFriend> = HashMap(),
    @get:PropertyName("notification") @set:PropertyName("notification") var notification: HashMap<String, UserNotification> = HashMap(),
    @get:PropertyName("sentRequest") @set:PropertyName("sentRequest") var sentRequest: HashMap<String, UserRequest> = HashMap()
)

data class UserFriend(
    @get:PropertyName("userId") @set:PropertyName("UserId")  var userID: String = ""
)

data class UserInfo(
    @get:PropertyName("id") @set:PropertyName("id") var id: String = "",
    @get:PropertyName("displayName") @set:PropertyName("displayName") var displayName: String = "",
    @get:PropertyName("status") @set:PropertyName("status") var status: String = "No Status",
    @get:PropertyName("profileImameUrl") @set:PropertyName("profileImameUrl") var profileImameUrl: String = "",
    @get:PropertyName("online") @set:PropertyName("online") var online: Boolean = false
)

data class UserNotification(
    @get:PropertyName("userID") @set:PropertyName("userID") var userID: String = ""
)

data class UserRequest(
    @get:PropertyName("userID") @set:PropertyName("userID") var userID: String = ""

)