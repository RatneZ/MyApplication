package com.ratnez.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ratnez.myapplication.model.Person

@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonDB : RoomDatabase() {
    abstract fun personDao(): PersonDao
}