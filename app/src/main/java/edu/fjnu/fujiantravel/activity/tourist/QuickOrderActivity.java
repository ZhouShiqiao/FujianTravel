package edu.fjnu.fujiantravel.activity.tourist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.message.Json;
import edu.fjnu.fujiantravel.order.Order;
import edu.fjnu.fujiantravel.order.OrderThread;
import edu.fjnu.fujiantravel.scenic.Scenic;
import edu.fjnu.fujiantravel.user.Tourist;
import edu.fjnu.fujiantravel.user.User;

import java.util.HashMap;
import java.util.Map;

public class QuickOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private static QuickOrderActivity instance;

    private Toolbar toolbar;
    private TextView choosesenic;
    private TextView playtime;
    private TextView peoplenumber;
    private TextView begintime;
    private EditText remark;
    private Button button;

    private int numbers;
    private int time;
    private Scenic scenic;

    private EditText numbersedit;
    private EditText timeedit;

    private Order order;
    private User user;

    private String address;
    private int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_order);
        instance = this;
        initdata();
        findview();
        initview();
    }

    public static QuickOrderActivity getInstance(){
        return instance;
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

        numbersedit = new EditText(this);
        timeedit = new EditText(this);
        numbersedit.setInputType(InputType.TYPE_CLASS_NUMBER);
        timeedit.setInputType(InputType.TYPE_CLASS_NUMBER);
        numbersedit.setHint("请输入游玩的人数（单位：人）");
        timeedit.setHint("请输入游玩的时间（单位：小时）");
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
        begintime.setOnClickListener(this);
        playtime.setOnClickListener(this);
        peoplenumber.setOnClickListener(this);
    }

    private void initdata() {
        address = getString(R.string.server_address);
        port = Integer.parseInt(this.getString(R.string.server_port));
        this.scenic = null;
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
                if (scenic == null | time <= 0 | numbers <= 0) {
                    Toast.makeText(this, "订单信息不全，请填写完整！", Toast.LENGTH_SHORT).show();
                    return;
                }
                createorder();
                Thread thread = new Thread(new OrderThread(Order.CREATEORDER, order, address, port, QuickOrderActivity.this, handler));
                thread.start();
                break;
            case R.id.quickorder_choosesenic:
                intent.setClass(this, OrderSearchScenicActivity.class);
                startActivity(intent);
                break;
            case R.id.quickorder_begintime:
                break;
            case R.id.quickorder_playtime:
                timedialog();
                break;
            case R.id.quickorder_peoplenumber:
                numberdialog();
                break;
        }
    }


    private void updateorderinfo() {
        if (time > 0)
            playtime.setText(time + "小时");
        if (numbers > 0)
            peoplenumber.setText(numbers + "人");
        if (scenic != null)
            choosesenic.setText(scenic.getname());
    }

    private void createorder() {
        this.order = new Order();
        order.settouristid(user.getid());
        order.settype(0);
        order.setstate(1);
        order.setplaytime(time);
        order.setpeoplenumbers(numbers);
        order.setremark(remark.getText().toString());
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Order.CREATRORDER_SUCCESS:
                    Toast.makeText(QuickOrderActivity.this, "订单创建成功，等待导游接单。", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case Order.CREATRORDER_ERROR:
                    Toast.makeText(QuickOrderActivity.this, "订单创建失败。", Toast.LENGTH_SHORT).show();
                    break;
                case Scenic.QUERYSCENIC_SUCCESS:
                    scenic = (Scenic) msg.obj;
                    break;
            }
        }
    };

    private void numberdialog() {
        new AlertDialog.Builder(this).setTitle("游玩人数")
                .setIcon(R.drawable.friend)
                .setView(numbersedit)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int n = Integer.parseInt(numbersedit.getText().toString());
                        if (n <= 0) {
                            Toast.makeText(QuickOrderActivity.this, "游玩人数：非法的参数", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            numbers = n;
                            peoplenumber.setText(n + "人");
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void timedialog() {
        new AlertDialog.Builder(this).setTitle("游玩时间")
                .setIcon(R.drawable.countdown)
                .setView(timeedit)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int t = Integer.parseInt(timeedit.getText().toString());
                                if (t <= 0) {
                                    Toast.makeText(QuickOrderActivity.this, "游玩时间：非法的参数", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    time = t;
                                    playtime.setText(t + "小时");
                                }
                            }
                        }
                )
                .setNegativeButton("取消", null)
                .show();
    }

    public Handler getHandler() {
        return handler;
    }
}
