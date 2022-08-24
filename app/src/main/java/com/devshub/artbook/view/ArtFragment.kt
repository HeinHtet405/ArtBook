package com.devshub.artbook.view

import androidx.fragment.app.Fragment
import com.devshub.artbook.R
import com.devshub.artbook.adapter.ArtRVAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtFragment @Inject constructor(
    val artRVAdapter: ArtRVAdapter
) : Fragment(R.layout.fragment_art){

}