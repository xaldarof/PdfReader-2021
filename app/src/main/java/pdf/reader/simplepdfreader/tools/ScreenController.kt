package pdf.reader.simplepdfreader.tools

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import java.lang.ref.WeakReference

interface ScreenController {

    fun changeScreenOrientation()

    class Base(private val activity: WeakReference<Activity>) : ScreenController {

        var isPortrait: Boolean = true

        @SuppressLint("SourceLockedOrientationActivity")
        override fun changeScreenOrientation() {
            if (isPortrait) {
                activity.get()!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                activity.get()!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    }
}
