package io.codyffly.fondo.adapter

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import io.codyffly.fondo.R
import io.codyffly.fondo.config.Constants
import io.codyffly.fondo.model.Photo
import io.codyffly.fondo.ui.detail.PhotoDetailActivity
import io.codyffly.fondo.ui.main.fragment.photo.OnItemInteraction
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotosAdapter(private var items: MutableList<Photo?>, val context: Context, private val interaction: OnItemInteraction) :
    RecyclerView.Adapter<PhotosAdapter.ItemViewHolder>() {

    private var glide: RequestManager = Glide.with(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_photo, parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){
        glide
            .load(items[position]?.urls?.small)
            .transition(withCrossFade())
            .into(holder.photo)

        holder.autor.text = items[position]?.user?.name

        holder.photo.setOnClickListener { view ->
            items[position]?.let { photo ->
                interaction.onItemInteraction(view, photo)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun addPage(list: List<Photo>) {
        items = (items + list) as MutableList<Photo?>
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : ViewHolder (itemView) {
        val autor: TextView = itemView.txtAutor
        val photo: ImageView = itemView.imgPhoto
    }
}