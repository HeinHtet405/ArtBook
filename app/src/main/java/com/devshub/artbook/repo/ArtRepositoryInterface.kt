package com.devshub.artbook.repo

import androidx.lifecycle.LiveData
import com.devshub.artbook.db.Art
import com.devshub.artbook.model.ImageResponse
import com.devshub.artbook.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>

}