package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

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

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid?>()
    val navigateToSelectedAsteroid: MutableLiveData<Asteroid?>
        get() = _navigateToSelectedAsteroid

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
        NasaApi.retrofitServices.getAsteroids().enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                _asteroids.value = response.body()?.let { parseAsteroidsJsonResult(JSONObject(it)) }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = t.message
            }
        })
    }

    fun displayAsteroid(asteroid: Asteroid){
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidCompleted(){
        _navigateToSelectedAsteroid.value = null
    }
}