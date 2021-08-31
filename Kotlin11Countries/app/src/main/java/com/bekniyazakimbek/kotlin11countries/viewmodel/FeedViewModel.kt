package com.bekniyazakimbek.kotlin11countries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.bekniyazakimbek.kotlin11countries.model.Country
import com.bekniyazakimbek.kotlin11countries.service.CountryAPIservice
import com.bekniyazakimbek.kotlin11countries.service.CountryDatabase
import com.bekniyazakimbek.kotlin11countries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

class FeedViewModel(application: Application): BaseViewModel(application) {

    private val countryAPIservice = CountryAPIservice()
    private val disposable = CompositeDisposable()
    @InternalCoroutinesApi
    private val customSharedPreferences = CustomSharedPreferences(getApplication())
    private val refreshTime = 0.1*60*1000*1000*1000L


    var countries = MutableLiveData<List<Country>>()
    var countryLoading = MutableLiveData<Boolean>()
    var countryError = MutableLiveData<Boolean>()

    @InternalCoroutinesApi
    fun refreshData(){
        var update = customSharedPreferences.getTime()
        if(update != null && update != 0L && System.nanoTime() - update < refreshTime){
            getDataFromSQLite()
            Toast.makeText(getApplication(),"From SQLite",Toast.LENGTH_LONG).show()
        }else{
            getDataFromAPI()
            Toast.makeText(getApplication(),"From API",Toast.LENGTH_LONG).show()
        }
    }
    @InternalCoroutinesApi
    fun refreshAPI(){
        getDataFromAPI()
    }

    @InternalCoroutinesApi
    private fun getDataFromAPI(){
        countryLoading.value = true
        disposable.add(
            countryAPIservice.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }
                })
        )

    }

    private fun getDataFromSQLite(){
        countryLoading.value = true
        launch {
            var countryList = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countryList)
        }
    }

    private fun showCountries(countryList: List<Country>){
        countries.value = countryList
        countryLoading.value = false
        countryError.value = false
    }

    @InternalCoroutinesApi
    private fun storeInSQLite(countryList: List<Country>){
        countryLoading.value = true
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            var listLong = dao.insertAllCountries(*countryList.toTypedArray())
            var i = 0
            while (i<countryList.size){
                countryList[i].uuid = listLong[i].toInt()
                i++
            }
            showCountries(countryList)
            customSharedPreferences.saveTime(System.nanoTime())
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}