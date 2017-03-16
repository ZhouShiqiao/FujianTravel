package edu.fjnu.fujiantravel.activity.tourist;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import edu.fjnu.fujiantravel.message.Client;
import edu.fjnu.fujiantravel.message.Json;
import edu.fjnu.fujiantravel.message.MyMessage;
import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.push.Utils;
import edu.fjnu.fujiantravel.user.Guide;
import edu.fjnu.fujiantravel.user.Tourist;
import edu.fjnu.fujiantravel.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TouristLogActivity extends AppCompatActivity implements Runnable {
    private EditText id;
    private EditText passwd;
    private Button log;

    private Socket socket = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;
    private MyMessage msg = new MyMessage();
    private String JsonStr = null;

    private String address;
    private int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_log);
        initdata();
        findview();
        initview();
    }

    private void initdata() {
        address = this.getString(R.string.server_address);
        port = Integer.parseInt(this.getString(R.string.server_port));
    }

    private void findview() {
        id = (EditText) findViewById(R.id.tourist_logid);
        passwd = (EditText) findViewById(R.id.tourist_logpasswd);
        log = (Button) findViewById(R.id.tourist_log);
    }

    private void initview() {
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().length() <= 0 || passwd.getText().length() <= 0) {
                    Toast toast = Toast.makeText(TouristLogActivity.this, "账号或密码为空!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                new Thread(TouristLogActivity.this).start();
            }
        });
    }

    public void run() {
        try {
            Socket socket = new Socket(address, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            msg.sethead(Tourist.TOURISTLOG);
            User user = new User();
            user.setid(id.getText().toString());
            user.setpasswd(id.getText().toString());
            msg.setdetail(Json.ObjecttoJson(user));
            JsonStr = Json.ObjecttoJson(msg);
            out.writeUTF(JsonStr);
            out.flush();
            JsonStr = in.readUTF();
            msg = (MyMessage) Json.JsontoObject(JsonStr, msg.getClass());
            Message message = new Message();
            message.what = msg.gethead();
            handler.sendMessage(message);
            MyMessage remsg = new MyMessage();
            remsg.sethead(Client.CLINT_FINISH);
            remsg.setdetail(null);
            out.writeUTF(Json.ObjecttoJson(remsg));
            out.flush();
            in.close();
            out.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //对接收到的新消息进行处理
            Toast toast = null;
            System.out.println(msg.what);
            switch (msg.what) {
                case Tourist.LOG_SUCCESS:
                    User user = new User();
                    String str = TouristLogActivity.this.msg.getdetail();
                    user = (User) Json.JsontoObject(str, user.getClass());
                    TouristActivity.whetherlog = true;
                    TouristActivity.user = user;
                    toast = Toast.makeText(TouristLogActivity.this, "登陆成功！", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                    break;
                case User.LOG_ERROR:
                    toast = Toast.makeText(TouristLogActivity.this, "账号密码错误！", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                default:
                    toast = Toast.makeText(TouristLogActivity.this, "服务器异常！", Toast.LENGTH_SHORT);
                    toast.show();
            }
        }
    };
}
