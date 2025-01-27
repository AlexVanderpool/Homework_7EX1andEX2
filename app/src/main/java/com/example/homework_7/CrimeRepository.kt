package com.example.homework_7

import android.content.Context
import androidx.room.Room
import com.example.homework_7.database.CrimeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(
    context:Context,
    private val coroutineScope : CoroutineScope = GlobalScope
) {

    private val database: CrimeDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            CrimeDatabase::class.java,
            DATABASE_NAME
        )
        .createFromAsset(DATABASE_NAME)
        .build()

    suspend fun getCrimes(): Flow<List<Crime>> = database.crimeDAO().getCrimes()

    suspend fun getCrime(id: UUID): Crime = database.crimeDAO().getCrime(id)

    fun updateCrime(crime:Crime){
        coroutineScope.launch {

            database.crimeDAO().updateCrime(crime)
        }

    }


    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE
                ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}