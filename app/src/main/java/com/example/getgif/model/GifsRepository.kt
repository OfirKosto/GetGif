package com.example.getgif.model

import com.example.getgif.model.dataclasses.DataResult
import com.example.getgif.model.dataclasses.Gif
import com.example.getgif.model.interfaces.IResponseListener
import com.example.getgif.model.util.GifApiUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GifsRepository {

    private fun createMyCallback(listener: IResponseListener): Callback<DataResult> = object : Callback<DataResult> {
        override fun onResponse(call: Call<DataResult>, response: Response<DataResult>) {
            if (response.isSuccessful) {
                if (response.body() != null) {
                    if(response.body()!!.gifsList != null)
                    {
                        listener.getResponse(response.body()!!.gifsList, true)
                    }
                    else
                    {
                        listener.getResponse(listOf<Gif>(), true)
                    }
                }
            } else {
                listener.getResponse(null, false)
            }
        }

        override fun onFailure(call: Call<DataResult>, t: Throwable) {
            listener.getResponse(null, false)
        }
    }

    fun getTrendingGifs(listener: IResponseListener) = GifApiUtil.getTrendingGifs(createMyCallback(listener))



    fun getGifsByName(nameToSearch: String, listener: IResponseListener) = GifApiUtil.getGifsByName(nameToSearch, createMyCallback(listener))

}