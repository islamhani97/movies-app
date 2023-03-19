package com.islam.android.apps.movies.ui.authdialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.islam.android.apps.movies.R
import com.islam.android.apps.movies.base.BaseDialogFragment
import com.islam.android.apps.movies.databinding.DialogAuthBinding
import com.islam.android.apps.movies.pojo.SessionRequest
import com.islam.android.apps.movies.pojo.ViewState
import dagger.hilt.android.AndroidEntryPoint

/**
 * The Dialog that appears when the user logged in the website and need to generate a session on the app
 */

@AndroidEntryPoint
class AuthDialog : BaseDialogFragment<DialogAuthBinding, AuthViewModel>() {
    override val layout: Int
        get() = R.layout.dialog_auth
    override val viewModel: AuthViewModel by viewModels()

    private var sessionId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val requestToken = AuthDialogArgs.fromBundle(requireArguments()).requestToken

        viewBinding.generateSession.setOnClickListener {
            viewModel.createSession(SessionRequest(requestToken))
        }

        viewModel.sessionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    loading.show()
                }
                is ViewState.Success -> {
                    sessionId = it.data!!.sessionId!!
                    viewModel.fetchUserAccount(sessionId!!)
                    loading.dismiss()
                }
                is ViewState.Error -> {
                    loading.dismiss()
                }
            }
        }

        viewModel.accountLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    loading.show()
                }
                is ViewState.Success -> {
                    viewModel.saveSessionId(sessionId!!)
                    viewModel.saveAccount(it.data!!)
                    findNavController().navigate(
                        AuthDialogDirections.actionAuthDialogToHomeFragment()
                    )
                    loading.dismiss()
                }
                is ViewState.Error -> {
                    loading.dismiss()
                }
            }
        }
    }
}