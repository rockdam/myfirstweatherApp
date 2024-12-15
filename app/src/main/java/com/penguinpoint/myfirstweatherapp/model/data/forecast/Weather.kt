package com.penguinpoint.myfirstweatherapp.model.data.forecast

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)