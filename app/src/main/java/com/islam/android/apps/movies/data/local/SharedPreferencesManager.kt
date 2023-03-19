package com.islam.android.apps.movies.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.islam.android.apps.movies.pojo.Account
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


/**
 *This class is responsible for handling SharedPreferences as a local storage
 */

@Singleton
class SharedPreferencesManager
@Inject
constructor(@ApplicationContext context: Context, private val gson: Gson) {

    companion object {
        private const val SH_PREFS_SESSION_ID = "SessionId"
        private const val SH_PREFS_ACCOUNT = "Account"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MoviesSharedPreferences", MODE_PRIVATE)

    fun saveSessionId(sessionId: String) =
        sharedPreferences.edit().putString(SH_PREFS_SESSION_ID, sessionId).apply()

    fun getSessionId(): String? = sharedPreferences.getString(SH_PREFS_SESSION_ID, null)


    fun saveAccount(account: Account) =
        sharedPreferences.edit().putString(SH_PREFS_ACCOUNT, gson.toJson(account)).apply()

    fun getAccount(): Account? =
        gson.fromJson(sharedPreferences.getString(SH_PREFS_ACCOUNT, null), Account::class.java)

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}