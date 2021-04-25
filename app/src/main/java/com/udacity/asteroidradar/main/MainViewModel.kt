package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.NasaApiServices
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfTheDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    init {
        getPictureOfTheDay()
        getAsteroids()
    }

    private fun getPictureOfTheDay() {
        NasaApi.retrofitServices.getTodayImage().enqueue(object : retrofit2.Callback<PictureOfDay>{
            override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
                _pictureOfTheDay.value = response.body()
            }
            override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
                _response.value = t.message
            }

        })
    }

    private fun getAsteroids(){
        NasaApi.retrofitServices.getAsteroids().enqueue(object : retrofit2.Callback<List<Asteroid>>{
            override fun onResponse(
                call: Call<List<Asteroid>>,
                response: Response<List<Asteroid>>
            ) {
                _response.value = "There are : ${response.body()?.size} asteroids"
            }
            override fun onFailure(call: Call<List<Asteroid>>, t: Throwable) {
                _response.value = t.message
            }

        })
    }
}