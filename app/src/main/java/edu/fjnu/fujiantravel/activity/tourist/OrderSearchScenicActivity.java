package edu.fjnu.fujiantravel.activity.tourist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import edu.fjnu.fujiantravel.R;

public class OrderSearchScenicActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private EditText edittext;
    private TextView textview;
    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_search_scenic);
        findview();
        initview();
    }

    public void findview() {
        button =(Button) findViewById(R.id.ordersenic_location);
        edittext=(EditText)findViewById(R.id.ordersenic_search);
        textview=(TextView)findViewById(R.id.ordersenic_cancel);
        listview=(ListView)findViewById(R.id.ordersenic_seniclist);
    }

    public void initview() {
        textview.setOnClickListener(this);
        button.setOnClickListener(this);
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ordersenic_cancel:
                finish();
                break;
            case R.id.ordersenic_location:
                Intent intent = new Intent();
                intent.setClass(this,ChooseLocationActivity.class);
                startActivity(intent);
                break;
        }
    }
}
