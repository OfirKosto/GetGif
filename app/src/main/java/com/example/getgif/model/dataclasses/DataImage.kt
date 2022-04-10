package com.example.getgif.model.dataclasses

import com.google.gson.annotations.SerializedName

data class DataImage (
    @SerializedName("original") val originalImage: Gif,
    @SerializedName("downsized") val downsizedImage: Gif
    )