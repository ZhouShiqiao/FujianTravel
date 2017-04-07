package edu.fjnu.fujiantravel.activity.tourist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.fjnu.fujiantravel.R;

public class TouristOrderActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;

    private TextView all;
    private TextView waittake;
    private TextView waitbegin;
    private TextView waitconfirm;
    private TextView waitevaluate;

    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_order);
        findview();
        initview();
    }
    private void findview(){
        toolbar=(Toolbar)findViewById(R.id.tourist_order_toolbar);
        all=(TextView)findViewById(R.id.tourist_order_all);
        waittake=(TextView)findViewById(R.id.tourist_order_waittaking);
        waitconfirm=(TextView)findViewById(R.id.tourist_order_waitconfirm);
        waitbegin=(TextView)findViewById(R.id.tourist_order_waitbegin);
        waitevaluate=(TextView)findViewById(R.id.tourist_order_waitevaluate);
        listview=(ListView)findViewById(R.id.tourist_order_list);
    }
    private void initview(){
        all.setOnClickListener(this);
        waittake.setOnClickListener(this);
        waitbegin.setOnClickListener(this);
        waitconfirm.setOnClickListener(this);
        waitevaluate.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tourist_order_all:
                break;
            case R.id.tourist_order_waittaking:
                break;
            case R.id.tourist_order_waitbegin:
                break;
            case R.id.tourist_order_waitconfirm:
                break;
            case R.id.tourist_order_waitevaluate:
                break;
        }
    }
}
