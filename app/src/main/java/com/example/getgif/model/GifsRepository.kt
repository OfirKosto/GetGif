package com.example.getgif.model

import com.example.getgif.model.util.GifApiUtil

object GifsRepository {

    private const val API_KEY: String  = "Zz7XnA0RZzJJetQAQv1e2c7ErivA9F5u"
    private const val RATING: String  = "g"
    private const val LIMIT: Int = 40

    suspend fun getTrendingGifs() = GifApiUtil.retroService.getTrendingGifs(LIMIT, RATING, API_KEY)

    suspend fun getGifsByName(nameToSearch: String) = GifApiUtil.retroService.getGifsByName(nameToSearch, LIMIT, RATING, API_KEY)



}