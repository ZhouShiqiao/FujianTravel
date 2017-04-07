package edu.fjnu.fujiantravel.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.user.User;

public class SimpleUserInfoActivity extends AppCompatActivity {
    private TextView userid;
    private TextView name;
    private TextView telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_user_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String str = bundle.getString("id");
        findview();
        initview();
        userid.setText(str);
    }

    public void findview() {
        userid = (TextView) findViewById(R.id.simpleuserinfo_userid);
        name = (TextView) findViewById(R.id.simpleuserinfo_name);
        telephone = (TextView) findViewById(R.id.simpleuserinfo_telephonenumber);
    }

    public void initview() {

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case User.QUERY_SUCCESS:

                    break;
                case User.QUERY_ERROR:
                    Toast.makeText(SimpleUserInfoActivity.this, "用户信息加载失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
