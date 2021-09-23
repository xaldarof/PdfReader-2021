package pdf.reader.simplepdfreader.presentation.dialogs

import android.app.Dialog
import android.content.Context
import pdf.reader.simplepdfreader.R

interface Api30Error {
    fun show()

    class Base(private val context: Context): Api30Error {
        override fun show() {
            val dialog = Dialog(context)
            dialog.setOnCancelListener {
                show()
            }
            dialog.setContentView(R.layout.deleted_item)
            dialog.show()
        }
    }
}