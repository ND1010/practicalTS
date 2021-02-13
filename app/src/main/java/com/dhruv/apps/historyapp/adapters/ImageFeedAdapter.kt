package com.dhruv.apps.historyapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhruv.apps.historyapp.R
import com.dhruv.apps.historyapp.databinding.RawImageBinding

class ImageFeedAdapter() : RecyclerView.Adapter<ImageFeedAdapter.ViewHolder>() {
    private var mList = ArrayList<String>()

    fun setUser(list: ArrayList<String>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFeedAdapter.ViewHolder {
        val binding: RawImageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.raw_image, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageFeedAdapter.ViewHolder, position: Int) {
        Glide.with(holder.itemBinding.ivFeedImage.context).load(mList[position])
            .into(holder.itemBinding.ivFeedImage)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(var itemBinding: RawImageBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }
}