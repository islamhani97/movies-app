package com.islam.android.apps.movies.ui.logindialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.islam.android.apps.movies.R
import com.islam.android.apps.movies.base.BaseDialogFragment
import com.islam.android.apps.movies.databinding.DialogLoginBinding
import com.islam.android.apps.movies.pojo.ViewState
import dagger.hilt.android.AndroidEntryPoint

/**
 * The Dialog that appears when the user not logged in no notify him to log in
 */

@AndroidEntryPoint
class LoginDialog : BaseDialogFragment<DialogLoginBinding, LoginViewModel>() {
    override val layout: Int
        get() = R.layout.dialog_login
    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.login.setOnClickListener {
            viewModel.createRequestToken()
        }

        viewModel.requestTokenLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    loading.show()
                }
                is ViewState.Success -> {
                    val url = "https://www.themoviedb.org/authenticate/${it.data?.requestToken}"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)

                    findNavController().navigate(
                        LoginDialogDirections.actionLoginDialogToAuthDialog(
                            it.data?.requestToken ?: ""
                        )
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