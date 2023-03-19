package com.islam.android.apps.movies.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.islam.android.apps.movies.ui.loading.LoadingDialog

/**
 * The base Activity class to configure some several configurations
 */
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var viewBinding: VB
    protected abstract val layout: Int
    protected lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layout)
        loading = LoadingDialog(this)
    }
}