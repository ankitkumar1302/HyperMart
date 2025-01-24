package com.isodev.hypermart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.isodev.hypermart.ui.theme.HyperMartTheme
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HyperMartTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        CustomBottomBar()
                    },
                    topBar = {
                        TopBar()
                    }) { innerPadding ->
                    Home(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Home(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
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
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(painter = painterResource(R.drawable.hypermart), contentDescription = "")
                Row(
                    modifier = Modifier.padding(end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painter = painterResource(R.drawable.group_6), contentDescription = "")
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(painter = painterResource(R.drawable.group_5), contentDescription = "")
                }
            }
        }
    )
}

@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    contentColor: Color = contentColorFor(containerColor),
    contentPadding: PaddingValues = PaddingValues(),
    windowInsets: WindowInsets = WindowInsets(0.dp),
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp)
                .shadow(5.dp)
                .background(containerColor)
        )
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Transparent,
                contentColor = contentColor,
                tonalElevation = 0.dp,
                contentPadding = contentPadding,
                windowInsets = windowInsets,
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(onClick = {}) {
                            Image(
                                painter = painterResource(R.drawable.ic_home),
                                contentDescription = "Home"
                            )
                        }
                        IconButton(onClick = {}) {
                            Image(
                                painter = painterResource(R.drawable.dashboard_1),
                                contentDescription = "Dashboard"
                            )
                        }
                        Spacer(modifier = Modifier.width(73.dp))
                        IconButton(onClick = {}) {
                            Image(
                                painter = painterResource(R.drawable.ic_wishlist),
                                contentDescription = "Wishlist"
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Account",
                                tint = colorResource(id = R.color.gray)
                            )
                        }
                    }
                }
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = -25.dp)
                    .size(73.dp)
                    .shadow(5.dp, shape = CircleShape)
                    .clip(CircleShape)
                    .background(color = colorResource(id = R.color.white))
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(63.dp)
                        .clip(CircleShape)
                        .background(color = colorResource(id = R.color.orange))
                ) {
                    Image(
                        painter = painterResource(R.drawable.vector),
                        contentDescription = "Center Icon",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomPager() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(start = 54.dp, end = 54.dp),
    ) { page ->
        Card(
            Modifier
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
                }
                .size(318.dp, 150.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(R.drawable.group9),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarAndLocation() {
    var searchText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.group_7),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Bengaluru",
                    color = Color.Black
                )
                Text(
                    text = "BTM Layout, 500628",
                    color = Color.Gray
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = {
                Text(
                    text = "Search Anything...",
                    color = Color.Gray
                )
            },
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.search__6__1),
                    contentDescription = null,
                )
            },
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(R.drawable.vector_3), contentDescription = "")
                    Image(
                        painter = painterResource(R.drawable.mic__1__1),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 4.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(R.color.search_bar_background),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 8.dp, vertical = 2.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = colorResource(R.color.search_bar_background),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}


@Composable
fun CategoriesLazyRow(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp, start = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categories",
                color = Color(0xFF303733),
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(6) { index ->
                CategoryItem(
                    image = listOf(
                        R.drawable.ic_vegetables,
                        R.drawable.vector__1_,
                        R.drawable.washing_machine_1,
                        R.drawable.ic_vegetables,
                        R.drawable.vector__1_,
                        R.drawable.washing_machine_1,
                    )[index % 6],
                    name = listOf(
                        "Groceries", "Appliances", "Fashion", "Groceries", "Appliances", "Fashion"
                    )[index % 6],
                    backgroundColor = listOf(
                        Color(0xFF4AB7B6),
                        Color(0xFF4B9DCB),
                        Color(0xFFAF558B),
                        Color(0xFFA187D9),
                        Color(0xFF4AB7B6),
                        Color(0xFF4B9DCB)
                    )[index % 6]
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    image: Int,
    name: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .size(width = 90.dp, height = 96.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
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
                    .padding(end = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Delivered",
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "On Wed, 27 Jul 2022",
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(10.dp))
                        .padding(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                            text = "+5 ",
                            color = Color.Gray,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Order ID : #28292999",
                            color = Color.Gray,
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
                Text(
                    text = "Order Again & Get Flat 10% OFF",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .rotate(90f)
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
            color = Color(0xFF303733),
            fontWeight = FontWeight.Bold
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
            .height(300.dp)
            .padding(8.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            content = {
                items(items.size) { index ->
                    ProductCard(items[index])
                }
            }
        )
    }
}


@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .height(230.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(Color.White),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    painter = painterResource(product.image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp, 94.dp)
                        .align(Alignment.Center)
                )
            }
            Text(
                text = product.name,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(R.color.product_name_color)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.price,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = product.rating.toString(),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700)
                        )
                    )
                    Icon(
                        painter = painterResource(android.R.drawable.star_on),
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .size(36.dp)
                        .background(color = colorResource(R.color.button_red))
                ) {
                    Text(
                        text = "-",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
                Text(
                    text = "2",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = colorResource(R.color.text_green)
                )

                IconButton(
                    onClick = { /* Decrement action */ },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .size(36.dp)
                        .background(color = colorResource(R.color.button_green))
                ) {
                    Text(
                        text = "+",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                        color = Color.White
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