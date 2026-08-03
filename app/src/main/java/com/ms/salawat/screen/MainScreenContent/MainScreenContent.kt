package com.ms.salawat.screen.MainScreenContent

import PlayerTimes2
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ms.salawat.R

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    navController: NavHostController,
    modifier: Modifier = Modifier // 👈 استقبال الـ modifier
) {
    Column(
        //  دمج الـ modifier القادم من HomeScreen مع السكرول
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        PlayerTimes2()
        Card (
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ){
            Row(
                modifier = Modifier
                    .background(color = Color(0xFF80C0DC))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.40f)
                        .fillMaxHeight()
                        .padding(start = 16.dp, top = 12.dp, bottom = 12.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "الظهر ", style = TextStyle(fontSize = 25.sp))
                    Text(text = "11:45", style = TextStyle(fontSize = 25.sp))
                    Text(text = "الصلاة التالية ", style = TextStyle(fontSize = 25.sp))
                    Text(text = "2:50 م", style = TextStyle(fontSize = 25.sp))
                }
                Image(
                    painter = painterResource(R.drawable.image1),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(0.60f)
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .height(41.dp)
                .fillMaxWidth()
                .background(color = Color(0xFFAAD9EE)),
            contentAlignment = Alignment.Center
        ) { Text(text = "جميع العبادت ", style = TextStyle(fontSize = 30.sp)) }

        Spacer(Modifier.height(10.dp))
        Card() {
        Column(
            modifier = Modifier
                .background(color = Color(0xFFAAD9EE))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Top
            ) {
                MyImage(
                    imageResourceId = R.drawable.vector1,
                    text = "اذكار الصلاة ",
                    onClick = { navController.navigate("ScreenPrayer") })
                MyImage(
                    imageResourceId = R.drawable.vector2,
                    text = "اذكار الصباح",
                    onClick = { navController.navigate("ScreenAlSabah") })
                MyImage(
                    imageResourceId = R.drawable.vector3,
                    text = "اذكار المساء",
                    onClick = { navController.navigate("ScreenAlMassa") })
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Top
            ) {
                MyImage(
                    imageResourceId = R.drawable.vector4,
                    text = "التقويم",
                    onClick = {})
                MyImage(
                    imageResourceId = R.drawable.vector5,
                    text = "التسبيح",
                    onClick = { navController.navigate("AltasbihScreen") })
                MyImage(
                    imageResourceId = R.drawable.vector6,
                    text = "الادعية",
                    onClick = { navController.navigate("AliadieiaScreen") })
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Top
            ) {
                MyImage(
                    imageResourceId = R.drawable.vector7,
                    description = "",
                    text = "اذكار متنوعة",
                    onClick = { navController.navigate("AzkarCategoriesScreen") })
                MyImage(
                    imageResourceId = R.drawable.vector8,
                    description = "",
                    text = "اقرب مسجد",
                    onClick = { navController.navigate("") })
                MyImage(
                    imageResourceId = R.drawable.vector9,
                    description = "",
                    text = "المفضلة ",
                    onClick = { navController.navigate("") })
            }
        }
    }
    }
}

@Composable
fun MyImage(
    imageResourceId: Int,
    description: String? = null, // 🟢 جعلنا له قيمة افتراضية null لتسهيل الاستدعاء
    text: String? = null,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable(onClick = onClick) // 🟢 الضغط ينطبق على العنصر بالكامل
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = description,
            modifier = Modifier.size(60.dp) // 🟢 مسحنا الـ clickable المكرر من هنا
        )

        // 🟢 نعرض النص فقط لو مش null
        if (!text.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                lineHeight = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}