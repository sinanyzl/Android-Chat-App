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

    private lateinit var viewDataBinding: FragmentSettingsBinding
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

        return viewDatabinding.true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservers()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == selectImageIntentRequestCode) {
            data?.data?.let { uri ->
                convertFileToByteArray(requireContext(), uri).let {
                    viewModel.changeUserImage(it)
                }
            }
        }
    }


    private fun setupObservers() {
        viewModel.editStatusEvent.observe(viewLifecycleOwner,
            EventObserver { showEditStatusDialog() })

        viewModel.editImageEvent.observe(viewLifecycleOwner,
            EventObserver { startSelectImageIntent() })

        viewModel.logoutEvent.observe(viewLifecycleOwner,
            EventObserver {
                SharedPreferencesUtil.removeUserID(requireContext())
                navigateToStart()
            })
    }

    private fun showEditStatusDialog() {
        val input = EditText(requireActivity() as Context)
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Status:")
            setView(input)
            setPositiveButton("Ok") { _, _ ->
                val textInput = input.text.toString()
                if (!textInput.isBlank() && textInput.length <= 40) {
                    viewModel.changeUserStatus(textInput)
                }
            }
            setNegativeButton("Cancel") { _, _ -> }
            show()
        }
    }

    private fun startSelectImageIntent() {
        val selectImageIntent = Intent(Intent.ACTION_GET_CONTENT)
        selectImageIntent.type = "image/*"
        startActivityForResult(selectImageIntent, selectImageIntentRequestCode)
    }

    private fun navigateToStart() {
        findNavController().navigate(R.id.action_navigation_settings_to_startFragment)
    }


}