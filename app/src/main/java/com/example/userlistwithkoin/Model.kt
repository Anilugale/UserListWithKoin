package com.example.userlistwithkoin

import com.example.userlistwithkoin.model.Data


interface UserRepository {
    suspend fun getUser():ArrayList<Data>
}




class UserRepositoryImpl(val api: UserApi) : UserRepository {

    override suspend fun getUser(): ArrayList<Data> {
        return api.getUserList().data as ArrayList<Data>
    }
}