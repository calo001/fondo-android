package com.github.calo001.fondo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.github.calo001.fondo.R
import com.github.calo001.fondo.model.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(private val items: List<Category>,
                      private val listener: OnCategoryInteraction,
                      private val context: Context?) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_category,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.categoryName.text = context?.getText(items[position].name)
        viewHolder.categoryName.setCompoundDrawablesWithIntrinsicBounds(0, items[position].icon,0,0)
        viewHolder.cardCategory.setBackgroundResource(items[position].background)

        viewHolder.cardCategory.setOnClickListener {
            listener.onCategoryInteraction(items[position])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView){
        val categoryName: TextView = itemView.txtCategoryName
        val cardCategory: CardView = itemView.cardCategory
    }

    interface OnCategoryInteraction {
        fun onCategoryInteraction(category: Category)
    }
}