package com.example.videofeed.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.videofeed.activity.DataRecordDetail
import com.example.videofeed.adapter.DataRecordAdapter
import com.example.videofeed.databinding.FragmentFeedBinding
import com.example.videofeed.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        setUp()
        return binding.root
    }

    private fun setUp() {

        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        val adapter = DataRecordAdapter(requireContext())
        binding.recyclerView.adapter = adapter

        noteViewModel.allItems.observe(viewLifecycleOwner, Observer { items ->
            items?.let { adapter.setItems(it)

            if (it.isEmpty()) {
                binding.noDataTextView.visibility = View.VISIBLE
            } else {
                binding.noDataTextView.visibility = View.GONE
            }}

        })

        binding.fabAdd.setOnClickListener { _ ->
            val intent = Intent(requireContext(), DataRecordDetail::class.java)
            startActivity(intent)
        }
    }

}