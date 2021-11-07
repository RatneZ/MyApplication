package com.ratnez.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ratnez.myapplication.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("Select * from Person")
    fun getAll(): Flow<List<Person>>

    @Query("Select count(*) from Person")
    fun getCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(persons: List<Person>)

    @Query("Update person set userAction = :action WHERE email = :email")
    suspend fun updatePerson(email: String, action: Int)
}