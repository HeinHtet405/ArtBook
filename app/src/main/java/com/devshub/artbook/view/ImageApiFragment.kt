package com.devshub.artbook.view

import androidx.fragment.app.Fragment
import com.devshub.artbook.R
import com.devshub.artbook.adapter.ImageRVAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageApiFragment @Inject constructor(
    val imageRVAdapter: ImageRVAdapter
) : Fragment(R.layout.fragment_image_api) {

}