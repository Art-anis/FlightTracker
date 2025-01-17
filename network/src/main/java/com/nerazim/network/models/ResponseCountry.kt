package com.nerazim.network.models

import com.google.gson.annotations.SerializedName

//класс страны для API
data class ResponseCountry(
    @SerializedName("capital") val capital: String, //столица
    @SerializedName("codeCurrency") val currencyCode: String, //обозначение валюты
    @SerializedName("codeFips") val fips: String, //код в FIPS
    @SerializedName("codeIso2Country") val iso2: String, //код Iso2
    @SerializedName("codeIso3Country") val iso3: String, //код Iso3
    @SerializedName("continent") val continent: String, //континент
    @SerializedName("countryId") val id: Int, //id в API
    @SerializedName("nameCountry") val name: String, //название
    @SerializedName("nameCurrency") val currencyName: String, //название валюты
    @SerializedName("numericIso") val numericIso: String, //числовой Iso
    @SerializedName("phonePrefix") val phonePrefix: String, //код номера телефона
    @SerializedName("population") val population: String //численность населения
)