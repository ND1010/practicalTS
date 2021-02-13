package com.dhruv.apps.historyapp.repositories

import androidx.lifecycle.MutableLiveData
import com.dhruv.apps.historyapp.interfaces.NetworkResponseCallback
import com.dhruv.apps.historyapp.network.RestClient
import com.dhruv.apps.historyapp.resmodel.UserFeedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl private constructor() {
    private lateinit var mCallback: NetworkResponseCallback

    private var mListUser: MutableLiveData<List<UserFeedResponse.Data.User>> = MutableLiveData()

    companion object {
        private var mInstance: RepositoryImpl? = null
        fun getInstance(): RepositoryImpl {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = RepositoryImpl()
                }
            }
            return mInstance!!
        }
    }


    private lateinit var mFeedUser : Call<UserFeedResponse>
    fun fetchUser(offset:String,limit:String,callback:NetworkResponseCallback):MutableLiveData<List<UserFeedResponse.Data.User>>{
        mCallback = callback
        mFeedUser = RestClient.getInstance().getApiService().getUser(offset,limit)
        mFeedUser.enqueue(object : Callback<UserFeedResponse>{
            override fun onResponse(
                call: Call<UserFeedResponse>,
                response: Response<UserFeedResponse>
            ) {
                response.body()?.let {
                    if (!it.status){
                        mListUser.value = ArrayList()
                        mCallback.onNetworkSuccess()
                    }
                }
                response.body()?.let {
                    if (it.status){
                        mListUser.value = it.data.users
                        mCallback.onNetworkSuccess()
                    }
                }
            }

            override fun onFailure(call: Call<UserFeedResponse>, t: Throwable) {
                mListUser.value = ArrayList()
                mCallback.onNetworkFailure(t)
            }

        })
        return mListUser

    }



}