package com.example.getgif

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.getgif.model.service.MusicService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Intent(this, MusicService::class.java).also {
            it.putExtra("uri", "https://drive.google.com/u/0/uc?id=1vLWBKzCrSnUGau6dPWWZ6sE7Lovt8P0-&export=download")
            startService(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Intent(this, MusicService::class.java).also {
            stopService(it)
        }
    }
}