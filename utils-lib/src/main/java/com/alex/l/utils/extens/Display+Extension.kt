package com.alex.l.utils.extens

import android.graphics.Rect
import android.view.View
import com.alex.l.utils.AUtils

/**
 * 类名: Display 扩展
 * 类的作用描述：
 *
 *
 * @author AlexL 2021/6/4
 */
/**
 *
 */

private val _context by Weak { AUtils.getContext() }

//----------尺寸转换----------
fun Number.dp2px(): Int {
    val scale = _context!!.resources.displayMetrics.density
    return (this.toFloat() * scale + 0.5f).toInt()
}

fun Number.px2dp(): Int {
    val scale = _context!!.resources.displayMetrics.density
    return (this.toFloat() / scale + 0.5f).toInt()
}

fun Number.sp2px(): Int {
    val scale = _context!!.resources.displayMetrics.scaledDensity
    return (this.toFloat() * scale + 0.5f).toInt()
}

fun Number.px2sp(): Int {
    val scale = _context!!.resources.displayMetrics.scaledDensity
    return (this.toFloat() / scale + 0.5f).toInt()
}

//----------屏幕尺寸----------

// 获取屏幕宽度
val _screenWidth = _context!!.resources?.displayMetrics?.widthPixels

// 获取屏幕高度
val _screenHeight = _context!!.resources?.displayMetrics?.heightPixels

/**
 * 判断 View 是否在屏幕上
 */
val View.inScreen: Boolean
    get() {
        // 获取屏幕宽度
        val screenWidth = context?.resources?.displayMetrics?.widthPixels ?: 0
        // 获取屏幕高度
        val screenHeight = context?.resources?.displayMetrics?.heightPixels ?: 0
        // 构建屏幕矩形
        val screenRect = Rect(0, 0, screenWidth, screenHeight)
        val array = IntArray(2)
        // 获取视图矩形
        getLocationOnScreen(array)
        val viewRect = Rect(array[0], array[1], array[0] + width, array[1] + height)
        // 判断屏幕和视图矩形是否有交集
        return screenRect.intersect(viewRect)
    }

/**
 * 给color添加透明度
 * @param alpha 透明度 0f～1f
 * @param baseColor 基本颜色
 * @return
 */
fun getColorWithAlpha(alpha: Float, baseColor: Int): Int {
    val a = Math.min(255, Math.max(0, (alpha * 255).toInt())) shl 24
    val rgb = 0x00ffffff and baseColor
    return a + rgb
}