package com.ms.salawat.screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AliadieiaScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("جميع الاذكار")
                }
            )
        }
    ) { paddingValues ->
        val context = LocalContext.current
        val allAzkar = remember { loadAzkarAll(context) }
        val categories = remember(allAzkar) { allAzkar.map { it.category }.distinct() }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // مهم
                .background(Color(0xffEDFBFF))
        ) {
            LazyColumn {
                items(categories) { category ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = category,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            }
        }
    }
}