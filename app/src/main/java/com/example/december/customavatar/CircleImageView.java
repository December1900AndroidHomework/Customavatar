package com.example.december.customavatar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.gesture.GestureOverlayView;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.jar.Attributes;
public class CircleImageView extends ImageView {
    private int getDefalutWidth = 100;
    private int getDefalutHeight = 100;
    private Paint paint;

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        //设置抗锯齿,设置位图进行滤波处理,设置递色
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
    }

    //重写OnMeasure方法,得到View最终视图的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //子视图的大小最多是specSize中指定的值
        if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST) {
            widthSize = (int) (100 * getResources().getDisplayMetrics().density);
            heightSize = (int) (100 * getResources().getDisplayMetrics().density);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        this.measure(0, 0);
        if (drawable.getClass() == NinePatchDrawable.class)
            return;
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        //根据上个位图b的大小产生一个新位图bitmap，根据指定的结构设置新位图的结构
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        getDefalutWidth = getWidth();
        getDefalutHeight = getHeight();
        //计算显示圆形的半径
        int radius = (getDefalutWidth < getDefalutHeight ? getDefalutWidth : getDefalutHeight) / 2;
        //获取处理后的圆形图片
        Bitmap roundBitmap = getCroppedRoundBitmap(bitmap, radius);
        //绘制图片进行显示
        canvas.drawBitmap(roundBitmap, getDefalutWidth / 2 - radius, getDefalutHeight / 2 - radius, null);
    }

    /**
     * 获取剪裁后的圆形图片
     * radius 半径
     */
    public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
        Bitmap scaledSrcBmp;
        int diameter = radius * 2;
        //对图片进行处理，获取我们需要的中央部分
        Bitmap squareBitmap = getCenterBitmap(bmp);
        //将图片缩放到需要的圆形比例大小
        if (squareBitmap.getWidth() != diameter || squareBitmap.getHeight() != diameter) {
            scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);
        } else {
            scaledSrcBmp = squareBitmap;
        }
        //创建一个我们输出的对应
        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
                scaledSrcBmp.getHeight(),
                Bitmap.Config.ARGB_8888);
        //在output上进行绘画
        Canvas canvas = new Canvas(output);
        //创建裁剪的矩形
        Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());
        //绘制dest目标区域
        canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
                scaledSrcBmp.getHeight() / 2,
                scaledSrcBmp.getWidth() / 2,
                paint);
        //设置Xfermode模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制src源区域
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
        bmp.recycle();
        squareBitmap.recycle();
        scaledSrcBmp.recycle();
        return output;

    }

    /**
     * 截取图片
     */
    private Bitmap getCenterBitmap(Bitmap bitmap) {
        return bitmap;
    }

            private Context mContext;
            private int defaultColor = 0xFFFFFFFF;
            // 边框的颜色
            private int mBordeColor = 0;
            //边框的宽度
            private int mBorderWidth = 0;

            /*
             * 获取自定义属性
             */
            private void setCustomAttributes(AttributeSet attrs) {
                TypedArray typeArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
                mBorderWidth = typeArray.getDimensionPixelSize(R.styleable.CircleImageView_border_size, 0);
                mBordeColor = typeArray.getColor(R.styleable.CircleImageView_border_color, defaultColor);
                typeArray.recycle();
            }

            int radiusNomal = (getDefalutHeight < getDefalutHeight ? getDefalutWidth : getDefalutWidth) / 2;
            //去掉边框的宽度后，图片展示的半径
            int radius = radiusNomal - mBorderWidth / 2;
            //绘制边框的半径
            int radiusBorder = radius;


            /**
             * 边缘画圆
             */
            private void drawCircleBorder(Canvas canvas, int radius, int color) {
                Paint paint = new Paint();
            /* 去锯齿 */
                paint.setAntiAlias(true);
                paint.setFilterBitmap(true);
                paint.setDither(true);
                paint.setColor(color);
            /* 设置paint的　style　为STROKE：空心 */
                paint.setStyle(Paint.Style.STROKE);
            /* 设置paint的外框宽度 */
                paint.setStrokeWidth(mBorderWidth);
                canvas.drawCircle(getDefalutWidth / 2, getDefalutHeight / 2, radius, paint);
            }
        }
