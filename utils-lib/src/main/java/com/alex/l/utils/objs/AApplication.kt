package com.alex.l.utils.objs

import android.app.Application
import com.alex.l.utils.AUtils

/**
 * [AApplication]
 * -
 * * Created by [Alex Y. Lan] on [2022-07-22]
 * * Power by AndroidStudio™ IDE
 */
open class AApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initAUtils()
    }

    private fun initAUtils() {
        AUtils.init {
            context = this@AApplication
        }
    }


}