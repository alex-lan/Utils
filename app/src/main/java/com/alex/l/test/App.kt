package com.alex.l.test

import android.app.Application
import com.alex.l.utils.AUtils

/**
 * [App]
 * -
 * Created by [Alex Y. Lan] on [2022-05-24]
 * Power by AndroidStudio™ IDE
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        AUtils.init {
            context = this@App
        }
    }

}