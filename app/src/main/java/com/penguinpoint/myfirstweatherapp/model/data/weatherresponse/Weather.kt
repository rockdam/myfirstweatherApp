package com.penguinpoint.myfirstweatherapp.model.data.weatherresponse

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)