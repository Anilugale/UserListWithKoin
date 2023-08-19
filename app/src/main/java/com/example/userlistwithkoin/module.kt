package com.example.userlistwithkoin

import com.example.userlistwithkoin.viewmodel.UserViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    viewModel { UserViewModel(get()) }
    factory { provideUserApi(get()) }
    factory { provideOkHttpClient() }
    single { provideRetrofit(get()) }

}


fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://reqres.in/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}

fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)
