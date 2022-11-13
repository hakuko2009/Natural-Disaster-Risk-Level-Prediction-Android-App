package com.example.natural_disaster_risk_level_prediction_android_app.adapter

import android.view.View
import android.webkit.WebView
import androidx.databinding.BindingAdapter
import com.example.natural_disaster_risk_level_prediction_android_app.configuration.Status

@BindingAdapter("loadingVisibility")
fun loadingVisibility(view: View, status: Status) {
    if (status == Status.LOADING)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

@BindingAdapter("mapVisibility")
fun mapVisibility(view: WebView, status: Status) {
    when (status) {
        Status.SUCCESS -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}
