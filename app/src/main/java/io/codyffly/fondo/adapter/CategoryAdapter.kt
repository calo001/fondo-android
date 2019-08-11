package io.codyffly.fondo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.codyffly.fondo.R
import io.codyffly.fondo.model.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(val items: List<Category>, val context: Context?) : androidx.recyclerview.widget.RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.categoryName.text = context?.getText(items.get(position).name)
        viewHolder.categoryName.setCompoundDrawablesWithIntrinsicBounds(items.get(position).icon, 0,0,0)
        viewHolder.cardCategory.setBackgroundResource(items.get(position).background)
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder (itemView){
        val categoryName = itemView.txtCategoryName
        val cardCategory = itemView.cardCategory
    }
}