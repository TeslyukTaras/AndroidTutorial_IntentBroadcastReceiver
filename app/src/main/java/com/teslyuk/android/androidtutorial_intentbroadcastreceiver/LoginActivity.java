package com.teslyuk.android.androidtutorial_intentbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText loginEt, passwordEt;
    private Handler handler;

    private static final String TEST_BROADCAST_CHANGE_LOGIN =
            "TEST_BROADCAST_CHANGE_LOGIN";
    private static final String TEST_BROADCAST_CHANGE_PASSWORD =
            "TEST_BROADCAST_CHANGE_PASSWORD";
    private static final String TEST_BROADCAST_CONFIRM =
            "TEST_BROADCAST_CONFIRM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startHandler();
        initListener();
        initReceivers();
    }

    @Override
    protected void onPause() {
        unregisterReceivers();
        super.onPause();
    }

    private void initReceivers() {
        IntentFilter loginIntentFiler = new IntentFilter(TEST_BROADCAST_CHANGE_LOGIN);
        registerReceiver(mLoginReceiver, loginIntentFiler);

        IntentFilter passwordIntentFiler = new IntentFilter(TEST_BROADCAST_CHANGE_PASSWORD);
        registerReceiver(mPasswordReceiver, passwordIntentFiler);

        IntentFilter confirmIntentFiler = new IntentFilter(TEST_BROADCAST_CONFIRM);
        registerReceiver(mConfirmReceiver, confirmIntentFiler);
    }

    private void unregisterReceivers() {
        unregisterReceiver(mLoginReceiver);
        unregisterReceiver(mPasswordReceiver);
        unregisterReceiver(mConfirmReceiver);
    }

    BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.isEmpty(loginEt.getText())) {
                loginEt.setText("test login");
            }
        }
    };

    BroadcastReceiver mPasswordReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.isEmpty(passwordEt.getText())) {
                passwordEt.setText("test password");
            }
        }
    };

    BroadcastReceiver mConfirmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            goToProfileScreen();
        }
    };

    private void startHandler() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(TEST_BROADCAST_CHANGE_LOGIN);
                sendBroadcast(intent);
            }
        }, 3000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(TEST_BROADCAST_CHANGE_PASSWORD);
                sendBroadcast(intent);
            }
        }, 4000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(TEST_BROADCAST_CONFIRM);
                sendBroadcast(intent);
            }
        }, 5000);
    }

    private void initView() {
        loginBtn = findViewById(R.id.login_btn);
        loginEt = findViewById(R.id.login_et);
        passwordEt = findViewById(R.id.password_et);
    }

    private void initListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfileScreen();
            }
        });
    }

    private void goToProfileScreen() {
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        startActivity(intent);
    }


}
