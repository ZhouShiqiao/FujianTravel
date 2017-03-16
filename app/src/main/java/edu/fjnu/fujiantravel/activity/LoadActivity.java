package edu.fjnu.fujiantravel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.push.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class LoadActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        
        final Intent localIntent = new Intent(this, ChooseActivity.class);
        Timer timer = new Timer();

        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
                Utils.getMetaValue(LoadActivity.this, "api_key"));

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
