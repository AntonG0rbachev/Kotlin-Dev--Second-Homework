package com.example.secondhomework

import android.annotation.SuppressLint
import androidx.annotation.StringRes

@SuppressLint("SupportAnnotationUsage")
data class Student(
    @StringRes
    val studentSurname: String,
    val studentName: String,
    val studentPatronymic: String,
    val studentAge: String,
    val studentCourse: Int,
    val studentGroup: String,
    val studentSubGroup: Int,
    val studentExam: Int
)
