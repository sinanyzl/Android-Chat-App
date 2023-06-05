package com.example.android_chat_app.data.model

import com.example.android_chat_app.data.db.entity.Chat
import com.example.android_chat_app.data.db.entity.UserInfo

data class ChatWithUserInfo (
    var mChat: Chat,
    var mUserInfo: UserInfo

    )