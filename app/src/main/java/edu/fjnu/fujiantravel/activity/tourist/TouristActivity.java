package edu.fjnu.fujiantravel.activity.tourist;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import edu.fjnu.fujiantravel.fragment.MainFragment;
import edu.fjnu.fujiantravel.fragment.TouristFragment;
import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.user.User;

public class TouristActivity extends AppCompatActivity implements View.OnClickListener {
    public static Boolean whetherlog = false;
    public static User user = null;

    private long exitTime = 0;

    private FrameLayout framelayout;
    private TouristFragment touristfragment = null;
    private MainFragment mainfragment = null;

    private Button infobutton = null;
    private Button mainbutton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist);
        findview();
        initview();
        setDefaultFragment();
    }

    private void findview() {
        framelayout = (FrameLayout) findViewById(R.id.tourist_content);
        infobutton = (Button) findViewById(R.id.tourist_infobutton);
        mainbutton=(Button)findViewById(R.id.tourist_mainbutton);
    }

    private void initview() {
        infobutton.setOnClickListener(this);
        mainbutton.setOnClickListener(this);
    }

    public static void log(User user) {
        TouristActivity.user = user;
        TouristActivity.whetherlog = true;
    }
    public static Boolean whetherlog(){
        return whetherlog;
    }
    public static User getUser(){
        return user;
    }


    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mainfragment=new MainFragment();
        transaction.replace(R.id.tourist_content, mainfragment);
        transaction.commit();
    }

    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.tourist_infobutton:
                if (touristfragment == null) {
                    touristfragment = new TouristFragment();
                }
                transaction.replace(R.id.tourist_content, touristfragment);
                break;
            case R.id.tourist_mainbutton:
                if (mainfragment == null) {
                    mainfragment = new MainFragment();
                }
                transaction.replace(R.id.tourist_content, mainfragment);
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();

    }
    /*
    重写返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
