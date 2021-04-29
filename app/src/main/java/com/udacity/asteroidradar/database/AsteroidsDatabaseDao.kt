package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidsDatabaseDao {
    @Insert
    fun insert(asteroid: Asteroid)
    @Update
    fun update(asteroid: Asteroid)
    @Query("SELECT * FROM asteroids_table WHERE id = :key")
    fun get(key: Long): Asteroid
    @Query("DELETE FROM asteroids_table")
    fun clear()
    @Query("SELECT * FROM asteroids_table ORDER BY id DESC")
    fun getAllAsteroids(): LiveData<List<Asteroid>>
    @Query("SELECT * FROM asteroids_table ORDER BY id DESC LIMIT 1")
    fun getAnAsteroid(): Asteroid?
}