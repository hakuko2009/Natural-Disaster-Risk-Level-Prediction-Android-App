package com.example.natural_disaster_risk_level_prediction_android_app.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.natural_disaster_risk_level_prediction_android_app.R
import com.example.natural_disaster_risk_level_prediction_android_app.configuration.Status
import com.example.natural_disaster_risk_level_prediction_android_app.databinding.FragmentNotificationsBinding
import com.example.natural_disaster_risk_level_prediction_android_app.model.CurrentWeather
import com.example.natural_disaster_risk_level_prediction_android_app.model.NaturalDisaster
import com.example.natural_disaster_risk_level_prediction_android_app.viewmodel.RiskLevelViewModel
import com.example.natural_disaster_risk_level_prediction_android_app.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val weatherViewModel by viewModels<WeatherViewModel>()
    private val riskLevelViewModel by viewModels<RiskLevelViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]
//        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
//        riskLevelViewModel = ViewModelProvider(this)[RiskLevelViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCurrentWeather()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadCurrentWeather() {
        weatherViewModel.getCurrentWeather()
            .observe(viewLifecycleOwner) {

                binding.status = it.status

                when (it.status) {
                    Status.LOADING_DB_FULL,
                    Status.SUCCESS -> {

                        it.data?.let { data ->
                            val riskDetectInput = convertCurrentWeatherData(data)
                            val result = riskLevelViewModel.getRiskLevel(riskDetectInput)
                            print("RESULT!!!!!: $result ")
                        }
                    }

                    Status.ERROR -> {
                        Toast.makeText(
                            context,
                            getString(R.string.network_error),
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    else -> {}
                }
            }
    }

    private fun convertCurrentWeatherData(currentWeather: CurrentWeather): NaturalDisaster {
        return NaturalDisaster(
            temp = currentWeather.main.temp,
            humidity = currentWeather.main.humidity,
            pressure = currentWeather.main.pressure,
            clouds = currentWeather.clouds.all,
            windSpeed = currentWeather.wind.speed,
            windDeg = currentWeather.wind.deg,
            weatherMain = currentWeather.weather[0].main,
            weatherDesc = currentWeather.weather[0].description

        )


    }
}
