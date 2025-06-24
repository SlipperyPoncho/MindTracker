package com.artem.android.mindtracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.artem.android.mindtracker.presentation.navigation.NavigationBarComposable
import com.artem.android.mindtracker.presentation.ui.theme.MindTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindTrackerTheme {
                NavigationBarComposable()
            }
        }
    }

    @Preview
    @Composable
    fun ComposablePreview() {
        NavigationBarComposable()
    }
}
