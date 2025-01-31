package com.isodev.hypermart.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.isodev.hypermart.R

private data class Category(
    val name: String,
    val icon: Int,
    val color: Color,
    val itemCount: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    var isLoaded by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        isLoaded = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Section with Search
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                SearchBar(
                    query = "",
                    onQueryChange = { },
                    onSearch = { },
                    active = false,
                    onActiveChange = { },
                    placeholder = {
                        Text(
                            text = "Search in categories...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) { }
            }
        }

        // Categories Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(allCategories) { category ->
                AnimatedVisibility(
                    visible = isLoaded,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { it * 2 },
                        animationSpec = spring(
                            dampingRatio = 0.8f,
                            stiffness = 200f
                        )
                    )
                ) {
                    CategoryCard(category)
                }
            }
        }
    }
}

@Composable
private fun CategoryCard(category: Category) {
    var isPressed by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .graphicsLayer {
                scaleX = if (isPressed) 0.95f else 1f
                scaleY = if (isPressed) 0.95f else 1f
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = category.color.copy(alpha = 0.15f)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = category.color.copy(alpha = 0.3f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                        }
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = category.color.copy(alpha = 0.2f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(category.icon),
                        contentDescription = category.name,
                        tint = category.color,
                        modifier = Modifier
                            .size(32.dp)
                            .graphicsLayer {
                                rotationZ = if (isPressed) 5f else 0f
                            }
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = "${category.itemCount} items",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private val allCategories = listOf(
    Category(
        name = "Fashion",
        icon = R.drawable.ic_category_fashion,
        color = Color(0xFFAF558B),
        itemCount = 245
    ),
    Category(
        name = "Groceries",
        icon = R.drawable.ic_category_groceries,
        color = Color(0xFF4B9DCB),
        itemCount = 156
    ),
    Category(
        name = "Appliances",
        icon = R.drawable.ic_category_appliances,
        color = Color(0xFF4AB7B6),
        itemCount = 89
    ),
    Category(
        name = "Electronics",
        icon = R.drawable.ic_category_fashion,
        color = Color(0xFFA187D9),
        itemCount = 178
    ),
    Category(
        name = "Beauty",
        icon = R.drawable.ic_category_fashion,
        color = Color(0xFFE6A27C),
        itemCount = 134
    ),
    Category(
        name = "Sports",
        icon = R.drawable.ic_category_fashion,
        color = Color(0xFF7CB342),
        itemCount = 92
    )
) 