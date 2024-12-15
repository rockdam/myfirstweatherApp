package com.penguinpoint.myfirstweatherapp.model.data.weatherresponse

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)