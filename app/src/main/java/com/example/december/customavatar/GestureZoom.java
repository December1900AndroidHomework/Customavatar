package com.example.december.customavatar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by december on 15/12/23.
 */
public class GestureZoom extends Activity implements GestureDetector.OnGestureListener {
    GestureDetector detector;
    ImageView imageView;
    Bitmap bitmap;
    int width,height;
    float currentScale = 1;
    Matrix matrix;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_circle_image_view);
        //创建手势检测器
        detector = new GestureDetector(this,this);
        imageView = (ImageView) findViewById(R.id.show);
        matrix = new Matrix();
        bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.show);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        //初始化图片
        imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.show));
    }
    @Override
    public boolean onTouchEvent(MotionEvent me)
    {
        return detector.onTouchEvent(me);
    }
    @Override
    public boolean onFling(MotionEvent event1,MotionEvent event2,float velocityX,float velocityY)
    {
      velocityX = velocityX > 4000 ? 4000 : velocityX;
      velocityX = velocityX < -4000 ? -4000 : velocityX;
        //根据手速来计算缩放比
        currentScale += currentScale * velocityX / 4000.0f;
        currentScale = currentScale > 0.01 ? currentScale: 0.01f;
        matrix.reset();
        matrix.setScale(currentScale, currentScale, 160, 200);
        BitmapDrawable tmp = (BitmapDrawable) imageView.getDrawable();
        //如果图片还未回收，先强直回收该图片
        if (!tmp.getBitmap().isRecycled())
        {
            tmp.getBitmap().recycle();
        }
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        imageView.setImageBitmap(bitmap2);
        return true;
    }
    @Override
    public boolean onDown(MotionEvent agr0)
    {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent event)
    {
    }
    @Override
    public boolean onScroll(MotionEvent event1 , MotionEvent event2, float distanceX, float distanceY)
    {
        return false;
    }
    @Override
    public void onShowPress(MotionEvent event)
    {
    }
    @Override
    public boolean onSingleTapUp(MotionEvent event)
    {
        return false;
    }
}
