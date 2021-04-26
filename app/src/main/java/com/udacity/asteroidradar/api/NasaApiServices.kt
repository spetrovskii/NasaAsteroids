package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("https://api.nasa.gov")
    .build()

interface NasaApiServices{
    @GET("/planetary/apod")
    fun getTodayImage(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<PictureOfDay>

    @GET("/neo/rest/v1/feed/")
    fun getAsteroids(
        @Query("start_date") startDate: String = "2015-09-07",
        @Query("end_date") endDate : String = "2015-09-08",
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Call<JSONObject>
}

object NasaApi{
    val retrofitServices : NasaApiServices by lazy {
        retrofit.create(NasaApiServices::class.java)
    }
}