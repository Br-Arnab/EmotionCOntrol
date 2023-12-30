package com.saradaedu.emotioncontrol

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

@Composable
fun VideoPlayer() {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember(context) {
        SimpleExoPlayer.Builder(context).build().apply {
            val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context,
                Util.getUserAgent(context, context.packageName))

            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(
                    Uri.parse(
                    // Big Buck Bunny from Blender Project
                    "https://firebasestorage.googleapis.com/v0/b/emotion-control.appspot.com/o/-%20Murta%20Maheshwara%20%23Swami_Vivekananda_Vandana%20%23Violin%20%23%20%E0%A6%AE%E0%A7%82%E0%A6%B0%E0%A7%8D%E0%A6%A4%20%E0%A6%AE%E0%A6%B9%E0%A7%87%E0%A6%B6%E0%A7%8D%E0%A6%AC%E0%A6%B0%2C%20%E0%A6%B8%E0%A7%8D%E0%A6%AC%E0%A6%BE%E0%A6%AE%E0%A7%80%20%E0%A6%AC%E0%A6%BF%E0%A6%AC%E0%A7%87%E0%A6%95%E0%A6%BE%E0%A6%A8%E0%A6%A8%E0%A7%8D%E0%A6%A6%20%E0%A6%AC%E0%A6%A8%E0%A7%8D%E0%A6%A6%E0%A6%A8%E0%A6%BE.mp4?alt=media&token=8f7e609b-c30d-447a-bdd8-45dba6e478c2"
                      //  "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

                    ))

            this.prepare(source)
        }
    }
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                //playWhenReady = true
            }
        }
    )
}

