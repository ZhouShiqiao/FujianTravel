package edu.fjnu.fujiantravel.activity;

import android.content.Intent;
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
import edu.fjnu.fujiantravel.activity.tourist.TouristActivity;
import edu.fjnu.fujiantravel.message.Json;
import edu.fjnu.fujiantravel.order.Order;
import edu.fjnu.fujiantravel.order.OrderThread;
import edu.fjnu.fujiantravel.order.OrderUpdate;

public class OrderSimpleInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button ensurebutton;
    private Button cancelbutton;
    private ImageButton backbutton;

    private TextView orderidtext;
    private TextView touristidtext;
    private TextView begintimetext;
    private TextView scenictext;
    private TextView routetext;
    private TextView playtimetext;
    private TextView peoplenumbertext;
    private TextView remarktext;

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
        orderidtext =(TextView)findViewById(R.id.simpleorderinfo_orderid);
        touristidtext=(TextView)findViewById(R.id.simpleorderinfo_touristid);
        begintimetext=(TextView)findViewById(R.id.simpleorderinfo_begintime);
        scenictext=(TextView)findViewById(R.id.simpleorderinfo_scenic);
        routetext=(TextView)findViewById(R.id.simpleorderinfo_route);
        playtimetext=(TextView)findViewById(R.id.simpleorderinfo_playtime);
        peoplenumbertext=(TextView)findViewById(R.id.simpleorderinfo_peoplenumber);
        remarktext=(TextView)findViewById(R.id.simpleorderinfo_remark);
    }

    private void initview() {
        ensurebutton.setOnClickListener(this);
        cancelbutton.setOnClickListener(this);
        backbutton.setOnClickListener(this);
    }

    private void initdate() {
        orderidtext.setText(order.getorderid());
        touristidtext.setText(order.gettouristid());
        playtimetext.setText(order.getplaytime()+"小时");
        peoplenumbertext.setText(order.getPeoplenumbers()+"人");
        remarktext.setText(order.getremark());
        scenictext.setText(order.getsenic());
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
                OrderUpdate update = new OrderUpdate();
                update.setorderID(id);
                update.sethead("guideID");
                update.setdetail(TouristActivity.getUser().getid());
                Thread thread = new Thread(new OrderThread(Order.RECEIVEORDER, update,
                        getString(R.string.server_address), Integer.parseInt(getString(R.string.server_port)),
                        OrderSimpleInfoActivity.this, handler));
                thread.start();
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
                case Order.UPDATEORDRE_SUCCESS:
                    Toast.makeText(OrderSimpleInfoActivity.this, "接单成功请与游客联系", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setClass(OrderSimpleInfoActivity.this,SimpleUserInfoActivity.class);
                    intent.putExtra("id", order.gettouristid());
                    startActivity(intent);
                    finish();
                    break;
                case Order.UPDATEORDER_ERROR:
                    Toast.makeText(OrderSimpleInfoActivity.this, "接单失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}

