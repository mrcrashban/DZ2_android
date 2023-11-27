package com.example.dz2_min.model

import com.google.gson.annotations.SerializedName

data class Beer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val image_url: String
)
