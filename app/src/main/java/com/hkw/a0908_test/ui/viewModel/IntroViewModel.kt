package com.hkw.a0908_test.ui.viewModel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkw.a0908_test.data.repo.TestRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import javax.inject.Inject

data class IntroState(
    val test: String = ""
)


@HiltViewModel
class IntroViewModel @Inject constructor(
    private val repo: TestRepo
) : ViewModel(){

    private val _introState = MutableStateFlow(IntroState());
    val introState = _introState.asStateFlow()

    init {
        viewModelScope.launch{
           val testInit = async {
                repo.test()
            }
            testInit.await()
    //        awaitAll(testInit)

        }
    }

    suspend fun testFunc(){
            delay(1000)
    }

}