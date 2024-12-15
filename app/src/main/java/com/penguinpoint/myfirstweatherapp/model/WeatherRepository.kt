package com.penguinpoint.myfirstweatherapp.model

import com.penguinpoint.myfirstweatherapp.model.data.weatherresponse.WeatherResponse
import com.penguinpoint.myfirstweatherapp.model.service.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// WeatherRepository.kt - 데이터 처리 계층
class WeatherRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherService = retrofit.create(WeatherService::class.java)

    // API 호출 함수
    suspend fun getWeatherData(city: String): WeatherResponse {
        return weatherService.getWeather(
            cityName = city,
            apiKey = "165374a7dcb06e44a8498f50154b2f5e" // OpenWeather API 키를 넣으세요
        )
    }
}