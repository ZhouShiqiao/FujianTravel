package edu.fjnu.fujiantravel.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Map;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.order.Order;

public class OrderSimpleInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private Map<String,TextView> TextViewMap;
    private Button ensurebutton;
    private Button cancelbutton;
    private ImageButton backbutton;

    private String id;
    private Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_simple_info);

        Bundle bundle = this.getIntent().getExtras();
        this.id = bundle.getString("id");

        this.initdate();
        this.findview();
        this.initview();
    }
    private void findview(){
        ensurebutton=(Button)findViewById(R.id.simpleorderinfo_ensurebutton);
        cancelbutton=(Button)findViewById(R.id.simpleorderinfo_cancelbutton);
        backbutton=(ImageButton)findViewById(R.id.simpleorderinfo_back);
        TextViewMap.put("orderid",(TextView)findViewById(R.id.simpleorderinfo_orderid));
        TextViewMap.put("touristid",(TextView)findViewById(R.id.simpleorderinfo_touristid));
        TextViewMap.put("begintime",(TextView)findViewById(R.id.simpleorderinfo_begintime));
        TextViewMap.put("senic",(TextView)findViewById(R.id.simpleorderinfo_scenic));
        TextViewMap.put("route",(TextView)findViewById(R.id.simpleorderinfo_route));
        TextViewMap.put("playtime",(TextView)findViewById(R.id.simpleorderinfo_palytime));
        TextViewMap.put("peoplenumber",(TextView)findViewById(R.id.simpleorderinfo_peoplenumber));
        TextViewMap.put("remark",(TextView)findViewById(R.id.simpleorderinfo_remark));
    }
    private void initview(){
        ensurebutton.setOnClickListener(this);
        cancelbutton.setOnClickListener(this);
        backbutton.setOnClickListener(this);
    }
    private void initdate(){
        this.TextViewMap = new HashMap<String,TextView>();
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.simpleorderinfo_back:
                this.finish();
                break;
            case R.id.simpleorderinfo_cancelbutton:
                this.finish();
                break;
            case R.id.simpleorderinfo_ensurebutton:
                break;
        }
    }
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Toast toast = null;
            System.out.println(msg.what);
            switch (msg.what) {
                case Order.QUERYORDER_SUCCESS:
                    break;
                case Order.QUERYORDER_ERROR:
                    break;
            }
        }
    };
}
