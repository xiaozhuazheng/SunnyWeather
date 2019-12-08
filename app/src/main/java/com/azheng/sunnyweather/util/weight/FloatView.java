package com.azheng.sunnyweather.util.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.azheng.sunnyweather.R;

public class FloatView extends View {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mRadius;

    //是在java代码创建视图的时候被调用，如果是从xml填充的视图，就不会调用这个，在代码里new的话一般用一个参数的
    public FloatView(Context context) {
        this(context,null);
    }

    //这个是在xml创建但是没有指定style的时候被调用
    public FloatView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    //这个是在xml创建，指定style的时候被调用
    public FloatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        // 设置paint为无锯齿
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(0xCC);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mRadius = mWidth/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(getResources().getColor(R.color.blue));
        canvas.drawCircle(mWidth/2,mHeight/2,mRadius,mPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
        canvas.drawLine(mWidth/2,mRadius/2,mWidth/2,mHeight - mRadius/2,mPaint);
        canvas.drawLine(mRadius/2,mHeight/2,mWidth -mRadius/2 ,mHeight/2,mPaint);
    }
}
