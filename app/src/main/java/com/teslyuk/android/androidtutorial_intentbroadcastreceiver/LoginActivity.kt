package com.teslyuk.android.androidtutorial_intentbroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        const val TEST_BROADCAST_CHANGE_LOGIN = "TEST_BROADCAST_CHANGE_LOGIN"
        const val TEST_BROADCAST_CHANGE_PASSWORD = "TEST_BROADCAST_CHANGE_PASSWORD"
        const val TEST_BROADCAST_CONFIRM = "TEST_BROADCAST_CONFIRM"
    }

    private var mLoginReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (TextUtils.isEmpty(loginEt.text)) {
                loginEt.setText("test login")
            }
        }
    }

    private var mPasswordReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (TextUtils.isEmpty(passwordEt.text)) {
                passwordEt.setText("test password")
            }
        }
    }

    private var mConfirmReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            goToProfileScreen()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onResume() {
        super.onResume()
        AuthBot(this).startFillingData()
        initListener()
        initReceivers()
    }

    override fun onPause() {
        unregisterReceivers()
        super.onPause()
    }

    private fun initReceivers() {
        val loginIntentFiler = IntentFilter(TEST_BROADCAST_CHANGE_LOGIN)
        registerReceiver(mLoginReceiver, loginIntentFiler)

        val passwordIntentFiler = IntentFilter(TEST_BROADCAST_CHANGE_PASSWORD)
        registerReceiver(mPasswordReceiver, passwordIntentFiler)

        val confirmIntentFiler = IntentFilter(TEST_BROADCAST_CONFIRM)
        registerReceiver(mConfirmReceiver, confirmIntentFiler)
    }

    private fun unregisterReceivers() {
        unregisterReceiver(mLoginReceiver)
        unregisterReceiver(mPasswordReceiver)
        unregisterReceiver(mConfirmReceiver)
    }

    private fun initListener() {
        loginBtn.setOnClickListener { goToProfileScreen() }
    }

    private fun goToProfileScreen() {
        val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
        startActivity(intent)
    }
}
