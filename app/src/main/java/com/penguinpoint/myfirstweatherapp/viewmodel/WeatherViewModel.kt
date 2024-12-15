package com.penguinpoint.myfirstweatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.penguinpoint.myfirstweatherapp.model.WeatherRepository
import com.penguinpoint.myfirstweatherapp.model.data.weatherresponse.WeatherResponse
import com.penguinpoint.myfirstweatherapp.view.adapter.WeatherHistory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// WeatherViewModel.kt - 뷰모델
class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()
    private val _currentWeather = MutableLiveData<WeatherResponse>()
    val currentWeather: LiveData<WeatherResponse> = _currentWeather

    // 히스토리를 위한 LiveData 추가
    private val _weatherHistory = MutableLiveData<List<WeatherHistory>>(emptyList())
    val weatherHistory: LiveData<List<WeatherHistory>> = _weatherHistory

    private fun convertToCelsius(kelvin: Double): Double {
        return kelvin - 273.15  // 켈빈을 섭씨로 변환
    }



    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                Log.d("MainActivity", "Setting up button click listener")


                val response = repository.getWeatherData(city)
                _currentWeather.value = response

                val celsius = convertToCelsius(response.main.temp)

                // 히스토리 아이템 생성 (섭씨 온도 사용)
                val historyItem = WeatherHistory(
                    timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        .format(Date()),
                    temperature = celsius,  // 변환된 섭씨 온도
                    description = response.weather.firstOrNull()?.description ?: "",
                    city = response.name
                )

                // 히스토리 업데이트
                val currentList = _weatherHistory.value.orEmpty().toMutableList()
                currentList.add(0, historyItem)
                _weatherHistory.value = currentList
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }
}