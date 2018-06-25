package com.vihan.example4navigatbutton.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.vihan.example4navigatbutton.Fragments.Fragment1;
import com.vihan.example4navigatbutton.Fragments.Fragment2;
import com.vihan.example4navigatbutton.Fragments.Fragment3;
import com.vihan.example4navigatbutton.R;

public class MainActivity extends AppCompatActivity
{
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment[] fragments;
    private TextView mTextMessage;
    private int lastShowFragment = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    if (lastShowFragment != 0)
                    {
                        switchFrament(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if (lastShowFragment != 1)
                    {
                        switchFrament(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    if (lastShowFragment != 2)
                    {
                        switchFrament(lastShowFragment, 2);
                        lastShowFragment = 2;
                    }
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initFragments();
    }

    private void initFragments()
    {
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragments = new Fragment[]{fragment1, fragment2, fragment3};
        lastShowFragment = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment1)
                .show(fragment1)
                .commit();
    }

    /**
     * 切换Fragment
     *
     * @param lastIndex 上个显示Fragment的索引
     * @param index     需要显示的Fragment的索引
     */
    public void switchFrament(int lastIndex, int index)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded())
        {
            transaction.add(R.id.fragment_container, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

}
