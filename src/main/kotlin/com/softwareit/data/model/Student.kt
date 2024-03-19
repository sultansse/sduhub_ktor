package com.softwareit.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val fullname: String,
    val studentId: Int,
    val faculty: String,
)