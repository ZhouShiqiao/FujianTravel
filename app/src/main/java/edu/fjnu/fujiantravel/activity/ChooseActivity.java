package edu.fjnu.fujiantravel.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.activity.guide.GuideActivity;
import edu.fjnu.fujiantravel.activity.tourist.TouristActivity;
import edu.fjnu.fujiantravel.push.Utils;

public class ChooseActivity extends AppCompatActivity {
    private ImageButton tourist;
    private ImageButton guide;

    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
                Utils.getMetaValue(this, "api_key"));
        findview();
        initview();
    }

    private void findview() {
        tourist = (ImageButton) findViewById(R.id.tourist);
        guide = (ImageButton) findViewById(R.id.guide);
    }

    private void initview() {
        tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChooseActivity.this, TouristActivity.class);
                startActivity(intent);
                finish();
            }
        });
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChooseActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

