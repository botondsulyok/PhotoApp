package hu.bme.photoapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class User(
    val token: String = "",
    val _id: String = "",
    val email: String = ""
)