package com.example.secondhomework

import android.annotation.SuppressLint
import androidx.annotation.StringRes

@SuppressLint("SupportAnnotationUsage")
data class Student(
    @StringRes
    val PersName: String,
    val PersAge: Int,
    val PersHeight: Int,
    val PersWeight: Int
)
