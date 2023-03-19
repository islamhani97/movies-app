package com.islam.android.apps.movies.pojo

import com.google.gson.annotations.SerializedName

data class FavouriteRequest(
    @SerializedName("media_type")
    val mediaType: String = "movie",
    @SerializedName("media_id")
    val mediaId: Int,
    @SerializedName("favorite")
    val favorite: Boolean
)
