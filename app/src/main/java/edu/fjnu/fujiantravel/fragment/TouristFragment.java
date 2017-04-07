package edu.fjnu.fujiantravel.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.activity.RegisterActivity;
import edu.fjnu.fujiantravel.activity.tourist.TouristActivity;
import edu.fjnu.fujiantravel.activity.tourist.TouristLogActivity;
import edu.fjnu.fujiantravel.activity.tourist.TouristOrderActivity;
import edu.fjnu.fujiantravel.user.Tourist;

/**
 * Created by Administrator on 2017/1/22 0022.
 */
public class TouristFragment extends Fragment implements View.OnClickListener {

    private View view = null;
    private Context context = null;

    private RelativeLayout loglayout;
    private RelativeLayout layout;
    private Button log;
    private Button register;

    private ImageView headportrait;
    private TextView username;
    private TextView userid;
    private ImageButton QRcode;

    private LinearLayout order;
    private LinearLayout collect;
    private LinearLayout setting;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.tourist_fragment, container, false);
        findview();
        initview();
        return view;
    }

    private void findview() {
        loglayout = (RelativeLayout) view.findViewById(R.id.tourist_loglayout);
        layout = (RelativeLayout) view.findViewById(R.id.tourist_layout);
        log = (Button) view.findViewById(R.id.tourist_logbutton);
        register = (Button) view.findViewById(R.id.tourist_registerbutton);

        headportrait = (ImageView) view.findViewById(R.id.tourist_image);
        username = (TextView) view.findViewById(R.id.tourist_username);
        userid = (TextView) view.findViewById(R.id.tourist_userid);
        QRcode = (ImageButton) view.findViewById(R.id.tourist_QRcode);

        order=(LinearLayout)view.findViewById(R.id.tourist_order);
        collect=(LinearLayout)view.findViewById(R.id.tourist_collect);
        setting=(LinearLayout)view.findViewById(R.id.tourist_setting);
    }

    private void initview() {
        log.setOnClickListener(this);
        register.setOnClickListener(this);
        order.setOnClickListener(this);
        collect.setOnClickListener(this);
        setting.setOnClickListener(this);
        whetherlog();
    }

    private void whetherlog() {
        if (!TouristActivity.whetherlog) {
            loglayout.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
        } else {
            loglayout.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            userlog();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        whetherlog();
    }

    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent();
        switch (id) {
            case R.id.tourist_logbutton:
                intent.setClass(context, TouristLogActivity.class);
                startActivity(intent);
                break;
            case R.id.tourist_registerbutton:
                intent.setClass(context, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tourist_order:
                intent.setClass(context, TouristOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.tourist_collect:
                break;
            case R.id.tourist_setting:
                break;
        }
    }

    private void userlog() {
        userid.setText(TouristActivity.getUser().getid());
        username.setText(TouristActivity.getUser().getid());
    }

}
