package com.example.videofeed.activity

import android.app.PendingIntent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.videofeed.adapter.ViewPagerAdapter
import com.example.videofeed.databinding.ActivityMainBinding
import com.example.videofeed.utils.Constants
import com.example.videofeed.utils.MyWorker
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewPager()

        val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun setViewPager() {

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = Constants.VIDEO
                }
                1 -> {
                    tab.text = Constants.FEED
                }
            }
        }.attach()
    }


}