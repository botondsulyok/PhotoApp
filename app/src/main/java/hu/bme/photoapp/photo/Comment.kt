package hu.bme.photoapp.photo

import com.squareup.moshi.JsonClass
import hu.bme.photoapp.model.User

@JsonClass(generateAdapter = true)
class Comment(
    val _id: String = "",
    val text: String = "",
    val user: User = User()
)