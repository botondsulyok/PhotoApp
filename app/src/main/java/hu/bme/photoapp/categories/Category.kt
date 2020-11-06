package hu.bme.photoapp.categories

import com.squareup.moshi.JsonClass
import hu.bme.photoapp.home.Image

@JsonClass(generateAdapter = true)
class Category(
    val visibility: Boolean,
    /*val photoList: MutableList<Image>,*/
    val limit: Int,
    val _id: String,
    val name: String/*,
    val creator: String*/
)