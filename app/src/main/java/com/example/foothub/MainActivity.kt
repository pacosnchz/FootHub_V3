package com.example.foothub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foothub.ui.navigation.AppNavigation
import com.example.foothub.ui.theme.FootHubTheme
import com.example.foothub.viewmodel.ProfileViewModel

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val profileViewModel: ProfileViewModel = viewModel()
            val prefs by profileViewModel.preferences.collectAsState()
            val windowSizeClass = calculateWindowSizeClass(this)

            FootHubTheme(appTheme = prefs.theme) {
                AppNavigation(windowSizeClass = windowSizeClass)
            }
        }
    }
}