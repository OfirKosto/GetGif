package com.example.getgif.model.util

import com.example.getgif.model.dataclasses.DataResult
import com.example.getgif.model.interfaces.IGifApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object GifApiUtil {

    private const val BASE_URL: String = "https://api.giphy.com/v1/gifs/"
    private const val API_KEY: String  = "Zz7XnA0RZzJJetQAQv1e2c7ErivA9F5u"
    private const val RATING: String  = "g"
    private const val LIMIT: Int = 40

    private val retroService: IGifApi by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(IGifApi::class.java)
    }

    suspend fun getTrendingGifs() = retroService.getTrendingGifs(LIMIT, RATING, API_KEY)

    suspend fun getGifsByName(nameToSearch: String) = retroService.getGifsByName(nameToSearch, LIMIT, RATING, API_KEY)
}