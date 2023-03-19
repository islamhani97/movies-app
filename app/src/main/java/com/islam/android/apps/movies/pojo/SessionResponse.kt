package com.islam.android.apps.movies.pojo

import com.google.gson.annotations.SerializedName

data class SessionResponse(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("session_id")
    val sessionId: String?
)
