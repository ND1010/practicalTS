package com.dhruv.apps.historyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.bumptech.glide.Glide
import com.dhruv.apps.historyapp.R
import com.dhruv.apps.historyapp.databinding.RawFeedBinding
import com.dhruv.apps.historyapp.resmodel.UserFeedResponse


class UserFeedAdapter() : RecyclerView.Adapter<UserFeedAdapter.ViewHolder>() {
    private var mList = ArrayList<UserFeedResponse.Data.User>()
    private val viewPool = RecycledViewPool()

    fun setUser(list: ArrayList<UserFeedResponse.Data.User>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFeedAdapter.ViewHolder {
        val binding: RawFeedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.raw_feed, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFeedAdapter.ViewHolder, position: Int) {
        val itemImage = mList[position].items

        val layoutManager = GridLayoutManager(
            holder.itemBinding.recyclerviewUserPhotos
                .context,itemImage.size)

        holder.itemBinding.tvName.text= mList[position].name
        Glide.with(holder.itemBinding.civUserImage.context).load(mList[position].image)
            .into(holder.itemBinding.civUserImage)

        val imageAdapter = ImageFeedAdapter()
        holder.itemBinding.recyclerviewUserPhotos.layoutManager = layoutManager
        holder.itemBinding.recyclerviewUserPhotos.adapter = imageAdapter
        imageAdapter.setUser(itemImage)
        holder.itemBinding.recyclerviewUserPhotos.setRecycledViewPool(viewPool)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(var itemBinding: RawFeedBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
    }
}