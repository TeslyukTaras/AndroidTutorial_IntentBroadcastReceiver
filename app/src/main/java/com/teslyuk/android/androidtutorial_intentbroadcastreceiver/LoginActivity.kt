package com.teslyuk.android.androidtutorial_intentbroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TEST_BROADCAST_CHANGE_LOGIN = "TEST_BROADCAST_CHANGE_LOGIN"
        private const val TEST_BROADCAST_CHANGE_PASSWORD = "TEST_BROADCAST_CHANGE_PASSWORD"
        private const val TEST_BROADCAST_CONFIRM = "TEST_BROADCAST_CONFIRM"
    }

    private lateinit var loginBtn: Button
    private lateinit var loginEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var handler: Handler

    internal var mLoginReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (TextUtils.isEmpty(loginEt.text)) {
                loginEt.setText("test login")
            }
        }
    }

    internal var mPasswordReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (TextUtils.isEmpty(passwordEt.text)) {
                passwordEt.setText("test password")
            }
        }
    }

    internal var mConfirmReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            goToProfileScreen()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    override fun onResume() {
        super.onResume()
        startHandler()
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

    private fun startHandler() {
        handler = Handler()
        handler.postDelayed({
            val intent = Intent(TEST_BROADCAST_CHANGE_LOGIN)
            sendBroadcast(intent)
        }, 3000)

        handler.postDelayed({
            val intent = Intent(TEST_BROADCAST_CHANGE_PASSWORD)
            sendBroadcast(intent)
        }, 4000)

        handler.postDelayed({
            val intent = Intent(TEST_BROADCAST_CONFIRM)
            sendBroadcast(intent)
        }, 5000)
    }

    private fun initView() {
        loginBtn = findViewById(R.id.login_btn)
        loginEt = findViewById(R.id.login_et)
        passwordEt = findViewById(R.id.password_et)
    }

    private fun initListener() {
        loginBtn.setOnClickListener { goToProfileScreen() }
    }

    private fun goToProfileScreen() {
        val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
        startActivity(intent)
    }
}
