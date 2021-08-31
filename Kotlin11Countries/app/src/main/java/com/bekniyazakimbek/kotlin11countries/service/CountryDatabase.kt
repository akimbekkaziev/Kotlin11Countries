package com.bekniyazakimbek.kotlin11countries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bekniyazakimbek.kotlin11countries.model.Country

@Database(entities = arrayOf(Country::class),version = 1)
abstract class CountryDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object{
        @Volatile private var instance: CountryDatabase? = null
        private var lock = Any()

        operator fun invoke(context: Context): CountryDatabase = instance?: synchronized(lock){
            instance?: makeCountryDatabase(context).also {
                instance = it
            }
        }

        private fun makeCountryDatabase(context: Context) = Room.databaseBuilder(
            context,CountryDatabase::class.java,"countryDatabase"
        ).build()
    }

}