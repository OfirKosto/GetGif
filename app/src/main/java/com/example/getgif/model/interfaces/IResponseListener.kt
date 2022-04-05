package com.example.getgif.model.interfaces

import com.example.getgif.model.dataclasses.Gif

interface IResponseListener {

    fun getResponse(gifsList: List<Gif>?, isSuccessful : Boolean)
}