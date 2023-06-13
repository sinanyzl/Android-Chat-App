package com.example.android_chat_app.ui.settings

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android_chat_app.App
import com.example.android_chat_app.R
import com.example.android_chat_app.databinding.FragmentSettingsBinding
import com.example.android_chat_app.data.EventObserver
import com.example.android_chat_app.util.SharedPreferencesUtil
import com.example.android_chat_app.util.convertFileToByteArray



class SettingsFragment : Fragment(){
    private val  viewModel: SettingsViewModel by viewModels { SettingsViewModelFactory(App.myUserID)  }

    private lateinit var viewDatabinding: FragmentSettingsBinding
    private val selectImageIntentRequestCode = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDatabinding = FragmentSettingsBinding.inflate(inflater, container, false)
            .apply {viewmodel = viewModel}
        viewDatabinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)
    }



}