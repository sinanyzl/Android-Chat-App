package com.example.android_chat_app.util

import com.google.firebase.database.DataSnapshot

fun <T> wrapSnapshotToClass(className: Class<T>, snap: DataSnapshot): T?{
    return snap.getValue(className)
}


fun <T> wrapSnapshotToArrayList(className: Class<T>, snap: DataSnapshot): MutableList<T>{
    val arrayList: MutableList<T> = arrayListOf()
    for (child in snap.children){
        child.getValue(className)?.let { arrayList.add(it) }
    }

    return arrayList
}

fun convertTwoUserIDs(userID1: String, userID2: String): String{
    return if (userID1 < userID1){
        userID2 + userID1
    }else {
        userID1 + userID2
    }
}