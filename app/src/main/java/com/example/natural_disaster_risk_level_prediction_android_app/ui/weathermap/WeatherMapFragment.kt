package com.example.natural_disaster_risk_level_prediction_android_app.ui.weathermap

import SearchViewModel
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.natural_disaster_risk_level_prediction_android_app.R
import com.example.natural_disaster_risk_level_prediction_android_app.configuration.Status
import com.example.natural_disaster_risk_level_prediction_android_app.databinding.FragmentWeatherMapBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_weather_map.cloudsButton
import kotlinx.android.synthetic.main.fragment_weather_map.pressureButton
import kotlinx.android.synthetic.main.fragment_weather_map.rainButton
import kotlinx.android.synthetic.main.fragment_weather_map.tempButton
import kotlinx.android.synthetic.main.fragment_weather_map.web_view
import kotlinx.android.synthetic.main.fragment_weather_map.windButton

class WeatherMapFragment : Fragment(R.layout.fragment_weather_map) {
    var status = Status.START
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var request: LocationRequest
    private lateinit var builder: LocationSettingsRequest.Builder
    private lateinit var locationCallback: LocationCallback
    private lateinit var binding: FragmentWeatherMapBinding
    private var lat: Double = 16.03456
    private var lon: Double = 108.22903
    private var url =
        "https://openweathermap.org/weathermap?basemap=map&cities=false&layer=temperature&lat=" +
                lat + "&lon=" + lon + "&zoom=6"

    companion object {
        private const val LOCATION_REQUEST_CODE = 2
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWeatherMapBinding.bind(view)
        binding.status = status
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        checkPermission().run {
            Log.d("startGPS - url", url)

            web_view.settings.javaScriptEnabled = true

            CookieManager.getInstance().setAcceptThirdPartyCookies(web_view, true)
            CookieManager.getInstance().setCookie(url, "stick-footer-panel=false")

            web_view.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    status = Status.LOADING
                    binding.status = status
                }

                override fun onLoadResource(view: WebView?, url: String?) {
                    super.onLoadResource(view, url)
                    try {
                        web_view?.loadUrl(
                            "javascript:(function() { " +
                                    "document.getElementsByClassName('stick-footer-panel')[0].style.display='none'; })()"
                        )
                        web_view?.loadUrl(
                            "javascript:(function() { " +
                                    "document.getElementsByClassName('leaflet-control-attribution leaflet-control')[0].style.display='none'; })()"
                        )
                        web_view?.loadUrl(
                            "javascript:(function() { " +
                                    "document.getElementById('header-website').style.display='none';})()"
                        )
                        web_view?.loadUrl(
                            "javascript:(function() { " +
                                    "document.getElementsByClassName('leaflet-top leaflet-right')[0].style.display='none'; })()"
                        )
                        web_view?.loadUrl(
                            "javascript:(function() { " +
                                    "document.getElementsByClassName('scale-details')[0].style.display='none'; })()"
                        )
                        web_view?.loadUrl(
                            "javascript:(function() { " +
                                    "document.getElementsByClassName('leaflet-control-container)[0].style.display='none'; })()"
                        )

                    } catch (e: Exception) {
                        e.printStackTrace()
                        status = Status.ERROR
                        binding.status = status
                    }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    status = Status.SUCCESS
                    binding.status = status
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    status = Status.ERROR
                    binding.status = status
                }
            }

            web_view.loadUrl(url)
            web_view.requestFocus(View.FOCUS_DOWN)

            windButton.setOnClickListener {
                newUrl(url, "windspeed")
            }
            rainButton.setOnClickListener {
                newUrl(url, "precipitation")
            }
            tempButton.setOnClickListener {
                newUrl(url, "temperature")
            }
            cloudsButton.setOnClickListener {
                newUrl(url, "clouds")
            }
            pressureButton.setOnClickListener {
                newUrl(url, "pressure")
            }
        }

    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        if (this::fusedLocationClient.isInitialized && this::locationCallback.isInitialized)
            fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun newUrl(q: String, layer: String) {
        val newUrl = q.split("layer=".toRegex()).toTypedArray()
        val newUrl2 = newUrl[1].split("&".toRegex()).toTypedArray()
        val result = newUrl[0] + "layer=" + layer + "&" + newUrl2[1] + "&" + newUrl2[2] + "&" + newUrl2[3]
        web_view.loadUrl(result)
    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun permissionExplanation() {
        val builder = MaterialAlertDialogBuilder(requireContext(), com.airbnb.lottie.R.style.AlertDialog_AppCompat)
        builder.setTitle(getString(R.string.location_required))
        builder.setMessage(getString(R.string.access_to_gps))
        builder.apply {
            setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
                if (shouldShowRequestPermissionRationale(permissions[0]) ||
                    shouldShowRequestPermissionRationale(
                        permissions[1]
                    )
                )
                    requestPermissions(
                        permissions,
                        LOCATION_REQUEST_CODE
                    )
                else
                    openPermissionSetting()
            }
            setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.create()
        builder.show()
    }

    private fun openPermissionSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", activity?.packageName, null)
        intent.data = uri
        startActivityForResult(intent, 0)
    }

    private fun checkPermission() {
        when {
            hasPermissions(requireContext(), *permissions) -> startGPS()
            shouldShowRequestPermissionRationale(permissions[0]) -> permissionExplanation()
            shouldShowRequestPermissionRationale(permissions[1]) -> permissionExplanation()
            else -> requestPermissions(
                permissions,
                LOCATION_REQUEST_CODE
            )
        }
    }

    private fun startGPS() {
        binding.status = Status.LOADING
        Log.d("startSPS: ", binding.status.toString())

        request = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        builder = LocationSettingsRequest.Builder().addLocationRequest(request)

        val result: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(requireContext())
                .checkLocationSettings(builder.build())

        result.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    val resolvable = it
                    resolvable.startResolutionForResult(requireActivity(), 8990)
                } catch (ex: IntentSender.SendIntentException) {
                    ex.printStackTrace()
                }
            }
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                if (p0.locations.isNotEmpty()) {
                    p0.lastLocation?.let { location ->
                        lat = location.latitude
                        Log.d("startGPS - lat", lat.toString())
                        lon = location.longitude
                        Log.d("startGPS - lon", lon.toString())
                        url = "https://openweathermap.org/weathermap?basemap=map&cities=false&layer=temperature&lat=" +
                                lat + "&lon=" + lon + "&zoom=6"
                    }
                    return
                }
            }
        }
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        if (this::fusedLocationClient.isInitialized && this::request.isInitialized && this::locationCallback.isInitialized)
            fusedLocationClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )
    }
}
