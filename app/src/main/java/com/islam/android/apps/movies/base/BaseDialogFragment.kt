package com.islam.android.apps.movies.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.islam.android.apps.movies.R
import com.islam.android.apps.movies.ui.loading.LoadingDialog

/**
 * The base DialogFragment class to configure some several configurations
 */

abstract class BaseDialogFragment<VB : ViewDataBinding, VM : BaseViewModel> :
    BottomSheetDialogFragment() {

    protected lateinit var viewBinding: VB
    protected abstract val layout: Int

    protected abstract val viewModel: VM

    protected lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loading =LoadingDialog(requireContext())
        isCancelable= false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layout, container, false)
        return viewBinding.root
    }

}