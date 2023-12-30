package com.saradaedu.emotioncontrol

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.common.base.Objects

@Composable
fun playAudio(context: Context,int: Int,song_name :String) {

    val mp : MediaPlayer = MediaPlayer.create(context, int)

    Column(
        modifier = Modifier.padding(10.dp)

    ) {



        Row() {
            Text(modifier = Modifier.padding(10 .dp), text = song_name)
            Image(
                painter = painterResource(id = R.drawable.ic__music),
                contentDescription = "",
                modifier = Modifier
                    .height(160.dp)
                    .width(160.dp)
                    .padding(32.dp)
                    .background(Color.Black)
            )
            IconButton(onClick = { mp.start() }, modifier = Modifier.size(35.dp)) {
                Icon(painter = painterResource(id = R.drawable.ic_play), contentDescription = "")
            }

            IconButton(onClick = { mp.pause() }, modifier = Modifier.size(35.dp)) {
                Icon(painter = painterResource(id = R.drawable.ic_stop), contentDescription = "")
            }
        }
    }
}