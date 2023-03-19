package com.islam.android.apps.movies.ui.home

import androidx.lifecycle.MutableLiveData
import com.islam.android.apps.movies.base.BaseViewModel
import com.islam.android.apps.movies.data.DataManager
import com.islam.android.apps.movies.data.repositories.Repository
import com.islam.android.apps.movies.pojo.GenresResponse
import com.islam.android.apps.movies.pojo.MoviesResponse
import com.islam.android.apps.movies.pojo.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(dataManager: DataManager, repository: Repository) :
    BaseViewModel(dataManager, repository) {

    val moviesLiveData = MutableLiveData<ViewState<MoviesResponse>>()
    val genresLiveData = MutableLiveData<ViewState<GenresResponse>>()


    fun getMovies(page: Int, sort: String, genreId: Int?) {
        dataManager.requestAPI({ repository.getMovies(page, sort, genreId) }, moviesLiveData)
    }

    fun getGenres() {
        dataManager.requestAPI({ repository.getGenres() }, genresLiveData)
    }

}