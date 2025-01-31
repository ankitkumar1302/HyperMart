package com.isodev.hypermart.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.isodev.hypermart.Product
import com.isodev.hypermart.R
import androidx.compose.foundation.lazy.items

// Data Classes
private data class Banner(
    val id: Int,
    val imageRes: Int
)

private data class CategoryData(
    val id: Int,
    val name: String,
    val icon: Int,
    val color: Color
)

private data class PreviousOrder(
    val id: Int,
    val name: String,
    val date: String,
    val imageRes: Int
)

private data class PopularDeal(
    val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int
)

// Sample Data
private val banners = listOf(
    Banner(1, R.drawable.grid_1),
    Banner(2, R.drawable.grid_2),
    Banner(3, R.drawable.grid_3)
)

private val categories = listOf(
    CategoryData(1, "Fashion", R.drawable.ic_category_fashion, Color(0xFFE91E63)),
    CategoryData(2, "Electronics", R.drawable.ic_category_appliances, Color(0xFF2196F3)),
    CategoryData(3, "Groceries", R.drawable.ic_category_groceries, Color(0xFF4CAF50)),
    CategoryData(4, "Home", R.drawable.ic_category_appliances, Color(0xFFFF9800)),
    CategoryData(5, "Beauty", R.drawable.ic_wishlist, Color(0xFF9C27B0))
)

private val previousOrders = listOf(
    PreviousOrder(1, "Nike Air Max", "2 days ago", R.drawable.grid_1),
    PreviousOrder(2, "Levi's T-Shirt", "5 days ago", R.drawable.grid_2),
    PreviousOrder(3, "Apple Watch", "1 week ago", R.drawable.grid_3)
)

private val popularDeals = listOf(
    PopularDeal(1, "Wireless Earbuds", 99.99, R.drawable.grid_1),
    PopularDeal(2, "Smart Watch", 199.99, R.drawable.grid_2),
    PopularDeal(3, "Running Shoes", 79.99, R.drawable.grid_3),
    PopularDeal(4, "Backpack", 49.99, R.drawable.grid_4)
)

// Main Screen Implementation
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProductClick: (Product) -> Unit,
    onViewAllDealsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search Bar
        item {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                query = "",
                onQueryChange = { },
                onSearch = { },
                active = false,
                onActiveChange = { },
                placeholder = {
                    Text("Search products, brands and more...")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                enabled = true,
                shape = RoundedCornerShape(12.dp),
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    dividerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                ),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                windowInsets = SearchBarDefaults.windowInsets
            ) {}
        }

        // Location Bar
        item {
            LocationBar(
                city = "New York",
                area = "Manhattan",
                pincode = "10001"
            )
        }

        // Featured Banners
        item {
            FeaturedBanners()
        }

        // Categories
        item {
            CategorySection()
        }

        // Previous Orders
        item {
            PreviousOrdersSection()
        }

        // Popular Deals
        item {
            PopularDealsGrid()
        }
    }
}

@Composable
private fun LocationBar(
    city: String,
    area: String,
    pincode: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = area,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "$city, $pincode",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun FeaturedBanners() {
    var currentPage by remember { mutableStateOf(0) }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Image(
                painter = painterResource(banners[currentPage].imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Page Indicators
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(banners.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (currentPage == index)
                                    MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surfaceVariant
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun CategorySection() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                CategoryItem(category = category)
            }
        }
    }
}

@Composable
private fun CategoryItem(category: CategoryData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(72.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(category.color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(category.icon),
                contentDescription = category.name,
                tint = category.color,
                modifier = Modifier.size(32.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun PreviousOrdersSection() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Previous Orders",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(previousOrders) { order ->
                PreviousOrderItem(order = order)
            }
        }
    }
}

@Composable
private fun PreviousOrderItem(order: PreviousOrder) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(order.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = order.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
            
            Text(
                text = order.date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PopularDealsGrid() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Popular Deals",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            TextButton(onClick = { /* TODO */ }) {
                Text("View All")
            }
        }
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(popularDeals) { product ->
                ProductCard(product = product)
            }
        }
    }
}

@Composable
private fun ProductCard(product: PopularDeal) {
    Card(
        modifier = Modifier.width(180.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
            
            Text(
                text = "₹${product.price}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
} 