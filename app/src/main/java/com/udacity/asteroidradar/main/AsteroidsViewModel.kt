package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.udacity.asteroidradar.database.AsteroidsDatabaseDao

class AsteroidsViewModel(
    val database: AsteroidsDatabaseDao,
    application: Application) : AndroidViewModel(application) {
}