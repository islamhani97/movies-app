package com.islam.android.apps.movies.ui.moviedatails

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.islam.android.apps.movies.R
import com.islam.android.apps.movies.base.BaseFragment
import com.islam.android.apps.movies.databinding.FragmentMovieDetailsBinding
import com.islam.android.apps.movies.pojo.FavouriteRequest
import com.islam.android.apps.movies.pojo.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {
    override val layout: Int
        get() = R.layout.fragment_movie_details
    override val viewModel: MovieDetailsViewModel by viewModels()

    private var menuItem: MenuItem? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // configuring toolbar with navigation controller
        NavigationUI.setupWithNavController(viewBinding.toolbar, findNavController())

        val movieId = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movieId
        viewModel.getMovieDetails(movieId)
        setUpObservers()
    }

    private fun configureFavourite() {
        if (viewModel.isLoggedIn()) {
            viewBinding.toolbar.inflateMenu(R.menu.menu_favourite_opotions)
            viewBinding.toolbar.setOnMenuItemClickListener { item ->
                item.icon = resources.getDrawable(R.drawable.ic_favourite_selected)
                addToFavourite()
                true
            }
        }
    }

    /**
     * request an API to add the movie to favourite
     */
    private fun addToFavourite() {
        viewModel.setFavourite(
            viewModel.getAccount()!!.id!!, viewModel.getSessionId()!!,
            FavouriteRequest(mediaId = viewBinding.movie!!.id!!, favorite = true)
        )
    }

    /**
     * this method for more organization to configure all observer for live data from view model
     * inside it and call it once at onViewCreated instead insert theme one by one
     */
    private fun setUpObservers() {
        viewModel.movieLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    loading.show()
                }
                is ViewState.Success -> {
                    viewBinding.movie = it.data
                    configureFavourite()
                    loading.dismiss()
                }
                is ViewState.Error -> {
                    loading.dismiss()
                }
            }
        }
    }
}