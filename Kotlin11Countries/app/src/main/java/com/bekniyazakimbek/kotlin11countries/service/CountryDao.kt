package com.bekniyazakimbek.kotlin11countries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bekniyazakimbek.kotlin11countries.model.Country

@Dao
interface CountryDao {

    @Query("SELECT * FROM Country")
    suspend fun getAllCountries(): List<Country>

    @Query("SELECT * FROM Country WHERE uuid = :uuid")
    suspend fun getCountry(uuid: Int): Country

    @Query("DELETE FROM Country")
    suspend fun deleteAllCountries()

    @Insert
    suspend fun insertAllCountries(vararg countries: Country): List<Long>
}