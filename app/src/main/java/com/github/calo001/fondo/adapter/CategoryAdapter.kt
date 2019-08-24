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
import kotlinx.android.synthetic.main.item_header.view.*

class CategoryAdapter(private val items: List<Category?>,
                      private val listener: OnCategoryInteraction,
                      private val context: Context?) : RecyclerView.Adapter<CategoryAdapter.BaseHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return when(viewType) {
            VIEW_TYPE_HEADER -> {
                HeaderHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_header,
                        parent,
                        false
                    )
                )
            }
            else -> {
                CategoryHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_category,
                        parent,
                        false
                    )
                )
            }
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> VIEW_TYPE_HEADER
            else -> VIEW_TYPE_CATEGORY
        }
    }

    override fun onBindViewHolder(viewHolder: BaseHolder, position: Int) {
        when (viewHolder) {
            is HeaderHolder -> {
                viewHolder.header.text = context?.resources?.getText(R.string.categories)
            }

            is CategoryHolder -> {
                viewHolder.categoryName.text = items[position]?.name?.let { context?.getText(it) }
                items[position]?.icon?.let {
                    viewHolder.categoryName.setCompoundDrawablesWithIntrinsicBounds(0,
                        it,0,0)
                }
                items[position]?.background?.let { viewHolder.cardCategory.setBackgroundResource(it) }

                viewHolder.cardCategory.setOnClickListener {
                    items[position]?.let { listener.onCategoryInteraction(it) }
                }
            }
        }
    }

    open class BaseHolder(itemView: View) : RecyclerView.ViewHolder (itemView)

    class CategoryHolder(itemView: View) : BaseHolder (itemView) {
        val categoryName: TextView = itemView.txtCategoryName
        val cardCategory: CardView = itemView.cardCategory
    }

    class HeaderHolder(itemView: View) : BaseHolder (itemView) {
        val header: TextView = itemView.header
    }

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_CATEGORY = 1
    }

    interface OnCategoryInteraction {
        fun onCategoryInteraction(category: Category)
    }
}