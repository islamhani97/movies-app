package com.islam.android.apps.movies.pojo

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    var selected: Boolean = false
)
