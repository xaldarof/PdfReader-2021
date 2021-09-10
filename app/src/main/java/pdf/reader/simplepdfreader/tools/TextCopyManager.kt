package pdf.reader.simplepdfreader.tools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

interface TextCopyManager {

    fun copyToClipboard(text:String):Boolean

    class Base(private val context: Context) : TextCopyManager {
        override fun copyToClipboard(text: String) : Boolean {
            val copyManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val copyData = ClipData.newPlainText("body",text)
            copyManager.setPrimaryClip(copyData)
            Toast.makeText(context,"Текст скопирован в буфер обмена",Toast.LENGTH_LONG)
                .show()
            return true
        }
    }
}