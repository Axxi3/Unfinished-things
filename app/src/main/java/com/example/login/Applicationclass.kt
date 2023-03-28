package com.example.login

import android.app.Application
import android.widget.Toast


class Applicationclass:Application() {
    override fun onCreate() {
        Toast.makeText(this, "with love by aashu raj <3", Toast.LENGTH_SHORT).show()
        super.onCreate()
    }
}