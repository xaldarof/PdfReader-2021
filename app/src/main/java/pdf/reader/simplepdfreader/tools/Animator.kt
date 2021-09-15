package pdf.reader.simplepdfreader.tools

import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_FADE
import com.google.android.material.snackbar.Snackbar

interface Animator {

    fun animate(view: View)

    class Base : Animator {
        override fun animate(view: View) {
            YoYo.with(Techniques.RotateIn)
                .duration(1000)
                .repeat(3)
                .playOn(view)
            Snackbar.make(view,"Сканирование...",Snackbar.LENGTH_LONG)
                .setAnimationMode(ANIMATION_MODE_FADE)
                .show()
        }
    }
}