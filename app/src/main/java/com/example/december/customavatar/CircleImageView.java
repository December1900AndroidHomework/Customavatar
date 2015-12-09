package com.example.december.customavatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.jar.Attributes;

public class CircleImageView extends ImageView {
    private int getDefalutWidth = 100;
    private int getDefalutHeight = 100;
    private Paint paint;
    public CircleImageView(Context context,AttributeSet attrs) {
        super(context,attrs);
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
                if(widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST){
                    widthSize = (int)(100 * getResources().getDisplayMetrics().density);
                    heightSize = (int)(100 * getResources().getDisplayMetrics().density);
                }
                setMeasuredDimension(widthSize, heightSize);
    }
       @Override
    protected void onDraw(Canvas canvas) {
           Drawable drawable = getDrawable();
           if (drawable == null) {
           return;
           }
           if(getWidth() == 0 || getHeight() == 0) {
               return;
           }
           this.measure(0,0);
           if (drawable.getClass() == NinePatchDrawable.class)
               return;
           Bitmap b = ((BitmapDrawable) drawable).getBitmap();
           //根据上个位图b的大小产生一个新位图bitmap，根据指定的结构设置新位图的结构
           Bitmap bitmap =b.copy(Bitmap.Config.ARGB_8888,true);
           getDefalutWidth = getWidth();
           getDefalutHeight = getHeight();
           //计算显示圆形的半径
           int radius = (getDefalutWidth < getDefalutHeight ? getDefalutWidth : getDefalutHeight) / 2 ;
           //获取处理后的圆形图片
           Bitmap roundBitmap

       }

}