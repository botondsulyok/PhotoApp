package hu.bme.photoapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class User(
    /*val _id: String,*/
    /*val email: String,*/
    //TODO a usert csak token alapján azonosítjuk, pl.: vip vagy nem vagy kell email cím is?
    val token: String)