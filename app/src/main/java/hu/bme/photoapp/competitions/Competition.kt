package hu.bme.photoapp.competitions

import com.squareup.moshi.JsonClass
import hu.bme.photoapp.home.Image

@JsonClass(generateAdapter = true)
class Competition(
    val _id: String = "",
    val photoList: List<Image> = listOf(),
    val name: String = "",
    val deadline: String = "",
    val currentVisibility: Boolean = true
)