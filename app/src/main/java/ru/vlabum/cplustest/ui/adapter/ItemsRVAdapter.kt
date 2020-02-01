package layout

import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.item_item.view.*
import ru.vlabum.cplustest.R
import ru.vlabum.cplustest.presenter.IItemListPresenter
import ru.vlabum.cplustest.view.IItemRowView
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
                    itemView.iv_icon.setImageBitmap(BitmapFactory.decodeFile(path))
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}