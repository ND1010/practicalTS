package com.dhruv.apps.historyapp.ui.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.apps.historyapp.R
import com.dhruv.apps.historyapp.adapters.ImageFeedAdapter
import com.dhruv.apps.historyapp.adapters.UserFeedAdapter
import com.dhruv.apps.historyapp.databinding.ActivityMainBinding
import com.dhruv.apps.historyapp.resmodel.UserFeedResponse
import com.dhruv.apps.historyapp.utils.observe
import com.dhruv.apps.historyapp.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "MainActivity"
    }
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var mFeedAdapter: UserFeedAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var arraylistUser = ArrayList<UserFeedResponse.Data.User>()
    private var offset = 0
    private var limit = 10
    private var loading = true
    private var pastVisiblesItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel =mainViewModel
        binding.lifecycleOwner = this
        onInint()
    }

    private fun onInint() {
        initializeObs()
        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        mFeedAdapter = UserFeedAdapter()
        layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerviewFeed.layoutManager = layoutManager
        binding.recyclerviewFeed.adapter =mFeedAdapter

        binding.recyclerviewFeed.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount;
                    totalItemCount = layoutManager.itemCount;
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                     if(loading){
                         if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                             loading = false
                             mainViewModel.fetchFeed(this@MainActivity,offset.toString(),limit.toString())
                         }
                     }
                }
            }
        })
    }


    private fun initializeObs() {
        observe(mainViewModel.fetchFeed(this@MainActivity,offset.toString(),limit.toString())){
            if(it.isNotEmpty()){
                offset =arraylistUser.size
                arraylistUser.addAll(it)
                mFeedAdapter.setUser(arraylistUser)
                loading = it.size>= limit
            }
        }

        observe(mainViewModel.mShowProgress){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}