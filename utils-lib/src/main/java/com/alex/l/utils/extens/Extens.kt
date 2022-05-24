package com.alex.l.utils.extens

import java.lang.ref.WeakReference
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

    constructor():this({
        null
    })

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return weakReference.get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        weakReference = WeakReference(value)
    }

}