package com.devshub.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.devshub.artbook.R
import com.devshub.artbook.adapter.ImageRVAdapter
import com.devshub.artbook.databinding.FragmentImageApiBinding
import com.devshub.artbook.util.Status
import com.devshub.artbook.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageApiFragment @Inject constructor(
    private val imageRVAdapter: ImageRVAdapter
) : Fragment(R.layout.fragment_image_api) {

    private lateinit var viewModel: ArtViewModel
    private var fragmentBinding: FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        var job: Job? = null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        subscribeToObservers()

        binding.imageRecyclerView.adapter = imageRVAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        imageRVAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }

    private fun subscribeToObservers() {
        viewModel.imageList.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult -> imageResult.previewURL }
                    imageRVAdapter.images = urls ?: listOf()
                    fragmentBinding?.progressBar?.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_SHORT)
                        .show()
                    fragmentBinding?.progressBar?.visibility = View.GONE
                }
                Status.LOADING -> {
                    fragmentBinding?.progressBar?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}