package pdf.reader.simplepdfreader.presentation.dialogs

import android.util.Log
import android.view.View
import pdf.reader.simplepdfreader.R
import pdf.reader.simplepdfreader.data.room.BookDb
import java.lang.reflect.Field

interface BookPopMenu {

    fun show(view: View, bookDb: BookDb)

    fun registerCallBack(popupCallBack: Base.CustomPopupCallBack)

    class Base : BookPopMenu {

        private var callBack:CustomPopupCallBack? = null

        override fun registerCallBack(popupCallBack: CustomPopupCallBack) {
            this.callBack = popupCallBack
        }

        interface CustomPopupCallBack {
            fun sendResult(result:String)
        }

        override fun show(view: View, bookDb: BookDb) {
            val popupMenu = androidx.appcompat.widget.PopupMenu(view.context, view)
            popupMenu.inflate(R.menu.menu)

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.open -> {
                        callBack?.sendResult(bookDb.isbn)
                        Log.d("pos", "CLICKED OPEN ${bookDb.isbn}")
                    }
                    R.id.delete -> {
                        Log.d("pos", "CLICKED DELETE ${bookDb.author}")
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
