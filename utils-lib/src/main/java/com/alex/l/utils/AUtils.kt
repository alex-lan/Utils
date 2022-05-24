package com.alex.l.utils

import android.content.Context
import com.alex.l.utils.AException.thr
import com.alex.l.utils.extens.Weak

/**
 * [AUtils]
 * -
 * * Created by [Alex Y. Lan] on [2022-05-22]
 * * Power by AndroidStudio™ IDE
 */
open class AUtils {
    var context: Context? by Weak()
    // by Weak()
    /**
     * 全局的 logTag，你仍可以每个log单独设置tag： 内容.i(tag)
     */
    var logTag: String = BuildConfig.LOG_TAG

    companion object {
        private var INSTANCE: AUtils? = null

        /**
         * AUtils init
         *
         * @param context  If is 'null' <String.toast()> will crash
         * @param logTag  If undefined, global default LogTag is ‘---ALog---’
         * @receiver
         * @return
         */
        fun init(block: AUtils.() -> Unit = {}) = INSTANCE ?: AUtils().also {
            block(it)
            INSTANCE = it
        }

        /**
         * 'AUtils' lib internal call, checked for null
          */
        fun getContext(): Context? {
            if (INSTANCE!!.context == null) AException.nullPointerException("请先初始化AUtils，并对context赋值: AUtils.init { context = ? }").thr()
            return INSTANCE!!.context
        }
    }
}