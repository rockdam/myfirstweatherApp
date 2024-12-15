package com.penguinpoint.myfirstweatherapp.view.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.penguinpoint.myfirstweatherapp.databinding.ItemWeatherBinding
import com.penguinpoint.myfirstweatherapp.model.data.weatherresponse.Weather
// 히스토리 아이템을 위한 데이터 클래스
data class WeatherHistory(
    val timestamp: String,  // 새로고침 시간
    val temperature: Double,
    val description: String,
    val city: String
)

// WeatherAdapter.kt - RecyclerView 어댑터
class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    private val historyItems = mutableListOf<WeatherHistory>()

    // ViewHolder 클래스
    class WeatherViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherHistory) {
            binding.apply {
                textTimestamp.text = item.timestamp
                textTemperature.text = String.format("%.1f°C", item.temperature)
                textDescription.text = item.description
                textCity.text = item.city
            }
        }
    }
    // 새 날씨 정보를 히스토리에 추가
    fun addHistoryItem(item: WeatherHistory) {
        Log.d("WeatherAdapter", "Before adding - List: $historyItems")

        historyItems.add(0, item)
        Log.d("WeatherAdapter", "Item added, total items: ${historyItems.size}")
        notifyItemInserted(0)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        Log.d("WeatherAdapter", "Binding item at position $position: ${historyItems[position]}")

        holder.bind(historyItems[position])
    }

    override fun getItemCount() = historyItems.size

}
