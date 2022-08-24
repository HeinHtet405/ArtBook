package com.devshub.artbook.di

import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.devshub.artbook.R
import com.devshub.artbook.api.ArtBookApi
import com.devshub.artbook.db.ArtDao
import com.devshub.artbook.db.ArtDatabase
import com.devshub.artbook.repo.ArtRepository
import com.devshub.artbook.repo.ArtRepositoryInterface
import com.devshub.artbook.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ArtDatabase::class.java, "ArtBookDb").build()

    @Singleton
    @Provides
    fun injectDao(
        database: ArtDatabase
    ) = database.artDao()

    @Singleton
    @Provides
    fun injectArtBookApi() : ArtBookApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ArtBookApi::class.java)
    }

    @Singleton
    @Provides
    fun injectRepository(dao: ArtDao, api: ArtBookApi) = ArtRepository(dao, api) as ArtRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
        )

}