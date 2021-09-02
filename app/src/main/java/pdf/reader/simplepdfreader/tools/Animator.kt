package pdf.reader.simplepdfreader.tools

import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

interface Animator {

    fun animate(view: View)

    class Base : Animator {
        override fun animate(view: View) {
            YoYo.with(Techniques.RotateIn)
                .duration(1000)
                .repeat(3)
                .playOn(view)
        }
    }
}