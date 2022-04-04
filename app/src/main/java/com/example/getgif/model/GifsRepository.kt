package com.example.getgif.model

import com.example.getgif.model.dataclasses.DataResult
import com.example.getgif.model.util.GifApiUtil
import retrofit2.Callback

object GifsRepository {

    fun getTrendingGifs(callback: Callback<DataResult>) = GifApiUtil.getTrendingGifs(callback)

    fun getGifsByName(nameToSearch: String, callback: Callback<DataResult>) = GifApiUtil.getGifsByName(nameToSearch, callback)
}