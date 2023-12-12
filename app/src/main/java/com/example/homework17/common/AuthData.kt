package com.example.homework17.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthData(val email: String, val password: String) : Parcelable