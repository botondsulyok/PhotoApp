package hu.bme.photoapp.home

import com.squareup.moshi.JsonClass
import hu.bme.photoapp.model.User

@JsonClass(generateAdapter = true)
class Image(
    val _id: String = "",
    val title: String = "",
    val likes: Int = 0,
    val ownImage: String = "",
    val owner : String = "",
    val ownerID: User = User(),
    val description: String =""

)