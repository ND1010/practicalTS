package com.dhruv.apps.historyapp.resmodel


import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName

data class UserFeedResponse(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: Any = Any(),
    @SerializedName("status")
    var status: Boolean = false
) {
    data class Data(
        @SerializedName("has_more")
        var hasMore: Boolean = false,
        @SerializedName("users")
        var users: List<User> = listOf()
    ) {
        data class User(
            @SerializedName("image")
            var image: String = "",
            @SerializedName("items")
            var items: ArrayList<String> = ArrayList(),
            @SerializedName("name")
            var name: String = ""
        )
    }
    companion object{
        @JvmStatic
        @BindingAdapter("android:src")
        fun loadImage(view:AppCompatImageView,imageUrl:String){
            Glide.with(view.context).load(imageUrl).into(view)
        }

        @JvmStatic
        @BindingAdapter("feedImage")
        fun loadFeedImage(view:AppCompatImageView,imageUrl:String){
            Glide.with(view.context).load(imageUrl).into(view)
        }

    }
}