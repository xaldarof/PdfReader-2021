package pdf.reader.simplepdfreader.tools

import android.widget.RelativeLayout

interface BarAnimator {

    fun check()

    class Base(private val bottomBar: RelativeLayout, private val topBar: RelativeLayout) :
        BarAnimator {

        var isOpen: Boolean = true

        override fun check() {
            isOpen = if (isOpen) {
                topBar.animate().translationY(-200F)
                bottomBar.animate().translationY(200F)
                false

            } else {
                topBar.animate().translationY(0F)
                bottomBar.animate().translationY(0F)
                true
            }
        }
    }
}