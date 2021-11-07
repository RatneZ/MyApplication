package com.ratnez.myapplication.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ratnez.myapplication.common.ACTION_NONE

data class ApiResponse(val results: List<Person>)

@Entity
data class Person(val cell: String,
                  @Embedded val dob: Dob,
                  @PrimaryKey val email: String,
                  val gender: String,
                  @Embedded val location: Location,
                  @Embedded val name: Name,
                  val nat: String,
                  val phone: String,
                  @Embedded val picture: Picture,
                  var userAction: Int = ACTION_NONE)

@Entity
data class Dob(val age: Int, val date: String)

@Entity
data class Location(val city: String, val country: String, val postcode: String, val state: String)

@Entity
data class Name(val first: String, val last: String, val title: String)

@Entity
data class Picture(val large: String, val medium: String, val thumbnail: String)
