package com.alex.l.utils.extens

import java.lang.ref.WeakReference
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit
import kotlin.reflect.KProperty

/**
 * Created by [Alex Y. Lan] on [2022-05-23]
 * Power by AndroidStudio™ IDE
 */

/**
 * WeakReference customize
 *
 * Usege:
 * * var i by Weak{ any }
 * * var i:Any? by Weak{ any }
 * * var i by Weak<Any> { any }
 * * var i:Any? by Weak()
 * * var i by Weak<Any>()
 * @param T
 * @param initializer
 * @constructor
 *
 */
class Weak<T : Any>(initializer: () -> T?) {
    var weakReference = WeakReference<T?>(initializer())

    constructor() : this({
        null
    })

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return weakReference.get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        weakReference = WeakReference(value)
    }

}

/**
 * 关闭线程池的公共方法
 *
 * @param poolAlies 添加一个别名来标记线程池，有助于log精确打印出其是否成功关闭的信息
 */
fun ExecutorService.shutdown(poolAlies: String = "") {
    if (!isTerminated) {
        try {
            shutdownNow()
            while (!awaitTermination(5, TimeUnit.SECONDS)) {
                ("$poolAlies - 线程池关闭失败!").w()
            }
            ("$poolAlies - 线程池关闭成功!").i()
        } catch (e1: InterruptedException) {
            e1.printStackTrace()
        }
    }
}

// ------------ encryption ---------------

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun String.sha1(): String {
    val md = MessageDigest.getInstance("SHA-1")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun String.sha256(): String {
    val md = MessageDigest.getInstance("SHA-256")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}