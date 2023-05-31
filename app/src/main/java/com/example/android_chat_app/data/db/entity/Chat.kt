package com.example.android_chat_app.data.db.entity

import com.google.firebase.database.PropertyName

data class Chat (
    @get:PropertyName("lastMessages") @set:PropertyName("lastMessages") var lastName: Message = Message(),
    @get:PropertyName("info") @set:PropertyName("info") var info: ChatInfo = ChatInfo()
)

data class ChatInfo(
    @get:PropertyName("id") @set:PropertyName("id") var id: String = ""
)