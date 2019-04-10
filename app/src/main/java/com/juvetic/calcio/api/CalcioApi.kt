package com.juvetic.calcio.api

import com.juvetic.calcio.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

open class CalcioApi {

    companion object {
        private lateinit var retrofit: Retrofit

        fun getClient(): Retrofit {

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            return retrofit
        }
    }

}