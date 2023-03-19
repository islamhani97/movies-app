package com.islam.android.apps.movies.ui.authdialog

import androidx.lifecycle.MutableLiveData
import com.islam.android.apps.movies.base.BaseViewModel
import com.islam.android.apps.movies.data.DataManager
import com.islam.android.apps.movies.data.repositories.Repository
import com.islam.android.apps.movies.pojo.Account
import com.islam.android.apps.movies.pojo.SessionRequest
import com.islam.android.apps.movies.pojo.SessionResponse
import com.islam.android.apps.movies.pojo.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(dataManager: DataManager, repository: Repository) :
    BaseViewModel(dataManager, repository) {

    val sessionLiveData = MutableLiveData<ViewState<SessionResponse>>()
    val accountLiveData = MutableLiveData<ViewState<Account>>()


    fun createSession(sessionRequest: SessionRequest) {
        dataManager.requestAPI({ repository.createSession(sessionRequest) }, sessionLiveData)
    }

    fun fetchUserAccount(sessionId: String) {
        dataManager.requestAPI({ repository.fetchUserAccount(sessionId) }, accountLiveData)
    }

}