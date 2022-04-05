package com.example.getgif.model.interfaces

import com.example.getgif.model.dataclasses.DataResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IGifApi {

    @GET("trending")
    suspend fun getTrendingGifs(
        @Query("limit") maxNumberOfObjects: Int,
        @Query("rating") rating: String,
        @Query("api_key") apiKey: String
    ) : Response<DataResult>


    @GET("search")
    suspend fun getGifsByName(
        @Query("q") nameToSearch: String,
        @Query("limit") maxNumberOfObjects: Int,
        @Query("rating") rating: String,
        @Query("api_key") apiKey: String
    ) : Response<DataResult>

}