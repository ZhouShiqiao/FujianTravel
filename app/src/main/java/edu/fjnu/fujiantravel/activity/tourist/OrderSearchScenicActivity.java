package edu.fjnu.fujiantravel.activity.tourist;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.message.Json;
import edu.fjnu.fujiantravel.scenic.Scenic;
import edu.fjnu.fujiantravel.scenic.ScenicList;
import edu.fjnu.fujiantravel.scenic.ScenicThread;

public class OrderSearchScenicActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private EditText edittext;
    private TextView textview;
    private ListView listview;

    private List<Scenic> scenicslist;
    private ArrayList<Scenic> mSearchList = new ArrayList<Scenic>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_search_scenic);
        findview();
        initview();
        new Thread(new ScenicThread(Scenic.QUERYSCENUCLIST, getString(R.string.server_address),
                Integer.parseInt(getString(R.string.server_port)), this, handler)).start();
    }

    public void findview() {
        button = (Button) findViewById(R.id.ordersenic_location);
        edittext = (EditText) findViewById(R.id.ordersenic_search);
        textview = (TextView) findViewById(R.id.ordersenic_cancel);
        listview = (ListView) findViewById(R.id.ordersenic_seniclist);
    }

    public void initview() {
        textview.setOnClickListener(this);
        button.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = new Message();
                message.what = Scenic.QUERYSCENIC_SUCCESS;
                if (mSearchList.size() <= 0) {
                    message.obj = scenicslist.get(position);
                } else {
                    message.obj = mSearchList.get(position);
                }
                QuickOrderActivity.getInstance().getHandler().sendMessage(message);
                finish();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ordersenic_cancel:
                finish();
                break;
            case R.id.ordersenic_location:
                Intent intent = new Intent();
                intent.setClass(this, ChooseLocationActivity.class);
                startActivity(intent);
                break;
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Scenic.QUERYLIST_SUCCESS:
                    String str = (String) msg.obj;
                    ScenicList sl = new ScenicList();
                    sl = (ScenicList) Json.JsontoObject(str, sl.getClass());
                    scenicslist = sl.getlist();
                    initlist();
                    break;
            }
        }
    };

    private void initlist() {
        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < scenicslist.size(); i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("name", scenicslist.get(i).getname());
            listem.put("address", scenicslist.get(i).toaddress());
            listem.put("no", scenicslist.get(i).getscenicid());
            listems.add(listem);
        }
        SimpleAdapter simplead = new SimpleAdapter(this, listems,
                R.layout.search_scenic_item, new String[]{"name", "address", "no"},
                new int[]{R.id.search_scenic_name, R.id.search_scenic_address, R.id.search_scenic_no});
        listview.setAdapter(simplead);

        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    listview.setVisibility(View.VISIBLE);
                    updateLayout(searchItem(s.toString()));
                } else {
                    //listview.setVisibility(View.GONE);
                }
            }
        });
    }

    public List<Scenic> searchItem(String name) {
            mSearchList.clear();
            for (int i = 0; i < scenicslist.size(); i++) {
                int index = scenicslist.get(i).getname().indexOf(name);
                // 存在匹配的数据
                if (index != -1) {
                    mSearchList.add(scenicslist.get(i));
                }
            }
            return mSearchList;
        }

    public void updateLayout(List<Scenic> list) {
        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("name", list.get(i).getname());
            listem.put("address", list.get(i).toaddress());
            listem.put("no", list.get(i).getscenicid());
            listems.add(listem);
        }
        SimpleAdapter simplead = new SimpleAdapter(this, listems,
                R.layout.search_scenic_item, new String[]{"name", "address", "no"},
                new int[]{R.id.search_scenic_name, R.id.search_scenic_address, R.id.search_scenic_no});
        listview.setAdapter(simplead);
    }
}
