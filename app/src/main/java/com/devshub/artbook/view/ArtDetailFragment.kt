package com.devshub.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.devshub.artbook.R
import com.devshub.artbook.databinding.FragmentArtDetailsBinding
import com.devshub.artbook.util.Status
import com.devshub.artbook.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailFragment @Inject constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_art_details) {

    private lateinit var viewModel: ArtViewModel
    private var fragmentBinding: FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.ivArtDetail.setOnClickListener {
            findNavController().navigate(
                ArtDetailFragmentDirections.actionArtDetailFragmentToImageApiFragment()
            )
        }

        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setSelectedImage("")
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding.btnSave.setOnClickListener {
            viewModel.makeArt(
                binding.etName.toString(),
                binding.etArtName.toString(),
                binding.etYear.toString()
            )
        }

    }

    private fun subscribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner) { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.ivArtDetail)
            }
        }

        viewModel.insertArtMessage.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_SHORT)
                        .show()
                }
                Status.LOADING -> {
                    // do nothing
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentBinding = null
    }
}