package com.devshub.artbook.repo

import androidx.lifecycle.LiveData
import com.devshub.artbook.api.ArtBookApi
import com.devshub.artbook.db.Art
import com.devshub.artbook.db.ArtDao
import com.devshub.artbook.model.ImageResponse
import com.devshub.artbook.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val artBookApi: ArtBookApi
) : ArtRepositoryInterface {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = artBookApi.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }
}