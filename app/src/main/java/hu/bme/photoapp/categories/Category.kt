package hu.bme.photoapp.categories

import com.squareup.moshi.JsonClass
import hu.bme.photoapp.home.Image

@JsonClass(generateAdapter = true)
class Category(
    val visibility: Boolean = false,
    val photoList: List<Image> = listOf(),
    val limit: Int = 0,
    val _id: String = "",
    val name: String = ""
)