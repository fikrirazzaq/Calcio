package com.juvetic.calcio.api

import com.juvetic.calcio.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Api {

    private fun setRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun <T> createService(service: Class<T>): T {
        return setRetrofit().create(service)
    }
}