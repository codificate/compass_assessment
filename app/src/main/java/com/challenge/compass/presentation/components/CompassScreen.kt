package com.challenge.compass.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.challenge.compass.R
import com.challenge.compass.presentation.UIState
import com.challenge.compass.presentation.viewmodel.CompassViewModel

@Composable
fun CompassScreen(viewModel: CompassViewModel = viewModel()) {
    val aboutCompassState by viewModel.compassState.collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)
    CompassScreenStateless(aboutCompassState, viewModel::fetchData)
}

@Composable
fun CompassScreenStateless(aboutCompassState: UIState, fetchDataClick: () -> Unit) {
    Scaffold(
        content = { paddingValues ->
            val modifierWithPadding = Modifier.padding(paddingValues)
            when (aboutCompassState) {
                UIState.Error -> Text(stringResource(id = R.string.something_went_wrong))
                UIState.Loading -> CircularProgressIndicator()
                UIState.Init, is UIState.Success -> {
                    Column(
                        modifier = modifierWithPadding.fillMaxSize()
                    ) {
                        CompassContent(
                            words = if (aboutCompassState is UIState.Success) aboutCompassState.wordCounter else "",
                            characters = if (aboutCompassState is UIState.Success) aboutCompassState.everyXCh else ""
                        )
                    }
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(8.dp)
            ) {
                Button(
                    onClick = { fetchDataClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.White)
                ) {
                    Text(
                        text = "Button",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}

@Composable
private fun CompassContent(words: String, characters: String) {
    Column(
        modifier = Modifier.padding(
            top = 8.dp,
            start = 16.dp,
            end = 16.dp
        )
    ) {
        LabelWithText(
            label = "Characters",
            text = characters,
            isScrollable = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabelWithText(
            label = "Number words",
            text = words
        )
    }
}

@Composable
fun LabelWithText(label: String, text: String, isScrollable: Boolean = false) {
    Column {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            if (isScrollable) {
                Text(
                    text = text, modifier = Modifier
                        .height(300.dp)
                        .verticalScroll(rememberScrollState())
                )
            } else {
                Text(text = text)
            }
        }
    }
}
