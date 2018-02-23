package com.xiyou3g.rainbower.pocket.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xiyou3g.rainbower.pocket.config.MyAPP;
import com.xiyou3g.rainbower.pocket.R;

/**
 * Created by dell2014 on 2017/5/14.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    ImageButton loginBtn;
    TextView registerTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        loginBtn = (ImageButton) findViewById(R.id.login_login_btn);
        loginBtn.setOnClickListener(this);

        registerTV = (TextView) findViewById(R.id.login_register_tv);
        registerTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击登录按钮
            case R.id.login_login_btn:
                MyAPP.getApplication().setLoginStatus(true);
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                this.finish();
                break;
            //点击注册
            case R.id.login_register_tv:
                Intent intent = new Intent(LoginActivity.this, RegisterFirstActivity.class);
                startActivity(intent);
                break;
        }
    }
}
