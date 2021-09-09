package pdf.reader.simplepdfreader.other

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.content.DialogInterface.BUTTON_POSITIVE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.cache.WarningStateCache

interface Politics {
    fun show()


    class Base(private val context: Context, private val warningStateCache: WarningStateCache) :
        Politics {

        private lateinit var dialog: AlertDialog.Builder

        override fun show() {
            dialog = AlertDialog.Builder(context)
                .setTitle("Политика конфиденциальности")
                .setMessage(context.resources.getString(R.string.politics_text))
                .setPositiveButton("Понятно") { _, _ -> warningStateCache.save(true)}
                .setNegativeButton("Выйти") { _, _ ->
                    dialog.show()
                    warningStateCache.save(true)
                }
            dialog.show()
        }
    }
}