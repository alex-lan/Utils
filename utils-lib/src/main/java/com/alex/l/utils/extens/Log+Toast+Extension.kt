package com.alex.l.utils.extens

import android.util.Log
import android.widget.Toast
import com.alex.l.utils.AUtils
import com.alex.l.utils.BuildConfig

/**
 * 类名: Log&Toast扩展方法
 * 类的作用描述：
 *
 * @author AlexL 2021/5/27
 */

private val logTag = AUtils.init().logTag
private val context by Weak { AUtils.getContext() }

private fun log(level: Int, tag: String, msg: String) {
    if (BuildConfig.DEBUG)
        when (level) {
            Log.DEBUG -> Log.d(tag, msg)
            Log.ERROR -> Log.e(tag, msg)
            Log.INFO -> Log.i(tag, msg)
            Log.VERBOSE -> Log.v(tag, msg)
            Log.WARN -> Log.w(tag, msg)
        }
}

/**
 * 相当于 Log.d()
 *
 * @param tag
 */
fun String.d(tag: String = logTag) {
    log(Log.DEBUG, tag, this)
}

/**
 * 相当于 Log.e()
 *
 * @param tag
 */
fun String.e(tag: String = logTag) {
    log(Log.ERROR, tag, this)
}

/**
 * 相当于 Log.i()
 *
 * @param tag
 */
fun String.i(tag: String = logTag) {
    log(Log.INFO, tag, this)
}

/**
 * 相当于 Log.v()
 *
 * @param tag
 */
fun String.v(tag: String = logTag) {
    log(Log.VERBOSE, tag, this)
}

/**
 * 相当于 Log.w()
 *
 * @param tag
 */
fun String.w(tag: String = logTag) {
    log(Log.WARN, tag, this)
}

/**
 * @param duration
 * * 0 - LENGTH_SHORT
 * * 1 - LENGTH_LONG
 */
fun CharSequence.toast(duration: Int = Toast.LENGTH_SHORT) {
    context!!.let { Toast.makeText(context, this, duration).show() }
}