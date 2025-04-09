package com.example.lab8
import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.lab8.R

class MyService : Service() {

    private lateinit var soundPlayer: MediaPlayer
    private val CHANNEL_ID = "channelId"

    override fun onCreate() {
        soundPlayer = MediaPlayer.create(this, R.raw.music)
        soundPlayer.isLooping = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Music Channel"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music)
            .setContentTitle("My Music Player")
            .setContentText("Music is playing")
            .build()

        startForeground(1, notification)
        soundPlayer.start()

        return START_STICKY
    }

    override fun onDestroy() {
        soundPlayer.stop()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
