package com.transportation.bookcar.app.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.Collections
import kotlin.collections.ArrayList

abstract class BaseAdapter<T, VH : BaseViewHolder> : RecyclerView.Adapter<VH>() {

    protected var mListItems: ArrayList<T> = ArrayList()
    protected abstract fun getLayoutId(viewType: Int): Int
    protected abstract fun createViewHolder(view: View): VH

    fun getListItems(): ArrayList<T> {
        return mListItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)
        return createViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListItems.size
    }

    fun clear() {
        mListItems.clear()
        notifyDataSetChanged()
    }

    fun setListItem(list: List<T>) {
        mListItems.clear()
        mListItems.addAll(list)
        notifyDataSetChanged()
    }

    fun addListItem(list: List<T>?) {
        if (list != null && list.isNotEmpty()) {
            val oldSize = itemCount
            this.mListItems.addAll(list)
            val newSize = itemCount
            notifyItemRangeInserted(oldSize, newSize - oldSize)
        }
    }

    fun addListItem(array: Array<T>?) {
        if (array != null && array.isNotEmpty()) {
            val oldSize = itemCount
            this.mListItems.addAll(array)
            val newSize = itemCount
            notifyItemRangeInserted(oldSize, newSize - oldSize)
        }
    }

    operator fun get(position: Int): T {
        return mListItems[position]
    }

    fun add(element: T) {
        mListItems.add(element)
    }

    fun addAndNotify(element: T) {
        mListItems.add(element)
        notifyItemInserted(itemCount - 1)
    }

    fun add(index: Int, element: T) {
        mListItems.add(index, element)
    }

    fun removeAt(index: Int): T {
        return mListItems.removeAt(index)
    }

    fun removeAndNotify(index: Int): T {
        val t = mListItems.removeAt(index)
        notifyItemRemoved(index)
        return t
    }

    fun sort(c: Comparator<in T>) {
        Collections.sort(mListItems, c)
    }
}
