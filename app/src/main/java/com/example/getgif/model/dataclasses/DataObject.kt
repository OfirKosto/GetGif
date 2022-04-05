package com.example.getgif.model.dataclasses

import com.google.gson.annotations.SerializedName

data class DataObject (
    @SerializedName("images") val images: DataImage
    )