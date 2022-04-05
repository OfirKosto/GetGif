package com.example.getgif.model.dataclasses

import com.google.gson.annotations.SerializedName

data class Gif(
    @SerializedName("id") val id: String? = null,
    @SerializedName("url") val url: String? = null,
)