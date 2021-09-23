package pdf.reader.simplepdfreader.tools

import android.util.Log
import pdf.reader.simplepdfreader.data.core.PdfFilesRepository
import java.io.File

interface FileNameChanger {

    suspend fun changeName(path: String, newName: String): Boolean

    class Base(private val pdfFilesRepository: PdfFilesRepository): FileNameChanger {
        override suspend fun changeName(path: String, newName: String): Boolean {
            val filePath = File(path).absolutePath
            val oldPath = File(filePath)
            val newPath = File("${File(path).parentFile}/$newName.pdf")
            val info = oldPath.renameTo(newPath)
            Log.d("pos", "IS $info  || NEW $newPath")

            pdfFilesRepository.updatePath(path,newPath.absolutePath)
            pdfFilesRepository.updateName(newPath.absolutePath,newName)

            return info

        }
    }
}
