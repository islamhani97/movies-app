package com.islam.android.apps.movies.ui.moviedatails

import androidx.lifecycle.MutableLiveData
import com.islam.android.apps.movies.base.BaseViewModel
import com.islam.android.apps.movies.data.DataManager
import com.islam.android.apps.movies.data.repositories.Repository
import com.islam.android.apps.movies.pojo.FavouriteRequest
import com.islam.android.apps.movies.pojo.FavouriteResponse
import com.islam.android.apps.movies.pojo.Movie
import com.islam.android.apps.movies.pojo.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel
@Inject
constructor(dataManager: DataManager, repository: Repository) :
    BaseViewModel(dataManager, repository) {

    val movieLiveData = MutableLiveData<ViewState<Movie>>()
    val favouriteLiveData = MutableLiveData<ViewState<FavouriteResponse>>()

    fun getMovieDetails(movieId: Int) {
        dataManager.requestAPI({ repository.getMovieDetails(movieId) }, movieLiveData)
    }

    fun setFavourite(accountId: Int,sessionId: String,  favouriteRequest: FavouriteRequest) {
        dataManager.requestAPI(
            { repository.setFavourite(accountId,sessionId,  favouriteRequest) },
            favouriteLiveData
        )
    }


}