package com.islam.android.apps.movies.data.repositories

import com.islam.android.apps.movies.data.api.API
import com.islam.android.apps.movies.data.local.SharedPreferencesManager
import com.islam.android.apps.movies.pojo.Account
import com.islam.android.apps.movies.pojo.FavouriteRequest
import com.islam.android.apps.movies.pojo.SessionRequest
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The Repository class is responsible for handling data that comes from all data sources (e.g. API & SharedPreferences)
 */

@Singleton
class Repository
@Inject
constructor(private val api: API, private val sharedPreferencesManager: SharedPreferencesManager) {

    suspend fun getMovies(page: Int, sort: String, genreId: Int?) =
        api.getMovies(page, sort, genreId)

    suspend fun getGenres() = api.getGenres()

    suspend fun search(searchQuery: String, page: Int) = api.search(searchQuery, page)

    suspend fun getMovieDetails(movieId: Int) = api.getMovieDetails(movieId)

    suspend fun createRequestToken() = api.createRequestToken()

    suspend fun createSession(sessionRequest: SessionRequest) = api.createSession(sessionRequest)

    suspend fun fetchUserAccount(sessionId: String) = api.fetchUserAccount(sessionId)

    suspend fun getFavouriteMovies(accountId: Int, sessionId: String, page: Int) =
        api.getFavouriteMovies(accountId, sessionId, page)

    suspend fun setFavourite(
        accountId: Int, sessionId: String, favouriteRequest: FavouriteRequest
    ) = api.setFavourite(accountId, sessionId, favouriteRequest)

    fun saveSessionId(sessionId: String) = sharedPreferencesManager.saveSessionId(sessionId)

    fun getSessionId(): String? = sharedPreferencesManager.getSessionId()

    fun saveAccount(account: Account) = sharedPreferencesManager.saveAccount(account)

    fun getAccount(): Account? = sharedPreferencesManager.getAccount()

    fun isLoggedIn(): Boolean {
        return sharedPreferencesManager.getSessionId() != null
    }
}