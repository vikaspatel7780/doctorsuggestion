package com.myapp.voicehealth.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.sharp.AccountBox
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.myapp.voicehealth.R
import com.myapp.voicehealth.core.storage.UserPreferences
import com.myapp.voicehealth.ui.components.FeatureTile
import com.myapp.voicehealth.ui.components.VoiceHealthTopBar
import com.myapp.voicehealth.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val userPrefs = UserPreferences(LocalContext.current)
    val authViewModel: AuthViewModel = viewModel()
    val bannerImages = listOf(
        R.drawable.doctor_banner1,
        R.drawable.doctor_banner2,
        R.drawable.doctor_banner1,
        R.drawable.doctor_banner3
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController,
                onItemClick = { navigationText ->
                    navController.navigate(navigationText)
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                VoiceHealthTopBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onUpgradeClick = { /* Handle Upgrade */ }
                )
            },
            containerColor = Color.White
        ) { innerPadding ->

            // Main scrollable grid with header
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 108.dp // bottom spacing for FAB or comfortable padding
                ),
                content = {
                    // Header: Health Tip + Banner
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        val healthTips = listOf(
                            "💡 Stay hydrated and sleep well.",
                            "💡 Visit your doctor regularly.",
                            "💡 Complete all your medications.",
                            "💡 A 30-min walk keeps your heart healthy."
                        )
                        val healthTip = remember { healthTips.random() }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        ) {
                            Text(
                                text = healthTip,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF444444),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            AutoScrollingBanner(images = bannerImages)
                        }
                    }

                    // Feature tiles
                    val featureList = listOf(
                        "Ask AI Assistant" to R.drawable.ai_assist,
                        "Doctor Suggestions" to R.drawable.doctors_advice,
                        "Scan Report" to R.drawable.scan_report,
                        "Health Records" to R.drawable.health_record,
                        "Nearby Doctors" to R.drawable.nearby_doctors,
                        "Book Appointment" to R.drawable.appointment_book_1
                    )

                    items(featureList) { item ->
                        FeatureTile(item.first, item.second) {
                            when (item.first) {
                                "Ask AI Assistant" -> navController.navigate("ask_ai_screen")
                                "Doctor Suggestions" -> navController.navigate("doctor_suggestion")
                                "Scan Report" -> navController.navigate("scan_report_screen")
                                "Health Records" -> navController.navigate("health_history_screen")
                                "Nearby Doctors" -> navController.navigate("nearby_doctors_screen")
                                "Book Appointment" -> navController.navigate("nearby_doctors_screen")
                            }
                        }
                    }
                }
            )
        }
    }
}







@Composable
fun DrawerContent(
    navController: NavController,
    userName: String = "John Doe",
    userEmail: String = "john.doe@example.com",
    onItemClick: (String) -> Unit // Pass route or label
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userPrefs = UserPreferences(context)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 16.dp, top = 30.dp)
    ) {
        // Profile section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_user),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(userName, style = MaterialTheme.typography.titleMedium)
                Text(userEmail, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Divider()

        // Menu items
        DrawerMenuItem(text = "Home", icon = Icons.Default.Home) {
            onItemClick("doctor_suggestion")
        }

        DrawerMenuItem(text = "Doctor Suggestions", icon = Icons.Sharp.AccountBox) {
            onItemClick("doctor_suggestion")
        }

        DrawerMenuItem(text = "Search Doctors", icon = Icons.Default.Search) {
            onItemClick("doctor_suggestion")
        }

        DrawerMenuItem(text = "Health History", icon = Icons.Default.Done) {
            onItemClick("doctor_suggestion")
        }

        DrawerMenuItem(text = "Profile Settings", icon = Icons.Default.Settings) {
            onItemClick("doctor_suggestion")
        }

        DrawerMenuItem(text = "Notifications", icon = Icons.Default.Notifications) {
            onItemClick("doctor_suggestion")
        }

        DrawerMenuItem(text = "Help & Feedback", icon = Icons.Default.Info) {
            onItemClick("doctor_suggestion")
        }

    }
}

@Composable
fun DrawerMenuItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun AutoScrollingBanner(
    images: List<Int>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentIndex = (currentIndex + 1) % images.size
            listState.animateScrollToItem(currentIndex)
        }
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val bannerHeight = screenWidth * 0.5f // 16:9 or 2:1 ratio

    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .height(bannerHeight),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(images.size) { index ->
            Box(
                modifier = Modifier
                    .width(screenWidth - 40.dp) // to add spacing and padding
                    .height(bannerHeight)
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = images[index]),
                    contentDescription = "Banner $index",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )

                // Ad badge (top-left)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(Color(0xAA000000), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Ad",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Bottom overlay text
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                            )
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Book a free doctor consultation today",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}


