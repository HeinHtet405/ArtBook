package com.devshub.artbook.view

import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.devshub.artbook.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailFragment @Inject constructor(
    val glide : RequestManager
) : Fragment(R.layout.fragment_art_details) {

}