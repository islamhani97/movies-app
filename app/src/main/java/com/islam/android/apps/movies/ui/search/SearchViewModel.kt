package com.islam.android.apps.movies.ui.search

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
class SearchViewModel
@Inject
constructor(dataManager: DataManager, repository: Repository) :
    BaseViewModel(dataManager, repository) {

    val moviesLiveData = MutableLiveData<ViewState<MoviesResponse>>()


    fun search(searchQuery: String,page: Int) {
        dataManager.requestAPI({ repository.search(searchQuery,page) }, moviesLiveData)
    }

}