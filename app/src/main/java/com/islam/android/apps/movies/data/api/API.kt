package com.islam.android.apps.movies.data.api

import com.islam.android.apps.movies.pojo.*
import retrofit2.Response
import retrofit2.http.*

/**
 *The API interface for Retrofit
 */
interface API {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("sort_by") sort: String,
        @Query("with_genres") genreId: Int?
    ): Response<MoviesResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenresResponse>

    @GET("search/movie")
    suspend fun search(
        @Query("query") searchQuery: String,
        @Query("page") page: Int
    ): Response<MoviesResponse>


    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int): Response<Movie>

    @GET("authentication/token/new")
    suspend fun createRequestToken(): Response<RequestTokenResponse>

    @POST("authentication/session/new")
    suspend fun createSession(@Body sessionRequest: SessionRequest): Response<SessionResponse>

    @GET("account")
    suspend fun fetchUserAccount(@Query("session_id") sessionId: String): Response<Account>


    @GET("account/{accountId}/favorite/movies")
    suspend fun getFavouriteMovies(
        @Path("accountId") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @POST("account/{accountId}/favorite")
    suspend fun setFavourite(
        @Path("accountId") accountId: Int,
        @Query("session_id") sessionId: String,
        @Body favouriteRequest: FavouriteRequest
    ): Response<FavouriteResponse>
}