package com.stereogarage.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stereogarage.R;


/**
 * Created by Administor on 17/11/5.
 */

public class LeftMenuFragment extends Fragment {

    public TextView user_name1;
    public View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.main_menu,container,false);
        user_name1= (TextView) view.findViewById(R.id.user_name1);
        return view;
    }
//
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showtext(String name) {

      user_name1.setText(name);
    }


}


