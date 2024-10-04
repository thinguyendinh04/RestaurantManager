package com.dinhthi2004.restaurantmanager.di

import com.dinhthi2004.restaurantmanager.common.Constant
import com.dinhthi2004.restaurantmanager.data.remote.ApiService
import com.dinhthi2004.restaurantmanager.data.repository.MenuRepositoryImpl
import com.dinhthi2004.restaurantmanager.domain.repository.MenuRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesMenuApi(): ApiService {
        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesMenuRepository(api: ApiService): MenuRepository {
        return MenuRepositoryImpl(api)
    }
}