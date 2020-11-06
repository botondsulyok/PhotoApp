package hu.bme.photoapp.home

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Image(
    val name: String,
    val author: String,
    val description: String,
    val url: String,
    val _id: String
)