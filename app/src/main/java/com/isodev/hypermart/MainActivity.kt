package com.isodev.hypermart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.isodev.hypermart.navigation.AnimatedNavigation
import com.isodev.hypermart.navigation.Screen
import com.isodev.hypermart.ui.theme.Error
import com.isodev.hypermart.ui.theme.HyperMartTheme
import com.isodev.hypermart.ui.theme.Primary
import com.isodev.hypermart.ui.theme.Warning
import kotlin.math.absoluteValue

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HyperMartTheme {
                val navController = rememberNavController()
                val currentRoute by navController.currentBackStackEntryAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            CustomBottomBar(
                                currentRoute = currentRoute?.destination?.route
                                    ?: Screen.Home.route,
                                onNavigate = { route ->
                                    navController.navigate(route) {
                                        popUpTo(Screen.Home.route) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        },
                        topBar = { TopBar() },
                        contentWindowInsets = WindowInsets(0, 0, 0, 0)
                    ) { innerPadding ->
                        AnimatedNavigation(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Home(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SearchBarAndLocation()
        }
        item {
            CustomPager()
        }
        item {
            CategoriesLazyRow()
        }
        item {
            PreviousOrder()
        }
        item {
            PopularDealsGrid()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Hyper",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xFFFF8A00),
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp,
                            shadow = Shadow(
                                color = Color(0xFFFF8A00).copy(alpha = 0.2f),
                                offset = Offset(1f, 1f),
                                blurRadius = 2f
                            )
                        )
                    )
                    Text(
                        text = "Mart",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xFF4AB7B6),
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp,
                            shadow = Shadow(
                                color = Color(0xFF4AB7B6).copy(alpha = 0.2f),
                                offset = Offset(1f, 1f),
                                blurRadius = 2f
                            )
                        )
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    ) {
                        Badge(
                            modifier = Modifier.offset(x = 14.dp, y = (-14).dp),
                            containerColor = MaterialTheme.colorScheme.error
                        ) {
                            Text("2")
                        }
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        TextButton(
                            onClick = { /* TODO */ },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "EN",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Change Language",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier.shadow(elevation = 4.dp)
    )
}

@Composable
fun CustomBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 16.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(95.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp
        ) {
            val items = listOf(
                NavigationItem(
                    route = Screen.Home.route,
                    title = "Home",
                    selectedIcon = Icons.Default.Home,
                    unselectedIcon = Icons.Outlined.Home
                ),
                NavigationItem(
                    route = Screen.Dashboard.route,
                    title = "Categories",
                    selectedIcon = Icons.Default.Star,
                    unselectedIcon = Icons.Outlined.Star
                ),
                NavigationItem(
                    route = Screen.Cart.route,
                    title = "Cart",
                    selectedIcon = Icons.Default.ShoppingCart,
                    unselectedIcon = Icons.Outlined.ShoppingCart
                ),
                NavigationItem(
                    route = Screen.Account.route,
                    title = "Account",
                    selectedIcon = Icons.Default.Person,
                    unselectedIcon = Icons.Outlined.Person
                )
            )

            items.forEach { item ->
                val selected = currentRoute == item.route
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigate(item.route) },
                    icon = {
                        Icon(
                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

private data class NavigationItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomPager() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 16.dp
        ) { page ->
            Card(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        val scale = lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = scale
                        scaleY = scale
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        rotationY = lerp(
                            start = 10f,
                            stop = 0f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .fillMaxWidth()
                    .aspectRatio(2.1f),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                )
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(R.drawable.group9),
                        contentDescription = "Promotional Banner ${page + 1}",
                        modifier = Modifier.fillMaxSize()
                    )
                    // Add gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.3f)
                                    )
                                )
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Enhanced page indicator
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .height(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) { iteration ->
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == iteration)
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                        .size(if (pagerState.currentPage == iteration) 24.dp else 8.dp, 8.dp)
                        .animateContentSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarAndLocation() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.group_7),
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Bengaluru",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "BTM Layout, 500628",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                FilledIconButton(
                    onClick = { /* TODO */ },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Change Location",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SearchBar(
            query = "",
            onQueryChange = { },
            onSearch = { },
            active = false,
            onActiveChange = { },
            placeholder = {
                Text(
                    text = "Search Anything...",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Voice Search",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
                dividerColor = Color.Transparent
            )
        ) { }
    }
}

