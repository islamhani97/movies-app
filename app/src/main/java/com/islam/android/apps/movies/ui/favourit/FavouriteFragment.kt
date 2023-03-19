package com.islam.android.apps.movies.ui.favourit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.islam.android.apps.movies.R
import com.islam.android.apps.movies.base.BaseFragment
import com.islam.android.apps.movies.databinding.FragmentFavouriteBinding
import com.islam.android.apps.movies.pojo.Movie
import com.islam.android.apps.movies.pojo.ViewState
import com.islam.android.apps.movies.ui.home.MoviesAdapter
import com.islam.android.apps.movies.utils.Decorations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding, FavouriteViewModel>(),
    MoviesAdapter.MoviesCallback {
    override val layout: Int
        get() = R.layout.fragment_favourite

    override val viewModel: FavouriteViewModel by viewModels()

    private var currentPage = 1
    private var pagesCount = 500
    private var loadMore = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // configuring toolbar with navigation controller
        NavigationUI.setupWithNavController(viewBinding.toolbar, findNavController())

        // configure recycler view
        viewBinding.itemsList.layoutManager = GridLayoutManager(context, 2)
        viewBinding.itemsList.addItemDecoration(Decorations.RECYCLER_VIEW_ITEM_DECORATION)

        // request user favourites
        viewModel.getFavouriteMovies(
            viewModel.getAccount()!!.id!!,
            viewModel.getSessionId()!!,
            currentPage
        )

        setUpListeners()
        setUpObservers()
    }

    /**
     * this method for more organization to configure all listeners inside it and call it once at
     * onViewCreated instead insert theme one by one
     */
    private fun setUpListeners() {
        viewBinding.itemsList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemsCount = recyclerView.layoutManager!!.childCount
                val firstVisibleItemPosition =
                    (recyclerView.layoutManager!! as GridLayoutManager).findFirstVisibleItemPosition()
                val totalItemsCount = recyclerView.layoutManager!!.itemCount
                if ((visibleItemsCount) + firstVisibleItemPosition >= totalItemsCount && !loadMore) {
                    loadMoreMovies()
                }
            }
        })
    }

    /**
     * this method for more organization to configure all observer for live data from view model
     * inside it and call it once at onViewCreated instead insert theme one by one
     */
    private fun setUpObservers() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {
                    if(!loadMore){
                        loading.show()
                    }
                }
                is ViewState.Success -> {
                    if (loadMore) {
                        loadMore = false
                        (viewBinding.itemsList.adapter as MoviesAdapter).appendToDataSet(it.data?.movies!!)
                    } else {
                        viewBinding.itemsList.adapter =
                            MoviesAdapter(it.data?.movies ?: ArrayList(), this)
                    }
                    loading.dismiss()
                }
                is ViewState.Error -> {
                    loading.dismiss()
                }
            }
        }


    }

    /**
     * to load more results from API
     */
    private fun loadMoreMovies() {
        if (currentPage < pagesCount) {
            loadMore = true
            viewModel.getFavouriteMovies(
                viewModel.getAccount()!!.id!!,
                viewModel.getSessionId()!!,
                ++currentPage
            )
        }
    }

    override fun onMovieClicked(movie: Movie) {
        findNavController().navigate(
            FavouriteFragmentDirections.actionFavouriteFragmentToMovieDetailsFragment(
                movie.id!!, movie.title!!
            )
        )
    }
}