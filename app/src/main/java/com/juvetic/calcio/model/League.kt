package com.juvetic.calcio.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(val id: String, val name: String, val logo: Int, val badge: Int, val desc: String) : Parcelable