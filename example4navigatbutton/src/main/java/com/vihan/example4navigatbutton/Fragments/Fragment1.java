package com.vihan.example4navigatbutton.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vihan.example4navigatbutton.R;


public class Fragment1 extends Fragment
{

    private Button mBt_test;

    public Fragment1()
    {
        // 必需的空公共构造函数
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment1, container, false);
    }


    @Override
    public void onAttach(Context context)
    {

        super.onAttach(context);
    }

    @Override
    public void onStart()
    {
        mBt_test = getActivity().findViewById(R.id.bt_test);
        mBt_test.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                System.out.println("Sir I am alive!");
            }
        });
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        System.out.println("Sir I am dead!");
    }

}