package com.islam.android.apps.movies.utils

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*


/**
 * Binding Adapters
 */

@BindingAdapter(value = ["moviePoster"])
fun moviePoster(view: ImageView, url: String?) {
    Glide.with(view).load("https://image.tmdb.org/t/p/w300_and_h450_bestv2/$url").into(view)
}

@BindingAdapter(value = ["movieBanner"])
fun movieBanner(view: ImageView, url: String?) {
    Glide.with(view).load("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/$url").into(view)
}

@BindingAdapter(value = ["movieYear"])
fun movieYear(view: TextView, date: Date?) {
    if (date != null) {
        view.text = SimpleDateFormat("yyyy", Locale("en")).format(date)
    } else {
        view.text = ""
    }
}

@BindingAdapter(value = ["voteProgress"])
fun voteProgress(view: ProgressBar, voteAverage: Double?) {
    if (voteAverage != null) {
        view.progress = (voteAverage * 10).toInt()
    }
}