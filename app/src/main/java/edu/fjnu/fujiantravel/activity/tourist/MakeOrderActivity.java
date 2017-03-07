package edu.fjnu.fujiantravel.activity.tourist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import edu.fjnu.fujiantravel.R;

public class MakeOrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        findview();
        initview();
    }
    private void findview(){}
    private void initview(){}
}
