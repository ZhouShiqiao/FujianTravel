package edu.fjnu.fujiantravel.activity.tourist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.order.Order;
import edu.fjnu.fujiantravel.order.OrderThread;
import edu.fjnu.fujiantravel.user.Tourist;
import edu.fjnu.fujiantravel.user.User;

import java.util.HashMap;
import java.util.Map;

public class QuickOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView choosesenic;
    private TextView playtime;
    private TextView peoplenumber;
    private TextView begintime;
    private EditText remark;
    private Button button;

    private int numbers;
    private int time;

    private Order order;
    private User user;

    private static Map<String, String> attribute;

    private String address;
    private int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_order);
        initdata();
        findview();
        initview();
    }

    protected void onRestart() {
        super.onRestart();
        this.updateorderinfo();
    }


    private void findview() {
        toolbar = (Toolbar) findViewById(R.id.quickorder_toolbar);
        choosesenic = (TextView) findViewById(R.id.quickorder_choosesenic);
        playtime = (TextView) findViewById(R.id.quickorder_playtime);
        peoplenumber = (TextView) findViewById(R.id.quickorder_peoplenumber);
        begintime = (TextView) findViewById(R.id.quickorder_begintime);
        remark = (EditText) findViewById(R.id.quickorder_remark);
        button = (Button) findViewById(R.id.quickorder_createorder);
    }

    private void initview() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(this);
        choosesenic.setOnClickListener(this);
        playtime.setOnClickListener(this);
        peoplenumber.setOnClickListener(this);
        begintime.setOnClickListener(this);
    }

    private void initdata() {
        address = getString(R.string.server_address);
        port = Integer.parseInt(this.getString(R.string.server_port));
        attribute = new HashMap<String, String>();
        attribute.put("senic", "");
        attribute.put("begintime", "now");
        this.numbers = 0;
        this.time = 0;
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.quickorder_createorder:
                if (TouristActivity.whetherlog)
                    this.user = TouristActivity.getUser();
                else {
                    Toast.makeText(this, "还没有登陆，请先登陆！", Toast.LENGTH_SHORT).show();
                    intent.setClass(this, TouristLogActivity.class);
                    startActivity(intent);
                    return;
                }
                createorder();
                Thread thread = new Thread(new OrderThread(Order.CREATEORDER, order, address, port, QuickOrderActivity.this));
                thread.start();
                break;
            case R.id.quickorder_choosesenic:
                intent.setClass(this, OrderSearchScenicActivity.class);
                startActivity(intent);
                break;
            case R.id.quickorder_playtime:
                break;
            case R.id.quickorder_begintime:
                break;
            case R.id.quickorder_peoplenumber:
                break;
        }
    }

    public static void setorderinfo(String key, String value) {
        attribute.put(key, value);
    }

    private void updateorderinfo() {
        if (time > 0)
            playtime.setText(time + "小时");
        if (numbers > 0)
            peoplenumber.setText(numbers + "人");
        if (attribute.get("begintime").toString().equals("now"))
            begintime.setText("立即游玩");
        else
            begintime.setText(attribute.get("begintime"));
        if (attribute.get("senic") == null || attribute.get("senic").toString().length() <= 0)
            choosesenic.setText("请选择景点");
        else
            choosesenic.setText(attribute.get("senic"));
    }

    private void createorder() {
        this.order = new Order();
        order.setorderid("1");
        order.settouristid(user.getid());
        order.settype(0);
        order.setstate(1);
        order.setplaytime(time);
        order.setpeoplenumbers(numbers);
        order.setremark(remark.getText().toString());
    }

}
