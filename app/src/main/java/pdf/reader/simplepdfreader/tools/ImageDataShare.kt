package pdf.reader.simplepdfreader.tools

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import pdf.reader.simplepdfreader.core.Share
import java.io.File

interface ImageDataShare<T> {

    class Base(private val context: Context) : Share<String>{

        override fun share(path:String) {
            val fileUri: Uri = FileProvider.getUriForFile(context, "pdf.reader.simplepdfreader", File(path))
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
            shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            shareIntent.type = "image/jpeg"
            context.startActivity(Intent.createChooser(shareIntent, "Поделитесь.."))
        }
    }
}