package com.devshub.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devshub.artbook.R
import com.devshub.artbook.adapter.ArtRVAdapter
import com.devshub.artbook.databinding.FragmentArtBinding
import com.devshub.artbook.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtFragment @Inject constructor(
    private val artRVAdapter: ArtRVAdapter
) : Fragment(R.layout.fragment_art) {

    private lateinit var viewModel: ArtViewModel

    private var fragmentBindings: FragmentArtBinding? = null
    private val swipeCallBack =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.layoutPosition
                val selectedArt = artRVAdapter.arts[layoutPosition]
                viewModel.deleteArt(selectedArt)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        val binding = FragmentArtBinding.bind(view)
        fragmentBindings = binding

        subscribeToObservers()

        binding.rvArt.adapter = artRVAdapter
        binding.rvArt.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.rvArt)

        binding.fabArt.setOnClickListener {
            findNavController().navigate(
                ArtFragmentDirections.actionArtFragmentToArtDetailsFragment()
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner) {
            artRVAdapter.arts = it
        }
    }

    override fun onDestroyView() {
        fragmentBindings = null
        super.onDestroyView()
    }
}