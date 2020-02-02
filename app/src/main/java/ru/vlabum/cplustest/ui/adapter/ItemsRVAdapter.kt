package layout

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_add_item.iv_add_icon
import kotlinx.android.synthetic.main.activity_add_item.view.*
import kotlinx.android.synthetic.main.item_item.view.*
import ru.vlabum.cplustest.R
import ru.vlabum.cplustest.presenter.IItemListPresenter
import ru.vlabum.cplustest.ui.activity.MainActivity
import ru.vlabum.cplustest.view.IItemRowView
import java.io.File
import java.lang.Exception

class ItemsRVAdapter(var listItems: IItemListPresenter) :
    RecyclerView.Adapter<ItemsRVAdapter.ViewHolderItems>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItems {
        return ViewHolderItems(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listItems.getCount()
    }

    override fun onBindViewHolder(holder: ViewHolderItems, position: Int) {
        holder.setPos(position)

        listItems.bind(holder)
        holder.itemView.clicks().map { o -> holder }.subscribe(listItems.getClickSubject())
    }


    class ViewHolderItems(itemView: View) : RecyclerView.ViewHolder(itemView), IItemRowView {

        private var pos: Int = 0

        override fun getPos(): Int = pos

        fun setPos(pos: Int) {
            this.pos = pos
        }

        override fun setName(name: String) {
            itemView.tv_name.text = name
        }

        override fun setImage(path: String?) {
            try {
                path?.let {

                    val selectedImage: Uri = Uri.fromFile(File(path))

                        if (Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                itemView.context.contentResolver,
                                selectedImage
                            )
                            itemView.iv_icon.setImageBitmap(bitmap)
                        }
                        else {
                            val source = ImageDecoder.createSource(itemView.context.contentResolver, selectedImage)

                            val bitmap = ImageDecoder.decodeBitmap(source)
                            itemView.iv_icon.setImageBitmap(bitmap)
                        }
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}