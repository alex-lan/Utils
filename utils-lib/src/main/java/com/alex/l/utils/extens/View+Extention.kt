package com.alex.l.utils.extens

import android.view.View
import android.view.ViewGroup

/**
 * [View+Extention]
 * -
 * * Created by [Alex Y. Lan] on [2022-05-25]
 * * Power by AndroidStudio™ IDE
 */


/**
 * Get all child views
 *
 * @param class childView class
 * @return list of childViews
 */
fun <T> View.getAllChildViews(classx: Class<T>): List<T> {
    val childViews: ArrayList<T> = ArrayList()
    if (this is ViewGroup) {
        val vp = this
        for (i in 0 until vp.childCount) {
            val viewChild: View = vp.getChildAt(i)
            if (viewChild.javaClass == classx) {
                childViews.add(viewChild as T)
            }
            childViews.addAll(viewChild.getAllChildViews(classx))
        }
    }
    return childViews
}