package com.nerazim.network.models

import com.google.gson.annotations.SerializedName

//класс аэропорта для API
data class ResponseAirport(
    @SerializedName("GMT") val gmt: String, //разница во времени с Гринвичем
    @SerializedName("airportId") val id: Int, //внутренний id для API
    @SerializedName("codeIataAirport") val iata: String, //код iata
    @SerializedName("codeIataCity") val cityIata: String?, //код iata города
    @SerializedName("codeIcaoAirport") val icao: String?, //код icao
    @SerializedName("codeIso2Country") val countryIso: String, //iso2 код страны
    @SerializedName("geonameId") val geonameId: String, //id в geonames
    @SerializedName("latitudeAirport") val latitude: Float, //ширина
    @SerializedName("longitudeAirport") val longitude: Float, //долгота
    @SerializedName("nameAirport") val name: String, //название
    @SerializedName("nameCountry") val countryName: String?, //название страны
    @SerializedName("phone") val phoneNumber: String?, //номер телефона
    @SerializedName("timezone") val timezone: String? //часовой пояс
)
