package com.alex.l.utils

/**
 * [AException]
 * -
 * Created by [Alex Y. Lan] on [2022-05-23]
 * Power by AndroidStudio™ IDE
 */
object AException {

    /**
     * 抛出一个运行时错误
     *
     * @param tag 错误标识
     * @return
     */
    @JvmOverloads
    fun runtimeException(tag: String? = "运行时错误@！"): RuntimeException {
        return RuntimeException(tag)
    }

    /**
     * 抛一个域错误
     *
     * @param tag
     * @return
     */
    @JvmOverloads
    fun noSuchFieldError(tag: String? = "域不存在@！"): NoSuchFieldError {
        return NoSuchFieldError(tag)
    }

    /**
     * 抛一个方法不存在错误
     *
     * @param tag
     * @return
     */
    @JvmOverloads
    fun noSuchMethodError(tag: String? = "方法不存在@!"): NoSuchMethodError {
        return NoSuchMethodError(tag)
    }

    @JvmOverloads
    fun nullPointerException(msg: String? = "空指针异常@!"): NullPointerException {
        return NullPointerException(msg)
    }

    /**
     * throw a Exception
     *
     */
    fun Exception.thr(){
        throw this
    }
}