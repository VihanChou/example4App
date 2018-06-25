package com.vihan.example4app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vihan.example4app.R;


public class Fragment2 extends Fragment
{
    private Button mBt_test;

    public Fragment2()
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
        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }


    @Override
    public void onAttach(Context context)
    {

        super.onAttach(context);
    }

    @Override
    public void onStart()
    {

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
