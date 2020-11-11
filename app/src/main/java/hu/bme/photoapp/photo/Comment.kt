package hu.bme.photoapp.photo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Comment(
    //val _id: String,
    val user: String,
    val text: String
)