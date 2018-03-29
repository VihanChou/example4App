package vihan.com.example;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import vihan.com.example.VihanViews.RingProgress;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RingProgress RingProgress = findViewById(R.id.RingProgress);
        int t[] = {0x000000, 0x333333, 0x777777, 0xaaaaaa, 0xdddddd};
        int w[] = {Color.rgb(235, 79, 56), Color.rgb(17, 205, 110), Color.rgb(234, 128, 16), Color.rgb(86, 171, 228), Color.rgb(157, 85, 184)};
        int p[] = {285, 234, 127, 80, 23};
        RingProgress.RingProgressSet(t, w, p, true);
        RingProgress.ringProgreesShow();
    }
}
