package com.hkw.a0908_test.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.hkw.a0908_test.ui.viewModel.IntroViewModel

@Composable
fun IntroView(
    modifier: Modifier,
    viewModel: IntroViewModel
) {

    val state by viewModel.introState.collectAsState()

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(
                    Color(
                        0xFFF9FAFB,
                    ),
                ),
    ) {
        Button(
            onClick = { },
            modifier = modifier.semantics {
                contentDescription = "사회서비스 전자바우처 신청하기 테스트중입니다"
            }
        ) {
            Text("신청하기 테스트")
        }
    }


}