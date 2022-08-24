package com.devshub.artbook.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.devshub.artbook.adapter.ArtRVAdapter
import com.devshub.artbook.adapter.ImageRVAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val imageRVAdapter: ImageRVAdapter,
    private val glide : RequestManager,
    private val artRVAdapter: ArtRVAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRVAdapter)
            ArtDetailFragment::class.java.name -> ArtDetailFragment(glide)
            ArtFragment::class.java.name -> ArtFragment(artRVAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }

}