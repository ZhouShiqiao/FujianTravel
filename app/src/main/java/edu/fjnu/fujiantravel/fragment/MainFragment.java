package edu.fjnu.fujiantravel.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.BatchUpdateException;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.activity.ChooseActivity;
import edu.fjnu.fujiantravel.activity.tourist.MakeOrderActivity;
import edu.fjnu.fujiantravel.activity.tourist.QuickOrderActivity;
import edu.fjnu.fujiantravel.activity.tourist.TouristActivity;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Context context;

    private WebView webView;
    private ImageButton changebutton;
    private ImageButton infobutton;
    private TextView searchView;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.main_fragment, container, false);
        findview();
        initview();
        return view;
    }

    private void findview() {
        webView = (WebView) view.findViewById(R.id.tourist_mainweb);
        changebutton = (ImageButton) view.findViewById(R.id.modelchangebutton);
        infobutton = (ImageButton) view.findViewById(R.id.tourist_messagebutton);
        searchView = (TextView) view.findViewById(R.id.tourist_search);
        button1 = (Button) view.findViewById(R.id.tourist_button1);
        button2 = (Button) view.findViewById(R.id.tourist_button2);
        button3 = (Button) view.findViewById(R.id.tourist_button3);
        button4 = (Button) view.findViewById(R.id.tourist_button4);
    }

    private void initview() {
        changebutton.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.modelchangebutton:
                intent.setClass(context, ChooseActivity.class);
                getActivity().finish();
                break;
            case R.id.tourist_button3:
                intent.setClass(context, MakeOrderActivity.class);
                context.startActivity(intent);
                break;
            case R.id.tourist_button4:
                if (TouristActivity.whetherlog()) {
                    intent.setClass(context, QuickOrderActivity.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "还没有登陆，请先登陆！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
