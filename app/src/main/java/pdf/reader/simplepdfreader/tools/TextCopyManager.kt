package pdf.reader.simplepdfreader.tools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

interface TextCopyManager {

    fun copyToClipboard(text:String)

    class Base(private val context: Context) : TextCopyManager {
        override fun copyToClipboard(text: String) {
            val copyManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val copyData = ClipData.newPlainText("body",text)
            copyManager.setPrimaryClip(copyData)
        }

    }
}