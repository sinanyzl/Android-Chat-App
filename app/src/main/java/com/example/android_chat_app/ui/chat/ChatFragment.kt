package com.example.android_chat_app.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.android_chat_app.databinding.FragmentChatBinding
import com.example.android_chat_app.databinding.ToolbarAddonChatBinding
import kotlinx.android.synthetic.main.fragment_chat.*


class ChatFragment : Fragment() {

    companion object{
        const val ARGS_KEY_USER_ID = "bundle_user?id"
        const val ARGS_KEY_OTHER_USER_ID = "bundle_other_user_id"
        const val ARGS_KEY_CHAT_ID = "bundle_other_chat_id"
    }

    private val viewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(
            requireArguments().getString(ARGS_KEY_USER_ID)!!,
            requireArguments().getString(ARGS_KEY_OTHER_USER_ID)!!,
            requireArguments().getString(ARGS_KEY_CHAT_ID)!!
        )
    }

    
}