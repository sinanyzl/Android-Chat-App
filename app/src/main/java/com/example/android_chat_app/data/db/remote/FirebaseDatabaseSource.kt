package com.example.android_chat_app.data.db.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseSource {

    companion object{
        val dbInstance = FirebaseDatabase.getInstance()
    }

    private fun refToPath(path: String): DatabaseReference{
        return dbInstance.reference.child(path)
    }




}