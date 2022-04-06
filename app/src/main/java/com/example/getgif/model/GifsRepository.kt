package com.example.getgif.model

import com.example.getgif.model.util.GifApiUtil

object GifsRepository {

    private const val API_KEY: String  = "Zz7XnA0RZzJJetQAQv1e2c7ErivA9F5u"
    private const val RATING: String  = "g"
    private const val LIMIT: Int = 40

//    private fun createMyCallback(listener: IResponseListener): Callback<DataResult> = object : Callback<DataResult> {
//        override fun onResponse(call: Call<DataResult>, response: Response<DataResult>) {
//            if (response.isSuccessful) {
//                if (response.body() != null) {
//                    if(response.body()!!.gifsList != null)
//                    {
//                        listener.getResponse(response.body()!!.gifsList, true)
//                    }
//                    else
//                    {
//                        listener.getResponse(listOf<Gif>(), true)
//                    }
//                }
//            } else {
//                listener.getResponse(null, false)
//            }
//        }
//
//        override fun onFailure(call: Call<DataResult>, t: Throwable) {
//            listener.getResponse(null, false)
//        }
//    }
//
//    suspend fun getTrendingGifs(listener: IResponseListener) = GifApiUtil.getTrendingGifs(createMyCallback(listener))
//
//
//
//    suspend fun getGifsByName(nameToSearch: String, listener: IResponseListener) = GifApiUtil.getGifsByName(nameToSearch, createMyCallback(listener))

    suspend fun getTrendingGifs() = GifApiUtil.retroService.getTrendingGifs(LIMIT, RATING, API_KEY)

    suspend fun getGifsByName(nameToSearch: String) = GifApiUtil.retroService.getGifsByName(nameToSearch, LIMIT, RATING, API_KEY)
}