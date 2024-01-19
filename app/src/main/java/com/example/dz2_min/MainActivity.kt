package com.example.dz2_min

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.dz2_min.model.Post
import com.example.dz2_min.ui.theme.DZ2_minTheme
import com.example.dz2_min.vm.BeerViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DZ2_minTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CenterAlignedTopAppBar()
                }
            }
        }
    }
}

@Composable
fun BeerPhoto(
    post: Post,
    modifier: Modifier = Modifier,
    backHandle: () -> Unit = {},
    isFull: Boolean = false
) {
    BackHandler {
        backHandle()
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(
                elevation = 200.dp,
                ambientColor = Color(0x14000000)
            )
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 16.dp))
    ) {
        SubcomposeAsyncImage(
            model = post.url,
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 12.dp)
                .width(328.dp)
                .height(301.dp)
                .clip(RoundedCornerShape(30.dp))
        )
        {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                CircularProgressIndicator()
            } else {
                SubcomposeAsyncImageContent()
            }
        }
        Text(
            text = post.title,
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
                letterSpacing = 0.2.sp,
            ),
            modifier = Modifier.padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 16.dp)
        )
        if (isFull){
            Text(
                text = "id = ${post.id}",
                style = TextStyle(
                    fontSize = 11.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF818C99),
                    letterSpacing = 0.3.sp,
                ),
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBar() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Аналог Threads",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        BeerScreen()
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BeerScreen() {
    var selected by remember {
        mutableStateOf(-1)
    }
    val scope = rememberCoroutineScope()
    val viewModel: BeerViewModel = viewModel()
    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getAllBeers()
        }
    }


    val beers by viewModel.beerUiState.collectAsState()
    var filteredBeers by remember {
        mutableStateOf(beers)
    }
    var filterText by remember {
        mutableStateOf("")
    }
    if (beers.isEmpty()){
        ShowLoading()
    } else {
        if (selected != -1) {
            beers.find { it.id == selected }
                ?.let {
                    BeerPhoto(
                        post = it,
                        backHandle = { selected = -1 },
                        isFull = true,
                        modifier = Modifier.padding(0.dp,60.dp,0.dp,0.dp)
                    )
                }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(1),
                verticalItemSpacing = 1.dp,
                horizontalArrangement = Arrangement.spacedBy(1.dp),

                modifier = Modifier.padding(0.dp,60.dp,0.dp,0.dp),
                content = {
                    items(filteredBeers.ifEmpty {
                        if (filterText.isEmpty()) beers else listOf(
                            Post(
                                -1,
                                -1,
                                "Without beer",
                                "No url",
                                "No url"
                            )
                        )
                    }) {
                        BeerPhoto(post = it, Modifier.clickable { selected = it.id })
                    }
                }
            )

        }
    }

}

@Composable
fun ShowLoading() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator()
        }

    }
}
