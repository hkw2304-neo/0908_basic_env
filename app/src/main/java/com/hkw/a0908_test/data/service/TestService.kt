package com.hkw.a0908_test.data.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TestService {

    @GET("test")
    suspend fun Test(
        @Query("OC") OC: String = "rhrnak2304",
        @Query("target") target: String = "prec",
        @Query("type") type: String = "JSON",
        @Query("search") search: String = "2",
        @Query("query") query: String ="test",
    ): Response<ResponseBody>
    //성공 여부만 확인
}