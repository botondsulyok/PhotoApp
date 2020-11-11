package hu.bme.photoapp.home

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Image(
    val _id: String,
    val title: String,
    val likes: Int,
    val ownImage: String/*,
    val owner: String*/
    /*val description: String,*/

)