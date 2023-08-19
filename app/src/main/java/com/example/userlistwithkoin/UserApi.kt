package com.example.userlistwithkoin

import com.example.userlistwithkoin.model.UserResponse
import retrofit2.http.GET

interface UserApi {
    @GET("api/users?page=1")
   suspend fun getUserList():UserResponse
}