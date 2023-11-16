package com.example.videofeed.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.videofeed.adapter.UserAdapter
import com.example.videofeed.databinding.FragmentVideoBinding
import com.example.videofeed.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoFragment : Fragment() {

    private lateinit var characterAdapter: UserAdapter

    val viewModel by viewModels<UserViewModel>()

    private lateinit var binding: FragmentVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadData()
        return binding.root
    }

    private fun setupRecyclerView() {
        characterAdapter = UserAdapter()
        binding.recyclerView.apply {
            adapter = characterAdapter
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.listData.collect {
                characterAdapter.submitData(it)
            }
        }
    }

}