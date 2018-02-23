package com.xiyou3g.rainbower.pocket.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.xiyou3g.rainbower.pocket.R;

/**
 * Created by dell2014 on 2017/5/14.
 */

public class RegisterSecondActivity extends Activity {

    ImageButton nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester_second);

        nextBtn = (ImageButton) findViewById(R.id.register_second_next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterSecondActivity.this, RegisterThirdActivity.class);
                startActivity(intent);
            }
        });


    }
}
