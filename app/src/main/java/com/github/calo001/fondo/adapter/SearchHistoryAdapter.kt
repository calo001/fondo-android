package com.github.calo001.fondo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.github.calo001.fondo.R
import com.github.calo001.fondo.model.SearchHistory
import kotlinx.android.synthetic.main.item_search_history.view.*

class SearchHistoryAdapter(
    private var items: MutableList<String>,
    val context: Context,
    val listener: OnSearchHistoryClickListener) : RecyclerView.Adapter<SearchHistoryAdapter.SearchViewHolder>() {
    var backupList: List<String>? = null

    init {
        backupList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_search_history, parent, false)
        return SearchViewHolder((view))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.item.text = items[position]
        holder.contraint.setOnClickListener {
            listener.onSearchHistoryClick(items[position])
        }
        holder.btnClose.setOnClickListener {
            listener.onDeleteItem(items[position])
            items.removeAt(position)
            backupList = items
            notifyDataSetChanged()
            //Toast.makeText(context, "Delete", Toast.LENGTH_LONG).show()
        }
    }

    fun filter(value: String) {
        if (value.isNotEmpty()) {
            items = items.filter { it.contains(value) } as MutableList<String>
        } else {
            backupList?.let { items = it as MutableList<String> }
        }
        notifyDataSetChanged()
    }

    class SearchViewHolder(holder: View): RecyclerView.ViewHolder(holder) {
        val item: TextView = holder.term
        val btnClose: ImageView = holder.btnDelete
        val contraint: ConstraintLayout = holder.contraint
    }

    interface OnSearchHistoryClickListener {
        fun onSearchHistoryClick(value: String)
        fun onDeleteItem(item: String)
    }
}