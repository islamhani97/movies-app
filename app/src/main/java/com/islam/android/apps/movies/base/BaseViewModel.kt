package com.islam.android.apps.movies.base

import androidx.lifecycle.ViewModel
import com.islam.android.apps.movies.data.DataManager
import com.islam.android.apps.movies.data.repositories.Repository
import com.islam.android.apps.movies.pojo.Account

/**
 * The base ViewModel class to configure some several configurations
 */

abstract class BaseViewModel(
    protected val dataManager: DataManager,
    protected val repository: Repository
) : ViewModel() {

    // add these base method to be available at all view models

    fun saveSessionId(sessionId: String) = repository.saveSessionId(sessionId)
    fun getSessionId(): String? = repository.getSessionId()
    fun saveAccount(account: Account) = repository.saveAccount(account)
    fun getAccount(): Account? = repository.getAccount()
    fun isLoggedIn() = repository.isLoggedIn()

}