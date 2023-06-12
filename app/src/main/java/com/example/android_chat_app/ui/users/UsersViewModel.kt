package com.example.android_chat_app.ui.users

import androidx.lifecycle.*
import com.example.android_chat_app.data.db.entity.User
import com.example.android_chat_app.data.db.repository.DatabaseRepository
import com.example.android_chat_app.ui.DefaultViewModel
import com.example.android_chat_app.data.Event
import com.example.android_chat_app.data.Result

class UsersViewModel (private val myUserID: String): DefaultViewModel() {
    private val  repository: DatabaseRepository = DatabaseRepository()

    private val _selectedUser = MutableLiveData<Event<User>>()
    val selectedUser: LiveData<User> = _selectedUser
    private val updatedUsersList = MutableLiveData<MutableList<User>>()
    var userList = MediatorLiveData<List<User>>()

    init {
        userList.addSource(updatedUsersList){ mutableList->
            userList.value = updatedUsersList.value?.filter { it.info.id != myUserID }
        }
        loadUsers()
    }

    private fun loadUsers(){
        repository.loadUsers { result: Result<MutableList<User>> ->
            onResult(updatedUsersList, result)
        }
    }

    fun selectUser(user: User){
        _selectedUser.value = Event(user)
    }
}

class UsersViewModelFactory(private val  myUserID: String):
    ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T{
            return UsersViewModel(myUserID) as T
        }
    }



