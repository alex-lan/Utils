package com.alex.l.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alex.l.utils.extens.i

/**
 * [TestActivity]
 * -
 * Created by [Alex Y. Lan] on [2022-05-22]
 * Power by AndroidStudio™ IDE
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        "6.px2dp().toString().toast()".i()

    }

}