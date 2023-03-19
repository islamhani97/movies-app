package com.islam.android.apps.movies.utils

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

object Decorations {


    /**
     * It is a static decoration for Recycler view items
     */

    val RECYCLER_VIEW_ITEM_DECORATION = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
            super.getItemOffsets(outRect, itemPosition, parent)
            outRect.top = 12
            outRect.bottom = 12
            outRect.left = 12
            outRect.right = 12
        }
    }
}