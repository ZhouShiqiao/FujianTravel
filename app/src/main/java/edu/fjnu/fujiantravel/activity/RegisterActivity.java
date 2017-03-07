package edu.fjnu.fujiantravel.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.fjnu.fujiantravel.message.Client;
import edu.fjnu.fujiantravel.message.Json;
import edu.fjnu.fujiantravel.message.MyMessage;
import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class RegisterActivity extends AppCompatActivity {
    private EditText id;
    private EditText passwd1;
    private EditText passwd2;
    private Button register;

    private DataOutputStream out = null;
    private DataInputStream in = null;
    private MyMessage msg = new MyMessage();
    private String JsonStr = null;

    private String address;
    private int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findview();
        initview();
        initdata();
    }

    private void initdata() {
        address = this.getString(R.string.server_address);
        port = Integer.parseInt(this.getString(R.string.server_port));
    }

    private void findview() {
        id = (EditText) findViewById(R.id.register_id);
        passwd1 = (EditText) findViewById(R.id.register_passwd1);
        passwd2 = (EditText) findViewById(R.id.register_passwd2);
        register = (Button) findViewById(R.id.register_button);
    }

    private void initview() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().length() <= 0 | passwd1.getText().length() <= 0 | passwd2.getText().length() <= 0) {
                    Toast toast = Toast.makeText(RegisterActivity.this, "信息不能为空！", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if (!passwd1.getText().toString().equals(passwd2.getText().toString())) {
                    Toast toast = Toast.makeText(RegisterActivity.this, "两次输入的密码不一样", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                new Thread() {
                    public void run() {
                        try {
                            Socket socket = new Socket(address, port);
                            out = new DataOutputStream(socket.getOutputStream());
                            in = new DataInputStream(socket.getInputStream());
                            msg.sethead(User.USERREGISTER);
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
                            msg.sethead(Client.CLINT_FINISH);
                            msg.setdetail(null);
                            out.writeUTF(Json.ObjecttoJson(msg));
                            out.flush();
                            in.close();
                            out.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //对接收到的新消息进行处理
            Toast toast;
            switch (msg.what) {
                case User.REGISTER_SUCCESS:
                    toast = Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                    break;
                case User.REGISTER_ERROR:
                    toast = Toast.makeText(RegisterActivity.this, "账号已存在！", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                default:
                    toast = Toast.makeText(RegisterActivity.this, "服务器异常！", Toast.LENGTH_SHORT);
                    toast.show();
            }
        }
    };
}
