package com.utiq.utiq_loader_android_webview

/**
 * A simple [Fragment] subclass.
 * Use the [UtiqConsent.newInstance] factory method to
 * create an instance of this fragment.
 */

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.utiq.utiq_loader_android_webview.databinding.FragmentUtiqConsentBinding

class UtiqConsent : DialogFragment(R.layout.fragment_utiq_consent) {

    private var _binding: FragmentUtiqConsentBinding? = null
    private val binding get() = _binding!!
    private var action: ((consent: Boolean) -> Unit)? =
        null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUtiqConsentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.acceptButton.setOnClickListener {
            this.action?.invoke(true)
            this.dismiss()
        }
        binding.cancelButton.setOnClickListener {
            this.action?.invoke(false)
            this.dismiss()
        }
        binding.btnClose.setOnClickListener {
            this.dismiss()
        }
    }

    companion object {
        fun open(
            fm: FragmentManager?,
            action: (omPermissionAccepted: Boolean) -> Unit = { _ -> }
        ) {
            fm?.let {
                val dialogFragment = UtiqConsent()
                dialogFragment.action = action
                dialogFragment.show(it, null)
            }
        }
    }
}