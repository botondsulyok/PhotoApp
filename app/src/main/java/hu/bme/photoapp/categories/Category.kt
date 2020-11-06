package hu.bme.photoapp.categories

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Category(
    val name: String,
    val _id: String
)