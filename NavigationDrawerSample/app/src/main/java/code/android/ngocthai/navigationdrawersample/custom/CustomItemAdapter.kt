package code.android.ngocthai.navigationdrawersample.custom

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import code.android.ngocthai.navigationdrawersample.R
import code.android.ngocthai.navigationdrawersample.entity.ItemCustom
import kotlinx.android.synthetic.main.item_custom.view.*

class CustomItemAdapter(
        private val listener: ItemCustomListener
) : RecyclerView.Adapter<CustomItemAdapter.ViewHolder>() {

    interface ItemCustomListener {
        fun onItemClicked(item: ItemCustom)
    }

    protected val items = mutableListOf<ItemCustom>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_custom, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(view: View, val listener: ItemCustomListener) : RecyclerView.ViewHolder(view) {

        fun bind(item: ItemCustom) {

            itemView.imageIcon.setImageResource(item.icon)

            itemView.textTitle.text = item.title

            if (item.notification != 0) {
                itemView.textNotification.text = item.notification.toString()
            } else {
                itemView.textNotification.visibility = View.GONE
            }

            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }

    fun updateAllData(newList: List<ItemCustom>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }
}
