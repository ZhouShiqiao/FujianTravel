package edu.fjnu.fujiantravel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import edu.fjnu.fujiantravel.R;

import java.util.Timer;
import java.util.TimerTask;

public class LoadActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        
        final Intent localIntent = new Intent(this, ChooseActivity.class);
        Timer timer = new Timer();
        TimerTask tast = new TimerTask() {
            @Override
            public void run() {
                startActivity(localIntent);
                LoadActivity.this.finish();
            }
        };
        timer.schedule(tast, 3000);

    }

}