@Composable
fun CategoriesLazyRow(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "View All Categories",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    image = category.icon,
                    name = category.name,
                    backgroundColor = category.color
                )
            }
        }
    }
}

private val categories = listOf(
    Category("Fashion", R.drawable.ic_category_fashion, Color(0xFFAF558B)),
    Category("Groceries", R.drawable.ic_category_groceries, Color(0xFF4B9DCB)),
    Category("Appliances", R.drawable.ic_category_appliances, Color(0xFF4AB7B6)),
    Category("Fashion", R.drawable.ic_category_fashion, Color(0xFFA187D9))
)

data class Category(
    val name: String,
    val icon: Int,
    val color: Color
)

@Composable
fun CategoryItem(
    image: Int,
    name: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .size(width = 90.dp, height = 96.dp)
            .graphicsLayer {
                scaleX = if (isPressed) 0.95f else 1f
                scaleY = if (isPressed) 0.95f else 1f
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                        }
                    )
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(image),
                contentDescription = name,
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .graphicsLayer {
                        rotationZ = if (isPressed) 5f else 0f
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                color = Color.White,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Medium,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.2f),
                        offset = Offset(1f, 1f),
                        blurRadius = 2f
                    )
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PreviousOrder(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Previous Order",
            color = Color(0xFF303733),
            fontWeight = FontWeight.Bold,
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(3.dp, shape = RoundedCornerShape(16.dp))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Delivered",
                        color = Color(0xFF14AB87),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                    Text(
                        text = "On Wed, 27 Jul 2022",
                        color = Color(0xFF303733),
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(10.dp))
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.x),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp, 42.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.y),
                            contentDescription = null,
                            modifier = Modifier.size(42.dp, 43.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.z),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp, 42.dp)
                        )
                        Text(
                            text = "+5",
                            color = Color(0xFF303733),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Order ID : #28292999",
                            color = Color(0xFF303733),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Final Total : ₹ 123",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Button(
                        onClick = { /* TODO: Handle click */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF73C2C2),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Order Again")
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(40.dp)
                    .background(
                        color = Color(0xFFF28B82),
                        shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.order_again_text),
                    contentDescription = "",
                    modifier = Modifier.padding(
                        top = 32.dp,
                        bottom = 32.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                )
            }
        }
    }
}

@Composable
fun PopularDealsGrid(modifier: Modifier = Modifier) {
    val items = listOf(
        Product("Strawberries", "₹10", 4.8, R.drawable.grid_1, 1),
        Product("Fried Chips", "₹12", 4.8, R.drawable.grid_2, 2),
        Product("Moder Chair", "₹3599", 4.8, R.drawable.grid_3, 3),
        Product("LG washing machine", "₹45,999", 4.9, R.drawable.grid_4, 4)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Popular Deals",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "View All Deals",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    Box(
        modifier = modifier
            .height(300.dp)
            .padding(8.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { product ->
                ProductCard(product)
            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    var quantity by remember { mutableStateOf(1) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(product.image),
                    contentDescription = product.name,
                    modifier = Modifier.size(100.dp)
                )
            }

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.price,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Warning,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = product.rating.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = Warning
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (quantity > 1) quantity-- },
                    modifier = Modifier
                        .size(36.dp)
                        .background(Error, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Decrease",
                        tint = Color.White
                    )
                }

                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                IconButton(
                    onClick = { quantity++ },
                    modifier = Modifier
                        .size(36.dp)
                        .background(Primary, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

data class Product(
    val name: String,
    val price: String,
    val rating: Double,
    val image: Int,
    val id: Int
)