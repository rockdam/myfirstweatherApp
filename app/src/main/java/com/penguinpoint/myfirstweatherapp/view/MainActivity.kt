package com.penguinpoint.myfirstweatherapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.penguinpoint.myfirstweatherapp.R
import com.penguinpoint.myfirstweatherapp.databinding.ActivityMainBinding
import com.penguinpoint.myfirstweatherapp.view.adapter.WeatherAdapter
import com.penguinpoint.myfirstweatherapp.viewmodel.WeatherViewModel

// "앱 요약"
//- 앱을 실행하면 서울의 현재 날씨 정보를 가져옴
//- 새로고침 버튼을 통해 날씨 정보를 업데이트할 수 있음
//- 새로고침할 때마다 히스토리가 쌓임 (최신순으로 표시)
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViews()
//        뷰 바인딩과 ViewModel 초기화
        setupRecyclerView()

        // RecyclerView 어댑터 및 레이아웃 매니저 설정
        observeViewModelData()
        //현재 날씨 데이터 관찰 및 UI 업데이트
        setupRefreshButton()
        //새로고침 버튼 클릭 리스너 설정
    }

    // 뷰 초기화 관련
    private fun initializeViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    // RecyclerView 설정
    private fun setupRecyclerView() {
        adapter = WeatherAdapter()
        binding.recyclerView.apply {
            this.adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    // ViewModel 데이터 관찰
    private fun observeViewModelData() {
        observeCurrentWeather()
        observeWeatherHistory()
    }

    // 현재 날씨 데이터 관찰
    private fun observeCurrentWeather() {
        viewModel.currentWeather.observe(this) { weatherResponse ->
            binding.apply {
                textCurrentTemperature.text =
                    getString(R.string.temperature_format, weatherResponse.main.temp)
                textCity.text = weatherResponse.name
                textDescription.text = weatherResponse.weather.firstOrNull()?.description
            }
        }
    }
    // 화씨를 섭씨로 변환하는 함수
    private fun fahrenheitToCelsius(fahrenheit: Double): Double {
        return ((fahrenheit - 32) * 5 / 9).also {
            Log.d("Temperature", "Converting ${fahrenheit}°F to ${String.format("%.1f", it)}°C")
        }
    }
    // 날씨 히스토리 데이터 관찰
    private fun observeWeatherHistory() {
        viewModel.weatherHistory.observe(this) { historyList ->
            if (historyList.isNotEmpty()) {
                Log.d("MainActivity", "Received history update, size: ${historyList.size}")
                adapter.addHistoryItem(historyList.first())
                binding.recyclerView.smoothScrollToPosition(0)
            }
        }
    }

    // 새로고침 버튼 설정
    private fun setupRefreshButton() {
        binding.buttonRefresh.setOnClickListener {
            Log.d("MainActivity", "Refresh button clicked")
            viewModel.fetchWeather("Seoul")
        }
        Log.d("MainActivity", "Setting up button click listener")
    }

    override fun onStart() {
        super.onStart()
        fetchInitialWeatherData()
    }

    // 초기 날씨 데이터 가져오기
    private fun fetchInitialWeatherData() {
        viewModel.fetchWeather("Seoul")
    }

    override fun onPause() {
        super.onPause()
        // 필요한 정리 작업 수행
    }
}