package com.dhruv.apps.historyapp.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dhruv.apps.historyapp.interfaces.NetworkResponseCallback
import com.dhruv.apps.historyapp.repositories.RepositoryImpl
import com.dhruv.apps.historyapp.resmodel.UserFeedResponse
import com.dhruv.apps.historyapp.utils.NetworkHelper

class MainViewModel : ViewModel() {
    private var mRepository = RepositoryImpl.getInstance()
    private var mListUser: MutableLiveData<List<UserFeedResponse.Data.User>> = MutableLiveData()
    var mShowProgress: MutableLiveData<Boolean> = MutableLiveData()
    var mShowErr: MutableLiveData<Boolean> = MutableLiveData()
    var mShowShowNetworkErr: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchFeed(mContext: Context,offset:String, limit:String):MutableLiveData<List<UserFeedResponse.Data.User>>{
        if(NetworkHelper.isOnline(mContext)){
            mShowProgress.value=true
            mListUser = mRepository.fetchUser(offset,limit,object : NetworkResponseCallback{
                override fun onNetworkSuccess() {
                    mShowProgress.value = false
                }

                override fun onNetworkFailure(th: Throwable) {
                    mShowErr.value = true
                }

            })
        }else{
            mShowShowNetworkErr.value = true
        }
        return mListUser
    }

}