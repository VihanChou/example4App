package vihan.com.example.VihanViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import org.jetbrains.annotations.Nullable;

/**
 * Created by 周寒 on 2018/3/17.
 */
public class MessageDot extends View
{
//    
//   getTop：获取到的，是view自身的顶边到其父布局顶边的距离
//
//    getLeft：获取到的，是view自身的左边到其父布局左边的距离
//
//    getRight：获取到的，是view自身的右边到其父布局左边的距离
//
//    getBottom：获取到的，是view自身的底边到其父布局顶边的距离

    private Path mBezierPath = new Path();   //用于描述Bezier曲线的封闭路径，
    private float mX1 = 200, mY1 = 200;
    private float mX2 = 200, mY2 = 200;
    private int mRadius = 100;
    boolean mBezier = false;                 //是否需要画Bezier曲线的flag
    boolean mIsOverlength = false;

    //继承View的类，需要实现其构造方法
    //    带一个参数的构造方法
    public MessageDot(Context context)
    {
        super(context);
    }

    //    带两个参数的构造方法
    public MessageDot(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MessageDot(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public MessageDot(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


//     onMeasure方法用于测量我们定义的一个View的大小尺寸
//     这个重写方法中的两个int 类型的参数widthMeasureSpec和heightMeasureSpec 占用32个bit，Google 使用这个32位的数据的几段不同的位来表示不同的参数
//     包含的参数包括测量模式以及尺寸大小，获取方法
//     int widthMode = MeasureSpec.getMode(widthMeasureSpec);   get the measure mode of width
//     int widthsize = MeasureSpec.getSize(widthMeasureSpec);    get the size of width
//     Surely  the same way for your dear Heith
//
//     All the Measure mode follows
//     UNSPECIFIED	父容器没有对当前View有任何限制，当前View可以任意取尺寸
//     EXACTLY	当前的尺寸就是当前View应该取的尺寸
//     AT_MOST	当前尺寸是当前View能取的最大尺寸
//     而上面的测量模式跟我们的布局时的wrap_content、match_parent以及写成固定的尺寸有什么对应关系呢？
//
//     match_parent        —>EXACTLY。怎么理解呢？match_parent就是要利用父View给我们提供的所有剩余空间，而父View剩余空间是确定的，也就是这个测量模式的整数里面存放的尺寸。
//     wrap_content        —>AT_MOST。怎么理解：就是我们想要将大小设置为包裹我们的view内容，那么尺寸大小就是父View给我们作为参考的尺寸，只要不超过这个尺寸就可以啦，具体尺寸就根据我们的需求去设定。
//     固定尺寸（如100dp） —>EXACTLY。用户自己指定了尺寸大小，我们就不用再去干涉了，当然是以指定的大小为主啦。、
//     方法的两个参数的来源是XML中设置的  下面这两个属性来的
//     android:layout_width="100dp"
//     android:layout_height="100dp"


    //根据用户在Xml传入的值进行自己的方法判断通过setMeasuredDimension方法来设置View在显示上的实际大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(100, widthMeasureSpec);                //根据用户在XML中设置的值来确定实际的大小
        int height = getMySize(100, heightMeasureSpec);
        if (width < height)                                                        //这是设置View为正方形的方法过程，以min{width，heigth}作为长方形的边长
        {
            height = width;
        }
        else
        {
            width = height;
        }
        //这个函数接口传入实际的我们想要设置的View长宽的值,也就是传入的显示的值
        setMeasuredDimension(width, height);
    }


    //该方法会在View第一次初始化的时候被调用一次，如果想要在用户进行某些行为比如，触摸事件改变的时候，改变View的图案，可以反复调用该方法来达到目的
    //值得一提的是，进入该方法，却没有Draw出图案的这段时间，图像将被擦去等待执行到draw显示出新的图案
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        setBezierPath();                       //调用方法绘制出贝塞尔曲线的Path
        Paint paint = new Paint();             //新创建画笔，并使用方法setColor设置颜色
        paint.setColor(Color.BLUE);            //画笔的颜色
        paint.setAntiAlias(true);              //设置画出来的额图形没有锯齿
        //通过两个圆和贝塞尔区域的绘制，绘制出抽拉效果

        //只有当他们的距离没有超出距离的时候我们才绘画让他们显示出来
        //when the distans between two circle ,we make the paint dispear
        if (!mIsOverlength)
        {
            canvas.drawCircle(mX1, mY1, mRadius, paint);     //The first circle we need draw
            if (mBezier)
            {
                canvas.drawPath(mBezierPath, paint);         //The area of Bezier
                canvas.drawCircle(mX2, mY2, mRadius, paint);  //The area of the second circle 
            }
        }
    }

    //该方法会在用户该View上边的触摸事件改变时调用，以下是触摸事件改变的情况
//    【1】从，未点击到点击，
//    【2】从，点击的坐标A到坐标B
//    【3】从点击到未点击
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        System.out.println("ontach被调用");
        mX2 = event.getX();
        mY2 = event.getY();
        double temp = (Math.pow(mY2 - mY1, 2) + Math.pow(mX1 - mX2, 2));
        System.out.println("temp值" + temp);

        // 当两个圆的距离太大的时候
        if (temp > 274135)
        {
            System.out.println("temp> 274135");
            mIsOverlength = true;
            mX1 = event.getX();
            mY1 = event.getY();
        }
        if (temp <= 274135)
        {
            System.out.println("temp《 274135");
            mIsOverlength = false;
        }

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:    //用户初次点击
                mBezier = true;
                setBezierPath();
                break;
            case MotionEvent.ACTION_MOVE:   //用户将手放在View内部进行移动
                mBezier = true;
                setBezierPath();
                break;
            case MotionEvent.ACTION_UP:      //当手抬起
                mBezier = false;
                break;
        }
        invalidate();
//        nvalidate()是用来刷新View的，必须是在UI线程中进行工作。比如在修改某个view的显示时，调用invalidate()才能看到重新绘制的界面。invalidate()的调用是把之前的旧的view从主UI线程队列中pop掉。
        //调用invalidate或者postInvalidate方法 ，可以在绘制完Canvas对象时 调用这2个方法就可以自动调用onDraw,这样一来就可以对View的内容进行重新绘图更新视图
        return true;
    }

    //Function to get the size actually by it's mode
    private int getMySize(int defaultSize, int measureSpec)
    {
        int mySize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode)
        {
            case MeasureSpec.UNSPECIFIED:    //如果没有指定大小，就设置为默认大小
            {
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST:          //如果测量模式是最大取值为size， //我们将大小取最大值,也可以取其他值
            {
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY:         //如果是固定的大小，那就不要去改变它
            {
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    //Set the path of Bezier
    private void setBezierPath()
    {
        float Dx = (float) (mRadius * Math.sin(Math.atan((mY2 - mY1) / (mX2 - mX1))));
        float Dy = (float) (mRadius * Math.cos(Math.atan((mY2 - mY1) / (mX2 - mX1))));

        //calculate the position of four points
        float x1 = mX1 - Dx;
        float y1 = mY1 + Dy;

        float x2 = mX2 - Dx;
        float y2 = mY2 + Dy;

        float x3 = mX2 + Dx;
        float y3 = mY2 - Dy;

        float x4 = mX1 + Dx;
        float y4 = mY1 - Dy;

        mBezierPath.reset();                                                           //重置路径信息
        mBezierPath.moveTo(x1, y1);
        mBezierPath.quadTo((mX1 + mX2) / 2, (mY1 + mY2) / 2, x2, y2);
        mBezierPath.lineTo(x3, y3);
        mBezierPath.quadTo((mX1 + mX2) / 2, (mY1 + mY2) / 2, x4, y4);
        mBezierPath.lineTo(x1, y1);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}