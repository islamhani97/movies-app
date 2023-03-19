package com.islam.android.apps.movies.ui.logindialog

import androidx.lifecycle.MutableLiveData
import com.islam.android.apps.movies.base.BaseViewModel
import com.islam.android.apps.movies.data.DataManager
import com.islam.android.apps.movies.data.repositories.Repository
import com.islam.android.apps.movies.pojo.RequestTokenResponse
import com.islam.android.apps.movies.pojo.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(dataManager: DataManager, repository: Repository) :
    BaseViewModel(dataManager, repository) {

    val requestTokenLiveData = MutableLiveData<ViewState<RequestTokenResponse>>()

    fun createRequestToken() {
        dataManager.requestAPI({ repository.createRequestToken() }, requestTokenLiveData)
    }

}