package com.ratnez.myapplication.data

import com.ratnez.myapplication.data.local.PersonDao
import com.ratnez.myapplication.model.Person
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ApiRepository @Inject constructor(private val remoteDataSource: RemoteDataSource, private val dao: PersonDao) {

    suspend fun getPersonData(): Flow<List<Person>> {
        MainScope().launch {
            dao.getCount().collectLatest {
                if (it == 0) {
                    val response = remoteDataSource.getData()
                    if (response.isSuccessful) {
                        val personData = response.body()?.results
                        if (!personData.isNullOrEmpty()) {
                            dao.insertAll(personData)
                        }
                    }
                }
            }
        }
        return dao.getAll()
    }

    suspend fun updateUserAction(email: String, action: Int) {
        dao.updatePerson(email, action)
    }
}