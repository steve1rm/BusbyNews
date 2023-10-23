package me.androidbox.beerpaging.presentation.screen

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun WebNewsScreen(webLink: String) {
    AndroidView(
        factory = { context ->

            WebView(context).apply {
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)

                loadUrl(webLink)
            } },
        update = { webView ->
            webView.loadUrl(webLink)
        },
        modifier = Modifier.fillMaxSize())
}
