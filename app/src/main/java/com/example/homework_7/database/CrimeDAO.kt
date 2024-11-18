package com.example.homework_7.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.homework_7.Crime
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface CrimeDao {
    @Query("SELECT * FROM crime")
    //suspend fun getCrimes(): List<Crime>
    fun getCrimes(): Flow<List<Crime>>

    @Query("SELECT * FROM crime WHERE id=(:id)")
    suspend fun getCrime(id: UUID): Crime

    @Update
    suspend fun updateCrime(crime: Crime)
}