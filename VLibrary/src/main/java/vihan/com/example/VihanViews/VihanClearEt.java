package vihan.com.example.VihanViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import vihan.com.example.R;


/**
 * Created by 周寒 on 2018/3/24.
 */

@SuppressLint("AppCompatCustomView")
public class VihanClearEt extends EditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher
{
    private Drawable mClearer;
    private OnTouchListener mOnTouchListener;
    private OnFocusChangeListener mOnFocusChangeListener;


    //自定义控件类的构造方法
    public VihanClearEt(Context context)
    {
        super(context);
        init(context);
    }

    public VihanClearEt(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public VihanClearEt(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context)
    {
        mClearer = ContextCompat.getDrawable(context, R.drawable.ic_close);
        mClearer.setBounds(0, 0, mClearer.getIntrinsicHeight(), mClearer.getIntrinsicWidth());
        setClearerVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    public void setClearerVisible(boolean clearerVisible)
    {
        mClearer.setVisible(clearerVisible, false);
        //官方为我们提供了TextView中的setCompoundDrawables方法，让我们可以在文本的旁边添加一个图片 , TextView_drawableRight
        Drawable drawableNull = null;
        this.setCompoundDrawables(drawableNull, drawableNull, clearerVisible ? mClearer : null, drawableNull);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        if (isFocused())
        {
            setClearerVisible(s.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        if (hasFocus)
        {
            setClearerVisible(this.getText().length() > 0);
        }
        else
        {
            setClearerVisible(false);
        }
        //TODO
        if (mOnFocusChangeListener != null)
        {
            mOnFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        final int x = (int) event.getX();
        //当Clear控件可见，并且点击范围在clear上的时候，清除EditText中的内容
        if (mClearer.isVisible() && x > getWidth() - getPaddingRight() - mClearer.getIntrinsicWidth())
        {
            if (event.getAction() == MotionEvent.ACTION_UP)
            {
                setText("");
            }
            return true;
        }
        return mOnTouchListener != null && mOnTouchListener.onTouch(v, event);
    }
}
