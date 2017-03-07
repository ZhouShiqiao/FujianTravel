package edu.fjnu.fujiantravel.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.activity.RegisterActivity;
import edu.fjnu.fujiantravel.activity.tourist.TouristActivity;
import edu.fjnu.fujiantravel.activity.tourist.TouristLogActivity;

/**
 * Created by Administrator on 2017/1/22 0022.
 */
public class TouristFragment extends Fragment implements View.OnClickListener{

    private View view = null;
    private Context context = null;

    private RelativeLayout loglayout;
    private RelativeLayout layout;
    private TextView username;
    private Button log;
    private Button register;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.tourist_fragment, container, false);
        findview();
        initview();
        return view;
    }

    private void findview(){
        loglayout=(RelativeLayout)view.findViewById(R.id.tourist_loglayout);
        layout=(RelativeLayout)view.findViewById(R.id.tourist_layout);
        username=(TextView)view.findViewById(R.id.username);
        log=(Button)view.findViewById(R.id.tourist_logbutton);
        register=(Button)view.findViewById(R.id.tourist_registerbutton);
    }
    private void initview(){
        log.setOnClickListener(this);
        register.setOnClickListener(this);
        whetherlog();
    }
    private void whetherlog(){
        if(!TouristActivity.whetherlog){
            loglayout.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
        }
        else{
            loglayout.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            username.setText(TouristActivity.user.getid());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        whetherlog();
    }

    public void onClick(View v){
        int id = v.getId();
        Intent intent = new Intent();
        switch (id){
            case R.id.tourist_logbutton:
                intent.setClass(context,TouristLogActivity.class);
                startActivity(intent);
                break;
            case R.id.tourist_registerbutton:
                intent.setClass(context,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
