package vihan.com.example.VihanViews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.Nullable;

/**
 * Created by 周寒 on 2018/3/24.
 */

public class RingProgress extends View
{
    private int mCurrentValue = 0;
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private float mWidth = 60;
    private int mIncClor[];
    private int mOutColor[];
    private int[] mProgress;
    private RectF mOval;
    private boolean mIsBackrpund = false;

    public RingProgress(Context context)
    {
        super(context);
    }

    public RingProgress(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

    }

    public RingProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

    }

    public RingProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(dip2px(getContext(), 260), dip2px(getContext(), 260));

    }

    public void RingProgressSet(int[] outRingColor, int[] inRingColor, int[] progress, boolean isBackrpund)
    {
        mIncClor = inRingColor;
        mOutColor = outRingColor;
        mProgress = progress;
        mIsBackrpund = isBackrpund;
    }

    @Override
    public void draw(final Canvas canvas)
    {
        super.draw(canvas);
        float point[] = {30, dip2px(getContext(), 260) - 30};

        for (int i = 0; i < mOutColor.length; i++)
        {
            drawSingleRing(canvas, point, mProgress[i], mOutColor[i], mIncClor[i]);
            point[0] = point[0] + 60;
            point[1] = point[1] - 60;
        }
    }


    private void drawSingleRing(Canvas canvas, float twoPint[], int progress, int outColor, int inColor)
    {
        if (progress > mCurrentValue)
        {
            progress = mCurrentValue;
        }
        mPaint.setStrokeWidth(mWidth);
        mPaint.setColor(outColor);
        mPaint.setAlpha(145);                     //f放在set color 后边才有效
        mPaint.setStyle(Paint.Style.STROKE);    //绘画路径，如果不设置，则默认绘画闭合区域,描绘笔画而不是区域
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);    //设置线条绘制下起始处圆角还是平角
        mOval = new RectF(twoPint[0], twoPint[0], twoPint[1], twoPint[1]);

        mPath.reset();
        if (mIsBackrpund)
        {
            mPath.arcTo(mOval, 0, mCurrentValue - 1, false);
        }
        else
        {
            mPath.arcTo(mOval, 0, progress, false);
        }
        canvas.drawPath(mPath, mPaint);

        mPaint.setStrokeWidth(mWidth - 10);
        mPaint.setColor(inColor);//0000FF;
        mPaint.setAlpha(200);
        mPath.reset();
        mPath.arcTo(mOval, 0, progress, false);
        canvas.drawPath(mPath, mPaint);

    }

    public void ringProgreesShow()
    {
        anim();
    }

    public void anim()
    {
        ValueAnimator anim = ValueAnimator.ofInt(0, 360);

        //设置动画的播放各种属性
        // 设置动画运行的时长
        anim.setDuration(1000);
        // 设置动画延迟播放时间
        anim.setStartDelay(500);
        // 设置动画重复播放次数 = 重放次数+1
        // 动画播放次数 = infinite时,动画无限重复
        anim.setRepeatCount(0);
        // 设置重复播放动画模式
        // ValueAnimator.RESTART(默认):正序重放
        // ValueAnimator.REVERSE:倒序回放
        anim.setRepeatMode(ValueAnimator.RESTART);
        //将改变的值手动赋值给对象的属性值：通过动画的更新监听器
        // 设置 值的更新监听器
        // 值每次改变、变化一次,该方法就会被调用一次
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                System.out.println(" before change ----------------" + mCurrentValue);
                // 获得改变后的值
                mCurrentValue = (Integer) animation.getAnimatedValue();
                // 步骤4：将改变后的值赋给对象的属性值，下面会详细说明
                System.out.println(" afterchange----------------" + mCurrentValue);
                invalidate();
            }
        });
        anim.start();
        // 启动动画
    }



    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
