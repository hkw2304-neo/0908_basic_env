package com.hkw.a0908_test.data.repo

import com.hkw.a0908_test.data.service.TestService
import javax.inject.Inject

class TestRepo
    @Inject constructor(
        private val service : TestService
    ) {
    suspend fun test() = service.Test()
    }