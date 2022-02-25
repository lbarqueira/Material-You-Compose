package com.luisbarqueira.materialyoucompose

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisbarqueira.materialyoucompose.ui.theme.MaterialYouComposeTheme
import kotlinx.coroutines.launch
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MaterialYouComposeTheme {
                ProvideWindowInsets {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ){
                        Column {
                            // Use statusBarsHeight() to add a spacer which pushes the content
                            // below the status bar (y-axis)
                            Spacer(Modifier.statusBarsHeight())
                            DividerItem()
                            SimpleScaffoldWithTopBar()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DividerItem(modifier: Modifier = Modifier) {
    // TODO (M3): No Divider, replace when available
    Divider(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleScaffoldWithTopBar() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Centered TopAppBar") },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            DividerItem()
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val list = (0..75).map { it.toString() }
                items(count = list.size) {
                    Text(
                        text = list[it],
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithSimpleSnackbar() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                onClick = {
                    // show snackbar as a suspend function
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Snackbar # ${++clickCount}"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Text(
                text = "Body content",
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    )
}

@Preview(
    name = "MyScreen preview day mode",
    showSystemUi = true,
    device = Devices.PIXEL_4,
    uiMode = UI_MODE_NIGHT_NO
)

@Preview(
    name = "MyScreen preview night mode",
    showSystemUi = true,
    device = Devices.PIXEL_4,
    uiMode = UI_MODE_NIGHT_YES
)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialYouComposeTheme {
        ScaffoldWithSimpleSnackbar()
    }
}
