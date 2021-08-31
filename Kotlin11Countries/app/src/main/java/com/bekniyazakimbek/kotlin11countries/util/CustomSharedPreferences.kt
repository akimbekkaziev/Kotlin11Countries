package com.bekniyazakimbek.kotlin11countries.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class CustomSharedPreferences {

    companion object{
        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: CustomSharedPreferences? = null
        private var lock = Any()

        @InternalCoroutinesApi
        operator fun invoke(context: Context): CustomSharedPreferences = instance?: synchronized(lock){
            instance?: makeCustomSharedPreferences(context)
        }

        private fun makeCustomSharedPreferences(context: Context): CustomSharedPreferences{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }

    fun saveTime(time: Long){
        sharedPreferences?.edit(commit = true){
            putLong("time",time)
        }
    }
    fun getTime() = sharedPreferences?.getLong("time",0)

}