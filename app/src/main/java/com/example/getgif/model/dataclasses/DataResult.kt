package com.example.getgif.model.dataclasses

import com.google.gson.annotations.SerializedName

data class DataResult(
    @SerializedName("data") val dataObjectList: List<DataObject>
)