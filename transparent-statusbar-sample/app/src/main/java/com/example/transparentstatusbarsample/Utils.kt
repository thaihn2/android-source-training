package com.example.transparentstatusbarsample

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

object Utils {

    const val URL_IMAGE_DEFAULT = "https://image.winudf.com/v2/image/Y29tLnVraW5nLm9uZXBpZWNld2FsbHBhcGVyX3NjcmVlbl8xXzE1MjQyMTY4NjZfMDU4/screen-1.jpg?h=800&fakeurl=1"

    const val TEXT_CONTENT_DEFAULT = "I have been experimenting with status bar for about 2 days. As I go deep i got to know new things about status bar and how to achieve the transparent status bar.\n" +
            "\n" +
            "This article is all about what i learned in that 2 days and also to help you so that you don’t have to research all like i did. If you already know much about status bar please put that in comment section i love to know that.\n" +
            "\n" +
            "There are two ways to achieve the transparent status bar. As both example contains android:fitsSystemWindows=”true” and it should be applied to root container(parent layout).\n" +
            "\n" +
            "Example First\n" +
            "\n" +
            "In this example we are using DrawerLayout and Coordinatorlayout and Navigation View as its child views.\n" +
            "\n" +
            "This is what we want to achieve:"

    fun changeColorLightStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun makeStatusBarNoLimit(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    fun changeColorBackgroundStatusBar(activity: Activity, color: Int) {
        activity.window.statusBarColor = Color.YELLOW
    }

    fun resolveTransparentStatusBarFlag(activity: Activity): Int {
        activity.packageManager?.systemSharedLibraryNames?.let { libs ->
            var reflect: String? = null
            libs.forEach { lib ->
                if (lib.equals("touchwiz")) {
                    reflect = "SYSTEM_UI_FLAG_TRANSPARENT_BACKGROUND"
                } else if (lib.startsWith("com.sonyericsson.navigationbar")) {
                    reflect = "SYSTEM_UI_FLAG_TRANSPARENT"
                }
            }

            reflect?.let {
                try {
                    val field = View::class.java.getField(it)
                    if (field.type == Integer.TYPE) {
                        return field.getInt(null)
                    }
                    return 0
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    return 0
                }
            }

            return 0
        }

        return 0
    }

    fun applyTransparentStatusBarAndroidOld(activity: Activity) {
        activity.window?.decorView?.systemUiVisibility = resolveTransparentStatusBarFlag(activity)
    }
}
