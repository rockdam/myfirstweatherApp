package com.penguinpoint.myfirstweatherapp.model.service

import com.penguinpoint.myfirstweatherapp.model.data.weatherresponse.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

// WeatherService.kt - API 통신을 위한 인터페이스
interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
//        @Query("units") units: String = "metric"
    ): WeatherResponse




}