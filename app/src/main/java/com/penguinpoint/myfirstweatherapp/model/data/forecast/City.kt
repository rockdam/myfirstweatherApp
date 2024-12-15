package com.penguinpoint.myfirstweatherapp.model.data.forecast

import com.penguinpoint.myfirstweatherapp.model.data.forecast.Coord

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)