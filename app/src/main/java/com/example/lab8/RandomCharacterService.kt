package com.example.lab8


import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class RandomCharacterService : Service() {

    private var isRunning = false
    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRunning = true

        Thread {
            while (isRunning) {
                Thread.sleep(1000)
                val randomChar = alphabet[Random().nextInt(alphabet.size)]
                val broadcastIntent = Intent("my.custom.action.tag.lab6")
                broadcastIntent.putExtra("randomCharacter", randomChar)
                sendBroadcast(broadcastIntent)
            }
        }.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

