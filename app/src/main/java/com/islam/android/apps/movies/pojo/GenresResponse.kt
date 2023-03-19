package com.islam.android.apps.movies.pojo

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres")
    val genres: List<Genre>?,
)
