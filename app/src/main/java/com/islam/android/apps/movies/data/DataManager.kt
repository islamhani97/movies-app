package com.islam.android.apps.movies.data

import androidx.lifecycle.MutableLiveData
import com.islam.android.apps.movies.pojo.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The DataManager class is responsible for handling API response
 */

@Singleton
class DataManager
@Inject
constructor() {

    fun <T : Any> requestAPI(
        request: suspend () -> Response<T>,
        liveData: MutableLiveData<ViewState<T>>
    ) {
        try {
            liveData.value = ViewState.Loading
            CoroutineScope(Dispatchers.IO).launch {
                val response = request.invoke()
                val responseConde = response.code()
                withContext(Dispatchers.Main) {
                    if (responseConde in 200..299) {
                        liveData.value = ViewState.Success(response.body())
                    } else {
                        liveData.value = ViewState.Error(response.errorBody()!!.string())
                    }
                }
            }
        } catch (e: Exception) {
            liveData.value = ViewState.Error(e.message!!)
        }
    }
}