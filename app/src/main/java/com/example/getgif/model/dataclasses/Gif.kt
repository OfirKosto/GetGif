package com.example.getgif.model.dataclasses

import com.google.gson.annotations.SerializedName

data class Gif(
    @SerializedName("url") val url: String
)