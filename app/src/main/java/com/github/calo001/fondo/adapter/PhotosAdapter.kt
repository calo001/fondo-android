package com.github.calo001.fondo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.TooltipCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.github.calo001.fondo.GlideApp
import com.github.calo001.fondo.R
import com.github.calo001.fondo.model.Photo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotosAdapter(private var items: MutableList<Photo?>,
                    private val context: Context,
                    private val interaction: OnItemInteraction
) :
    RecyclerView.Adapter<PhotosAdapter.DynamicViewHolder>() {

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
                items[position]?.let {
                    holder.bind(it, interaction)
                }
            }
            is HeaderViewHolder -> {
                holder.bind(title)
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

    abstract class DynamicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), LayoutContainer
    class ProgressViewHolder(itemView: View): DynamicViewHolder(itemView) {
        override val containerView: View?
            get() = itemView
    }

    class HeaderViewHolder(itemView: View): DynamicViewHolder(itemView) {
        override val containerView: View? = itemView

        fun bind(title: String) {
            containerView?.header?.text = title
        }
    }

    class ItemViewHolder(itemView: View) : DynamicViewHolder(itemView) {
        override val containerView: View?
            get() = itemView

        fun bind(item: Photo, interaction: OnItemInteraction) {
            GlideApp.with(itemView.context)
                .load(item.urls.small)
                .placeholder(R.drawable.back_loading_photo)
                .thumbnail(0.01f)
                .fitCenter()
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.imgPhoto)

            containerView?.txtAutor?.let { txtAuthor ->
                txtAuthor.text = item.user.name
                val tooltipText =  txtAuthor.resources?.getString(R.string.author)
                TooltipCompat.setTooltipText(txtAuthor, tooltipText)
            }


            containerView?.cardPhoto?.setOnClickListener { _ ->
                interaction.onItemClick(item)
            }

            containerView?.btnSuccess?. let { btnSuccess ->
                btnSuccess.visibility = View.GONE
                val tooltipText =  btnSuccess.resources?.getString(R.string.chossen)
                TooltipCompat.setTooltipText(btnSuccess, tooltipText)
            }

            containerView?.btnShare?.let {
                TooltipCompat.setTooltipText(it, it.resources.getString(R.string.share_photo))
                it.setOnClickListener {
                    interaction.onShareClick(item)
                }
            }

            containerView?.btnSetWall?.let { btnWall ->
                btnWall.visibility = View.VISIBLE
                val tooltipText =  btnWall.resources.getString(R.string.set_as_wallpaper)
                TooltipCompat.setTooltipText(btnWall, tooltipText)
                btnWall.setOnClickListener { view ->
                    val animOut = AnimationUtils.loadAnimation(view.context, R.anim.fade_out_rotate)
                    view.startAnimation(animOut)
                    view.visibility = View.GONE

                    interaction.onSetWallClick(item)

                    val btnSuccess = containerView?.btnSuccess
                    val animIn = AnimationUtils.loadAnimation(btnSuccess?.context, R.anim.fade_in_rotate)
                    btnSuccess?.visibility = View.VISIBLE
                    btnSuccess?.startAnimation(animIn)
                }
            }
        }
    }

    interface OnItemInteraction {
        fun onItemClick(item: Photo)
        fun onShareClick(photo: Photo)
        fun onSetWallClick(photo: Photo)
    }
}