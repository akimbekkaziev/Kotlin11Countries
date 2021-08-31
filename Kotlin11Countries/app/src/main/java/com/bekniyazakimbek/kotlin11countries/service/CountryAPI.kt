package com.bekniyazakimbek.kotlin11countries.service

import com.bekniyazakimbek.kotlin11countries.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    //  https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries(): Single<List<Country>>

}