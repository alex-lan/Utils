package com.alex.l.utils.extens

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.alex.l.utils.AUtils
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

/**
 * Md5
 *
 * @return result
 */
fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

/**
 * Sha1
 *
 * @return result
 */
fun String.sha1(): String {
    val md = MessageDigest.getInstance("SHA-1")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

/**
 * Sha256
 *
 * @return result
 */
fun String.sha256(): String {
    val md = MessageDigest.getInstance("SHA-256")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

// -------------- Cust NetWork Object -------------
/**
 * _network 是 NetWork 的一个全局实例
 * @see com.alex.l.utils.extens.NetWork
 */
val _network: NetWork by lazy {
    NetWork.init()
}

/**
 * NetWork
 * 若需要实例化，必须在 AndroidManifest.xml  添加  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
class NetWork private constructor() {
    private val context by Weak { AUtils.getContext() }

    /**
     * @see android.net.ConnectivityManager
     */
    var cm: ConnectivityManager
    val info: NetworkInfo?
        @SuppressLint("MissingPermission")
        get() {
            return try {
                cm.activeNetworkInfo
            } catch (e: SecurityException) {
                e.printStackTrace()
                e.message?.toast(1)
                null
            }
        }

    companion object {
        private var INSTANCE: NetWork? = null
        fun init() = INSTANCE ?: NetWork().also {
            INSTANCE = it
        }
    }

    init {
        (" —— NetWork初始化！——").i()
        cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    }

    /**
     * If Network connected
     *
     * @return
     */
    val isConnected: Boolean
        get() {
            if (info?.isConnected == true) {
                return info!!.state == NetworkInfo.State.CONNECTED
            }
            return false
        }

    /**
     * 判断是否是wifi连接
     */
    val isWifi: Boolean
        get() {
            return netType == ConnectivityManager.TYPE_WIFI
        }

    /**
     * 网络类型
     * @sample android.net.ConnectivityManager.TYPE_WIFI
     * @see android.net.ConnectivityManager
     *
     * @return
     */
    val netType: Int
        get() {
            return if (info != null) {
                info!!.type
            } else -1
        }

}