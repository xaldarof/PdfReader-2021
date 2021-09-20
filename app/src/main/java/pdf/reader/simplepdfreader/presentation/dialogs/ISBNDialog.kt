package pdf.reader.simplepdfreader.presentation.dialogs

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pdf.reader.simplepdfreader.R

interface ISBNDialog {

    fun show(context: Context)

    class Base : ISBNDialog {
        override fun show(context: Context) {
            val alertDialog = MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
                .setMessage(R.string.info_isbn)
                .setTitle("Информация о ISBN")
                .setPositiveButton(
                    "Понятно"
                ) { p0, p1 -> }
            alertDialog.show()

        }

    }
}