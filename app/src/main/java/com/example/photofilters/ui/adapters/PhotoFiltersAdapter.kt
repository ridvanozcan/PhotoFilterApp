package com.example.photofilters.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.photofilters.R
import com.example.photofilters.data.models.PhotoFiltersModel
import com.example.photofilters.databinding.PhotofiltersListItemBinding

class PhotoFiltersAdapter (private val items : ArrayList<PhotoFiltersModel>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<PhotoFiltersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoFiltersViewHolder {
        return PhotoFiltersViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.photofilters_list_item, parent,   false))
    }

    override fun getItemCount(): Int { return items.size }

    override fun onBindViewHolder(holder: PhotoFiltersViewHolder, position: Int) {
        val item = items[position]
        holder.itemBinding.photofilters = item
        item.overlayImageBitMap.let {
            holder.itemBinding.overlayIcon.setImageBitmap(item.overlayImageBitMap)
        }
        holder.itemView.setOnClickListener { onClickListener.onClick(item)}
    }

    class OnClickListener(val clickListener: (photoFiltersItem: PhotoFiltersModel) -> Unit) {
        fun onClick(photoFiltersItem: PhotoFiltersModel) = clickListener(photoFiltersItem)
    }

}

class PhotoFiltersViewHolder(val itemBinding: PhotofiltersListItemBinding) : RecyclerView.ViewHolder(itemBinding.root)
