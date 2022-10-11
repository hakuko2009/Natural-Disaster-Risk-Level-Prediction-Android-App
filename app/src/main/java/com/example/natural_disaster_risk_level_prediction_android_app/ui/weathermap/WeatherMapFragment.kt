package com.example.natural_disaster_risk_level_prediction_android_app.ui.weathermap

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.view.Callout
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.example.natural_disaster_risk_level_prediction_android_app.R
import com.example.natural_disaster_risk_level_prediction_android_app.databinding.FragmentWeatherMapBinding
import com.google.android.gms.maps.MapView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WeatherMapFragment : Fragment() {

    // view bindings
    private val _binding by lazy {
        FragmentWeatherMapBinding.inflate(layoutInflater)
    }

    private val mapView: MapView by lazy {
        _binding.mapView
    }

    private val locationButton: FloatingActionButton by lazy {
        _binding.locationFab
    }

    // mapping
    private lateinit var map: ArcGISMap
    private lateinit var viewModel: WeatherMapViewModel
    private lateinit var mapOverlay: GraphicsOverlay
    private lateinit var mapCallout: Callout
    // menu items
    private lateinit var placeSearchItem: MenuItem
    // runtime permissions
    private var reqPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[WeatherMapViewModel::class.java]

//        _binding = FragmentWeatherMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mapView: MapView = binding.mapView
        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            mapView.accessibilityTraversalAfter
        }
        return root
    }

    override fun onPause() {
        super.onPause()
//        mapView.pause()
    }

    override fun onResume() {
        super.onResume()
//        mapView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
//        mapView.dispose()
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            locationDisplay.startAsync()
        } else {
            viewModel.displayMessage(getString(R.string.location_settings))
        }
    }

    private fun requestPermissions() {
        val context = requireContext()
        val requestCode = 2
        // fine location permission
        val permissionCheckFineLocation =
            ContextCompat.checkSelfPermission(context, reqPermissions[0]) ==
                    PackageManager.PERMISSION_GRANTED
        // coarse location permission
        val permissionCheckCoarseLocation =
            ContextCompat.checkSelfPermission(context, reqPermissions[1]) ==
                    PackageManager.PERMISSION_GRANTED
        if (!(permissionCheckFineLocation && permissionCheckCoarseLocation)) {
            ActivityCompat.requestPermissions(requireActivity(), reqPermissions, requestCode)
        }
    }

}
