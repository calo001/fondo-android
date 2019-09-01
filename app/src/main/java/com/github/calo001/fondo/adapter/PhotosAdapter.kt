package com.github.calo001.fondo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.github.calo001.fondo.R
import com.github.calo001.fondo.model.Photo
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotosAdapter(private var items: MutableList<Photo?>,
                    private val context: Context,
                    private val interaction: OnItemInteraction
) :
    RecyclerView.Adapter<PhotosAdapter.DynamicViewHolder>() {

    private var glide: RequestManager = Glide.with(context)
    private lateinit var title: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DynamicViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                HeaderViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_header, parent, false)
                )
            }
            VIEW_TYPE_ITEM -> {
                ItemViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_photo, parent, false)
                )
            }
            else -> {
                ProgressViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.item_loading, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: DynamicViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                glide
                    .load(items[position]?.urls?.small)
                    .placeholder(R.drawable.back_loading_photo)
                    .thumbnail(0.01f)
                    .fitCenter()
                    .transition(withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.photo)

                holder.author.text = items[position]?.user?.name

                holder.cardView.setOnClickListener { view ->
                    items[position]?.let { photo ->
                        interaction.onItemClick(view, photo)
                    }
                }

                holder.btnShare.setOnClickListener {
                    items[position]?.let { photo ->
                        interaction.onShareClick(photo)
                    }
                }

                holder.btnSetWall.setOnClickListener {
                    items[position]?.let { photo ->
                        interaction.onSetWallClick(photo)
                        it.visibility = View.GONE
                    }
                }
            }
            is HeaderViewHolder -> {
                holder.header.text = title
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when {
            (position == 0) and (items[position] == null)   -> VIEW_TYPE_HEADER
            (position >  0) and (items[position] == null)   -> VIEW_TYPE_LOADING
            else                                            -> VIEW_TYPE_ITEM
        }
    }

    fun addHeader(header: String) {
        items.add(null)
        updateHeader(header)
    }

    fun addNullItem() {
        items.add(null)
        val lastPosition = items.lastIndex
        notifyItemInserted(lastPosition)
    }

    fun removeProgressItem() {
        if(items.size > 1) {
            val lastPosition = items.lastIndex
            items.removeAt(lastPosition)
            notifyItemRemoved(lastPosition)
        }
    }

    fun updateHeader(title: String) {
        this.title = title
        notifyItemChanged(0)
    }

    fun addPage(list: List<Photo>) {
        val count = items.size

        if (list.isNotEmpty()) {
            items.addAll(count, list)
            if (count <= 1) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeInserted(count, items.size)
            }
        }
        val last = items[items.lastIndex]
        last
    }

    fun clear() {
        items = items.filter { it == null } as MutableList<Photo?>
        title = ""
        notifyDataSetChanged()
    }

    fun clearOnlyData() {
        items = items.filter { it == null } as MutableList<Photo?>
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
        private const val VIEW_TYPE_HEADER = 2
    }

    open class DynamicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    class ProgressViewHolder(itemView: View): DynamicViewHolder(itemView)
    class HeaderViewHolder(itemView: View): DynamicViewHolder(itemView) {
        val header: TextView = itemView.header
    }

    class ItemViewHolder(itemView: View) : DynamicViewHolder(itemView) {
        val author: TextView = itemView.txtAutor
        val photo: ImageView = itemView.imgPhoto
        val cardView: CardView = itemView.cardPhoto
        val btnShare: ImageView = itemView.btnShare
        val btnSetWall: ImageView = itemView.btnSetWall
    }

    interface OnItemInteraction {
        fun onItemClick(view: View, item: Photo)
        fun onShareClick(photo: Photo)
        fun onSetWallClick(photo: Photo)
    }
}