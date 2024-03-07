package com.softwareit.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val name: String,
    val surname: String,
    val studentId: Int,
    val faculty: String,
)