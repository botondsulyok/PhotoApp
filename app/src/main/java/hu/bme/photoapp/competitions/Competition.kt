package hu.bme.photoapp.competitions

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Competition(
    val _id: String,
    //TODO Date, User osztály kezelése
    /*val creator: String,*/
    val name: String/*,
    val deadline: Date*/
)