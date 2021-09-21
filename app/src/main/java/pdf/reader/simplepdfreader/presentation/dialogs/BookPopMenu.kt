package pdf.reader.simplepdfreader.presentation.dialogs

import android.util.Log
import android.view.View
import androidx.appcompat.widget.PopupMenu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.cloud.BookRepository
import pdf.reader.simplepdfreader.data.room.BookDb
import java.lang.reflect.Field

interface BookPopMenu {

    fun show(view: View, bookDb: BookDb)

    class Base(private val onClickCallBack: OnClickCallBack) : BookPopMenu {

        interface OnClickCallBack{
            suspend fun onClickDelete(bookDb: BookDb)
            fun onClickOpen(isbn:String)
        }

        override fun show(view: View, bookDb: BookDb) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.menu)

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.open -> {
                        onClickCallBack.onClickOpen(bookDb.isbn)
                    }
                    R.id.delete -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            onClickCallBack.onClickDelete(bookDb)
                        }
                    }
                }
                true
            }
            view.setOnClickListener {
                val popup: Field = androidx.appcompat.widget.PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
                popupMenu.show()
            }
        }
    }
}