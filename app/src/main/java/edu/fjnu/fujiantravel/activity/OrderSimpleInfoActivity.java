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


import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.message.Json;
import edu.fjnu.fujiantravel.order.Order;
import edu.fjnu.fujiantravel.order.OrderThread;

public class OrderSimpleInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Map<String, TextView> TextViewMap = new HashMap<>();
    private Button ensurebutton;
    private Button cancelbutton;
    private ImageButton backbutton;

    private String id;
    private Order order = new Order();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_simple_info);

        Bundle bundle = this.getIntent().getExtras();
        this.id = bundle.getString("id");

        Thread thread = new Thread(new OrderThread(Order.QUERYORDER_SINGLE, id,
                getString(R.string.server_address), Integer.parseInt(getString(R.string.server_port)),
                OrderSimpleInfoActivity.this, handler));
        thread.start();

        this.findview();
        this.initview();
    }

    private void findview() {
        ensurebutton = (Button) findViewById(R.id.simpleorderinfo_ensurebutton);
        cancelbutton = (Button) findViewById(R.id.simpleorderinfo_cancelbutton);
        backbutton = (ImageButton) findViewById(R.id.simpleorderinfo_back);
        TextViewMap.put("orderid", (TextView) findViewById(R.id.simpleorderinfo_orderid));
        TextViewMap.put("touristid", (TextView) findViewById(R.id.simpleorderinfo_touristid));
        TextViewMap.put("begintime", (TextView) findViewById(R.id.simpleorderinfo_begintime));
        TextViewMap.put("senic", (TextView) findViewById(R.id.simpleorderinfo_scenic));
        TextViewMap.put("route", (TextView) findViewById(R.id.simpleorderinfo_route));
        TextViewMap.put("playtime", (TextView) findViewById(R.id.simpleorderinfo_palytime));
        TextViewMap.put("peoplenumber", (TextView) findViewById(R.id.simpleorderinfo_peoplenumber));
        TextViewMap.put("remark", (TextView) findViewById(R.id.simpleorderinfo_remark));
    }

    private void initview() {
        ensurebutton.setOnClickListener(this);
        cancelbutton.setOnClickListener(this);
        backbutton.setOnClickListener(this);
    }

    private void initdate() {
        TextViewMap.get("orderid").setText(order.getorderid());
        /*TextViewMap.get("touristid").setText(order.gettouristid());
        TextViewMap.get("begintime").setText("now");
        //TextViewMap.get("senic").setText(order.getsenic());
        TextViewMap.get("playtime").setText(order.getplaytime());
        TextViewMap.get("peoplenumber").setText(order.getPeoplenumbers());
        TextViewMap.get("remark").setText(order.getremark());*/
    }

    public void onClick(View v) {
        switch (v.getId()) {
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
            switch (msg.what) {
                case Order.QUERYORDER_SUCCESS:
                    String str = (String) msg.obj;
                    order = (Order) Json.JsontoObject(str, order.getClass());
                    initdate();
                    break;
                case Order.QUERYORDER_ERROR:
                    Toast.makeText(OrderSimpleInfoActivity.this, "订单信息加载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
