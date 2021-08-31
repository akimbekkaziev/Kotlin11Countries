package com.bekniyazakimbek.kotlin11countries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.bekniyazakimbek.kotlin11countries.model.Country
import com.bekniyazakimbek.kotlin11countries.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application): BaseViewModel(application) {

    var countryLiveData = MutableLiveData<Country>()

    fun refreshData(uuid: Int){
        launch {
            var country = CountryDatabase(getApplication()).countryDao().getCountry(uuid)
            countryLiveData.value = country
        }
    }

}