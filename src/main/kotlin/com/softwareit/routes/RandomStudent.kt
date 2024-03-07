package com.softwareit.routes

import com.softwareit.data.model.Student
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

//private const val BASE_URL = "http://192.168.100.7:8080"
//private const val BASE_URL = "http://localhost:8080"

val students = listOf(
    Student("Michael", "Jordan", 200107106, "Physics"),
    Student("LeBron", "James", 200107105, "Mathematics"),
    Student("Stephen", "Curry", 200107102, "Biology"),
    Student("Kevin", "Durant", 200107143, "Chemistry"),
    Student("Kobe", "Bryant", 200107199, "Engineering"),
    Student("Shaquille", "O'Neal", 200107081, "History"),
    Student("Tim", "Duncan", 200107077, "English"),
    Student("Magic", "Johnson", 200107199, "Geology"),
    Student("Larry", "Bird", 200107094, "Economics")
)



