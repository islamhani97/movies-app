package com.islam.android.apps.movies.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.islam.android.apps.movies.R
import com.islam.android.apps.movies.base.BaseFragment
import com.islam.android.apps.movies.databinding.FragmentHomeBinding
import com.islam.android.apps.movies.pojo.Genre
import com.islam.android.apps.movies.pojo.Movie
import com.islam.android.apps.movies.pojo.ViewState
import com.islam.android.apps.movies.utils.Decorations
import com.islam.android.apps.movies.utils.MoviesSort
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    GenresAdapter.GenresCallback, MoviesAdapter.MoviesCallback {
    override val layout: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

    private var sort = MoviesSort.POPULARITY_DESC
    private var currentPage = 1
    private var pagesCount = 500
    private var loadMore = false

    private var selectedGenre: Genre? = null
    private var selectedGenrePosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getGenres()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // configuring toolbar with navigation controller
        NavigationUI.setupWithNavController(viewBinding.toolbar, findNavController())
        //add a menu to toolbar
        viewBinding.toolbar.inflateMenu(R.menu.menu_home_opotions)

        // configure recycler views

        viewBinding.moviesList.layoutManager = GridLayoutManager(context, 2)
        viewBinding.moviesList.addItemDecoration(Decorations.RECYCLER_VIEW_ITEM_DECORATION)

        viewBinding.genresList.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        viewBinding.genresList.addItemDecoration(Decorations.RECYCLER_VIEW_ITEM_DECORATION)


        setUpListeners()
        setUpObservers()

        // request list movies API

        viewModel.getMovies(currentPage, sort, selectedGenre?.id)
    }

    /**
     * this method for more organization to configure all listeners inside it and call it once at
     * onViewCreated instead insert theme one by one
     */
    private fun setUpListeners() {
        viewBinding.moviesList.addOnScrollListener(object :
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

        viewBinding.toolbar.setOnMenuItemClickListener { item ->
            if (item?.itemId == R.id.search) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
            } else if (item?.itemId == R.id.favourite) {
                if (viewModel.isLoggedIn()) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFavouriteFragment())
                } else {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginDialog())
                }
            }
            true
        }
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
                        (viewBinding.moviesList.adapter as MoviesAdapter).appendToDataSet(it.data?.movies!!)
                    } else {
                        viewBinding.moviesList.adapter =
                            MoviesAdapter(it.data?.movies ?: ArrayList(), this)
                    }
                    loading.dismiss()
                }
                is ViewState.Error -> {
                    loading.dismiss()
                }
            }
        }

        viewModel.genresLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Loading -> {

                }
                is ViewState.Success -> {
                    viewBinding.genresList.adapter =
                        GenresAdapter(it.data?.genres ?: ArrayList(), this)
                }
                is ViewState.Error -> {

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
            viewModel.getMovies(++currentPage, sort, selectedGenre?.id)
        }
    }


    override fun onGenreSelected(genre: Genre, position: Int) {
        if (selectedGenre != null) {
            selectedGenre?.selected = false
            viewBinding.genresList.adapter?.notifyItemChanged(selectedGenrePosition!!)
        }
        selectedGenre = genre
        selectedGenre!!.selected = true
        selectedGenrePosition = position
        viewBinding.genresList.adapter?.notifyItemChanged(selectedGenrePosition!!)

        currentPage = 1
        viewModel.getMovies(currentPage, sort, selectedGenre?.id)
    }

    override fun onMovieClicked(movie: Movie) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(
                movie.id!!,
                movie.title!!
            )
        )
    }
}