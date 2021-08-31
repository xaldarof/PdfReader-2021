package pdf.reader.simplepdfreader.tools

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.util.Log
import android.view.Window
import android.widget.*
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.cache.AutoSpacingStateCache
import pdf.reader.simplepdfreader.data.cache.DarkThemeCache

interface ReadingPopupManager {

    fun showPopupMenu()

    class Base(private val activity: Activity, private val darkThemeCache: DarkThemeCache,private val autoSpacingStateCache: AutoSpacingStateCache) :
        ReadingPopupManager {

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        override fun showPopupMenu() {
            val dialog = Dialog(activity,R.style.BottomSheetDialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.reading_settings)

            val switchTheme = dialog.findViewById<Switch>(R.id.darkTheme)
            val switchAutoSpacing = dialog.findViewById<Switch>(R.id.AutoSpacing)
            switchTheme.isChecked = darkThemeCache.read()
            switchAutoSpacing.isChecked = autoSpacingStateCache.read()

            switchTheme.setOnCheckedChangeListener { compoundButton, b ->
                if (compoundButton.isChecked) {
                    darkThemeCache.save(true)

                } else {
                    darkThemeCache.save(false)
                }
                activity.finish()
            }
             switchAutoSpacing.setOnCheckedChangeListener { compoundButton, b ->
                if (compoundButton.isChecked) {
                    autoSpacingStateCache.save(true)
                } else {
                    autoSpacingStateCache.save(false)
                }
                 activity.finish()
            }



            dialog.show()
        }
    }
}
