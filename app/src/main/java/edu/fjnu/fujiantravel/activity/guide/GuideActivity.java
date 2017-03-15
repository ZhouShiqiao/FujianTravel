package edu.fjnu.fujiantravel.activity.guide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.user.User;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    private Button infobutton;
    private Button button2;
    private Button button3;

    private FrameLayout framelayout;

    private User user;
    private Boolean flag;

    private static GuideActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        findview();
        initview();
        initdata();
    }

    private void findview() {
        infobutton = (Button) findViewById(R.id.guide_myguide);
        framelayout = (FrameLayout) findViewById(R.id.guide_contents);
    }

    private void initview() {
        infobutton.setOnClickListener(this);
    }

    private void initdata() {
        instance = this;
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.guide_myguide:
                intent.setClass(this, GuideInfoActivity.class);
                break;
        }
    }

    public static GuideActivity getInstance() {
        return instance;
    }
}
