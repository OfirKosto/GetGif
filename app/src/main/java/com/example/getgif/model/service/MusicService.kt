package com.example.getgif.model.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder


class MusicService : Service(), MediaPlayer.OnPreparedListener{

    private val mediaPlayer: MediaPlayer = MediaPlayer()

    override fun onCreate() {
        super.onCreate()
        mediaPlayer.setOnPreparedListener(this)
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val songUri = intent?.getStringExtra("uri")

        songUri?.let{
            mediaPlayer.setDataSource(songUri)
            mediaPlayer.prepareAsync()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onPrepared(p0: MediaPlayer?) {
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }
}