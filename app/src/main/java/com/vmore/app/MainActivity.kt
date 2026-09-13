package com.vmore.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.vmore.app.ui.VMoreTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedText = getSharedText(intent)

        setContent {
            VMoreTheme {
                VMoreApp(
                    initialUrl = sharedText
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    private fun getSharedText(intent: Intent?): String {

        if (intent == null) return ""

        return when (intent.action) {

            Intent.ACTION_SEND ->
                intent.getStringExtra(Intent.EXTRA_TEXT).orEmpty()

            Intent.ACTION_VIEW ->
                intent.dataString.orEmpty()

            else -> ""
        }.trim()
    }
}

@Composable
fun VMoreApp(
    initialUrl: String
) {

    var selectedTab by remember {
        mutableStateOf(0)
    }

    var url by remember {
        mutableStateOf(initialUrl)
    }

    val clipboard = LocalClipboardManager.current

    Scaffold(

        bottomBar = {

            NavigationBar(
                modifier = Modifier.navigationBarsPadding()
            ) {

                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                    },
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                    },
                    icon = {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = "Downloads"
                        )
                    },
                    label = {
                        Text("Downloads")
                    }
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                    },
                    icon = {
                        Icon(
                            Icons.Default.History,
                            contentDescription = "History"
                        )
                    },
                    label = {
                        Text("History")
                    }
                )

                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = {
                        selectedTab = 3
                    },
                    icon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    },
                    label = {
                        Text("Settings")
                    }
                )
            }
        }

    ) { padding ->

        when (selectedTab) {

            0 -> HomeScreen(
                padding = padding,
                url = url,
                onUrlChange = {
                    url = it
                },
                onPaste = {

                    clipboard.getText()?.text?.let {
                        url = it
                    }

                },
                onDownload = {

                    selectedTab = 1

                }
            )

            1 -> DownloadsScreen(padding)

            2 -> HistoryScreen(padding)

            3 -> SettingsScreen(padding)
        }
    }
}

@Composable
fun HomeScreen(
    padding: PaddingValues,
    url: String,
    onUrlChange: (String) -> Unit,
    onPaste: () -> Unit,
    onDownload: () -> Unit
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(
                horizontal = 20.dp,
                vertical = 24.dp
            ),

        verticalArrangement = Arrangement.Top

    ) {

        Text(
            text = "VMore",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Multimedia Downloader",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        OutlinedTextField(

            value = url,

            onValueChange = onUrlChange,

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text("Paste video URL")
            },

            placeholder = {
                Text("https://...")
            },

            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                onClick = onPaste,
                modifier = Modifier.weight(1f)
            ) {

                Icon(
                    Icons.Default.ContentPaste,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(
                    modifier = Modifier.size(8.dp)
                )

                Text("Paste")
            }

            Spacer(
                modifier = Modifier.size(12.dp)
            )

            Button(
                onClick = onDownload,
                enabled = url.isNotBlank(),
                modifier = Modifier.weight(1f)
            ) {

                Icon(
                    Icons.Default.Download,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(
                    modifier = Modifier.size(8.dp)
                )

                Text("Download")
            }
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "Share directly to VMore",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "YouTube, Instagram or TikTok me Share dabao aur VMore select karo. Shared link yahan automatically aa jayega.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun DownloadsScreen(
    padding: PaddingValues
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(20.dp)
    ) {

        Text(
            text = "Downloads",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "No active downloads yet.\nDownloader engine Phase 2 me add hoga.",
                modifier = Modifier.padding(18.dp)
            )
        }
    }
}

@Composable
fun HistoryScreen(
    padding: PaddingValues
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(20.dp)
    ) {

        Text(
            text = "History",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "No download history.",
                modifier = Modifier.padding(18.dp)
            )
        }
    }
}

@Composable
fun SettingsScreen(
    padding: PaddingValues
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(20.dp)
    ) {

        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "VMore v1.0",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text("Serverless architecture")

                Text("GitHub remote config ready")

                Text("Share Sheet integration enabled")
            }
        }
    }
}
