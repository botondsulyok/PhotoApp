package hu.bme.photoapp.competitions

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Competition(
    val name: String,
    val date: String,
    val vip: Boolean,
    val _id: String
)