package com.isodev.hypermart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.isodev.hypermart.ui.theme.HyperMartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HyperMartTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    CustomBottomBar()
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

}

@Composable
fun TopBar(modifier: Modifier = Modifier) {

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





@Composable
fun CustomPager(modifier: Modifier = Modifier) {

}

@Composable
fun SearchBarAndLocation(modifier: Modifier = Modifier) {

}

@Composable
fun CategoriesLazyRow(modifier: Modifier = Modifier) {

}

@Composable
fun PreviousOrder(modifier: Modifier = Modifier) {

}

@Composable
fun PopularDealsLazyGrid(modifier: Modifier = Modifier) {

}