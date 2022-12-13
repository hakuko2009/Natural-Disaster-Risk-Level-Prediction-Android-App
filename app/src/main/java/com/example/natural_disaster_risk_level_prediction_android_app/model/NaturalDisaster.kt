package com.example.natural_disaster_risk_level_prediction_android_app.model

data class NaturalDisaster(
    var temp: Double,
    var pressure: Double,
    var humidity: Double,
    var clouds: Double,
    var windSpeed: Double,
    var windDeg: Double,
    var weatherMain: String,
    var weatherDesc: String,
    var rain: Double = 0.0
)

enum class Type(val value: String) {
    NONE(""),
    FLOOD("Flood"),
    DROUGHT("Drought"),
    STORM("Storm"),
    LANDSLIDE("Landslide"),
    EXTREME_TEMPERATURE("Extreme temperature"),
    WILDFIRE("Wildfire"),
    EARTHQUAKE("Earthquake"),
    VOLCANO("Volcanic activity"),
    MASS_MOVEMENT("Mass movement"),
    OUTBURST("Glacial lake outburst"),
    OTHER("")
}

enum class Region(val value: String) {
    NONE(""),
    EASTERN_ASIA("Eastern Asia"),
    SOUTHERN_ASIA("Southern Asia"),
    SOUTH_AMERICA("South America"),
    WESTERN_EUROPE("Western Europe"),
    WESTERN_ASIA("Western Asia"),
    SOUTHERN_EUROPE("Southern Europe"),
    AUS("Australia and New Zealand"),
    MIDDLE_AFRICA("Middle Africa"),
    EASTERN_AFRICA("Eastern Africa"),
    EASTERN_EUROPE("Eastern Europe"),
    CENTRAL_AMERICA("Central America"),
    SOUTHERN_AFRICA("Southern Africa"),
    NORTHERN_AMERICA("Northern America"),
    SE_ASIA("South-Eastern Asia"),
    NORTHERN_EUROPE("Northern Europe"),
    CARIBBEAN("Caribbean"),
    NORTHERN_AFRICA("Northern Africa"),
    MELANESIA("Melanesia"),
    WESTERN_AFRICA("Western Africa"),
    CENTRAL_ASIA("Central Asia"),
    POLYNESIA("Polynesia"),
    MICRONESIA("Micronesia")
}

enum class MagScale(val value: String) {
    NONE(""),
    Km2("Km2"),
    Kph("Kph"),
    CELSIUS_DEGREE("°C"),
    RICHTER("Richter")
}
