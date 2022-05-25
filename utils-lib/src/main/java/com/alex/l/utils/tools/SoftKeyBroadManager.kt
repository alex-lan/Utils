package com.alex.l.utils.tools

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import java.util.*

/**
 * -
 * * Created by [Alex Y. Lan] on [2022-05-26]
 * * Power by AndroidStudio™ IDE
 */

interface SoftKeyboardStateListener {
    fun onSoftKeyboardOpened(keyboardHeightInPx: Int)
    fun onSoftKeyboardClosed()
}

open class SoftKeyBroadStateObserve private constructor(
    activityRootView: View,
    isSoftKeyboardOpened: Boolean = false
) :
    OnGlobalLayoutListener {

    private val listeners: MutableList<SoftKeyboardStateListener> = LinkedList()
    private val activityRootView: View
    private var lastSoftKeyboardHeightInPx = 0
    private var isSoftKeyboardOpened: Boolean
    override fun onGlobalLayout() {
        val r = Rect()
        activityRootView.getWindowVisibleDisplayFrame(r)
        val heightDiff = activityRootView.rootView.height - (r.bottom - r.top)
        if (!isSoftKeyboardOpened && heightDiff > 500) {
            // 如果高度超过500 键盘可能被打开
            isSoftKeyboardOpened = true
            notifyOnSoftKeyboardOpened(heightDiff)
        } else if (isSoftKeyboardOpened && heightDiff < 500) {
            isSoftKeyboardOpened = false
            notifyOnSoftKeyboardClosed()
        }
    }

    fun setIsSoftKeyboardOpened(isSoftKeyboardOpened: Boolean) {
        this.isSoftKeyboardOpened = isSoftKeyboardOpened
    }

    fun isSoftKeyboardOpened(): Boolean {
        return isSoftKeyboardOpened
    }

    fun getLastSoftKeyboardHeightInPx(): Int {
        return lastSoftKeyboardHeightInPx
    }

    fun addStateListener(listener: SoftKeyboardStateListener) {
        listeners.add(listener)
    }

    fun removeStateListener(listener: SoftKeyboardStateListener) {
        listeners.remove(listener)
    }

    private fun notifyOnSoftKeyboardOpened(keyboardHeightInPx: Int) {
        lastSoftKeyboardHeightInPx = keyboardHeightInPx
        for (listener in listeners) {
            listener.onSoftKeyboardOpened(keyboardHeightInPx)
        }
    }

    private fun notifyOnSoftKeyboardClosed() {
        for (listener in listeners) {
            listener.onSoftKeyboardClosed()
        }
    }

    /**
     * Use: SoftKeyBroadStateObserve.with(decorView).addStateListener(object :listener)
     *
     * @constructor Create empty Companion
     */
    companion object {
        /**
         * With
         *
         * @param decorView activity rootView
         * @return
         */
        fun with(decorView: View): SoftKeyBroadStateObserve {
            return SoftKeyBroadStateObserve(decorView)
        }

        /**
         * With
         *
         * @param decorView
         * @param isSoftKeyboardOpened
         * @return
         */
        fun with(decorView: View, isSoftKeyboardOpened: Boolean): SoftKeyBroadStateObserve {
            return SoftKeyBroadStateObserve(decorView, isSoftKeyboardOpened)
        }
    }

    init {
        this.activityRootView = activityRootView
        this.isSoftKeyboardOpened = isSoftKeyboardOpened
        activityRootView.viewTreeObserver.addOnGlobalLayoutListener(this)
    }
}

fun Activity.hideSoftInputWindow() {
    val mInputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (currentFocus == null) return
    mInputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
}

fun Fragment.hideSoftInputWindow() {
    activity?.hideSoftInputWindow()
}