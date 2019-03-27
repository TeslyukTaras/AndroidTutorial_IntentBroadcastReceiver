package com.teslyuk.android.androidtutorial_intentbroadcastreceiver

import android.content.Context
import android.content.Intent
import android.os.Handler

class AuthBot(var context: Context) {

    companion object {
        const val LOGIN_FILL_TIME = 3000L
        const val PASSWORD_FILL_TIME = 4000L
        const val AUTH_FILL_TIME = 5000L
    }

    private lateinit var handler: Handler

    fun startFillingData() {
        handler = Handler()
        handler.postDelayed({
            val intent = Intent(LoginActivity.TEST_BROADCAST_CHANGE_LOGIN)
            context.sendBroadcast(intent)
        }, LOGIN_FILL_TIME)

        handler.postDelayed({
            val intent = Intent(LoginActivity.TEST_BROADCAST_CHANGE_PASSWORD)
            context.sendBroadcast(intent)
        }, PASSWORD_FILL_TIME)

        handler.postDelayed({
            val intent = Intent(LoginActivity.TEST_BROADCAST_CONFIRM)
            context.sendBroadcast(intent)
        }, AUTH_FILL_TIME)
    }
}