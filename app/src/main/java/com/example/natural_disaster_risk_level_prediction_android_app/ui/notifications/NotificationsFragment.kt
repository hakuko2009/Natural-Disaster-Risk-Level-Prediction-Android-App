package com.example.natural_disaster_risk_level_prediction_android_app.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.natural_disaster_risk_level_prediction_android_app.R
import com.example.natural_disaster_risk_level_prediction_android_app.configuration.Status
import com.example.natural_disaster_risk_level_prediction_android_app.databinding.FragmentNotificationsBinding
import com.example.natural_disaster_risk_level_prediction_android_app.model.CurrentWeather
import com.example.natural_disaster_risk_level_prediction_android_app.model.LevelClassifier
import com.example.natural_disaster_risk_level_prediction_android_app.model.WeatherAttribute
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
        val weather = loadCurrentWeather()
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            weather.observe(viewLifecycleOwner) {
                if (weather.value != null) {
                    weather.removeObservers(viewLifecycleOwner)
                    textView.text =
                        getString(
                            R.string.current_weather,
                            weather.value!!.temp.minus(273f).toInt(),
                            weather.value!!.windSpeed.toInt(),
                            weather.value!!.weatherDesc
                        )
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadCurrentWeather(): MutableLiveData<WeatherAttribute> {
        val riskDetectInput = MutableLiveData<WeatherAttribute>()
        weatherViewModel.getCurrentWeather()
            .observe(viewLifecycleOwner) {
                if (it.data != null) {
                    weatherViewModel.getCurrentWeather().removeObservers(viewLifecycleOwner)
                    binding.status = it.status

                    when (it.status) {
                        Status.LOADING_DB_FULL,
                        Status.SUCCESS -> {

                            val data = it.data
                            riskDetectInput.value = convertCurrentWeatherData(data)
                            riskLevelViewModel.getRiskLevel(riskDetectInput.value!!)
                                .observe(viewLifecycleOwner) { result ->
                                    if (result != null) {
                                        riskLevelViewModel.getRiskLevel(riskDetectInput.value!!)
                                            .removeObservers(viewLifecycleOwner)
                                        val message = when (result.riskLevel - 2) {
                                            LevelClassifier.LEVEL_1.value -> {
                                                getString(R.string.level_1_alert)
                                            }

                                            LevelClassifier.LEVEL_2.value -> {
                                                getString(R.string.level_2_alert)
                                            }

                                            LevelClassifier.LEVEL_3.value -> {
                                                getString(R.string.level_3_alert)
                                            }

                                            else -> {
                                                getString(R.string.level_0_alert)
                                            }
                                        }
                                        val builder = AlertDialog.Builder(requireContext())
                                        //set title for alert dialog
                                        builder.setTitle(R.string.title_notifications)
                                        //set message for alert dialog
                                        builder.setMessage(message)
                                        builder.setIcon(android.R.drawable.ic_dialog_alert)

                                        builder.setPositiveButton(getString(R.string.dialog_button_text)) { dialog, _ ->
                                            dialog.dismiss()
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.show()
                                    }
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
        Log.d("get result 2", riskDetectInput.value.toString())
        return riskDetectInput
    }

    private fun convertCurrentWeatherData(currentWeather: CurrentWeather): WeatherAttribute {
        return WeatherAttribute(
            // convert Celsius Degree to Kelvin Degree
            temp = currentWeather.main.temp + 273,
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
