package com.example.wentongwang.myfloatingbutton;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Wentong WANG on 2016/5/5.
 */
public class MyFragment extends Fragment {
    private String myTitle;
    private TextView mtv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_layout,container,false);
        mtv = (TextView) root.findViewById(R.id.tv);
        mtv.setText(myTitle);
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void setTitle(String title){
        myTitle = title;
    }
}
