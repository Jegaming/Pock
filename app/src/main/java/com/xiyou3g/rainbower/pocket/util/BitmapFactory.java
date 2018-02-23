package com.xiyou3g.rainbower.pocket.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

/**
 * Created by dell2014 on 2017/5/14.
 */

public class BitmapFactory {


    public static Bitmap drawable2Bitmap(int a, Context context) {
        Resources resources = context.getResources();
        Drawable drawable = resources.getDrawable(a);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    public Bitmap drawCircleView(Bitmap bitmap) {
        //这里可能需要调整一下图片的大小来让你的图片能在圆里面充分显示
        bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
        //构建一个位图对象，画布绘制出来的图片将会绘制到此bitmap对象上
        Bitmap bm = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        //构建一个画布,
        Canvas canvas = new Canvas(bm);
        //获得一个画笔对象，并设置为抗锯齿
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //获得一种渲染方式对象
        //BitmapShader的作用是使用一张位图作为纹理来对某一区域进行填充。
        //可以想象成在一块区域内铺瓷砖，只是这里的瓷砖是一张张位图而已。
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //设置画笔的渲染方式
        paint.setShader(shader);
        //通过画布的画圆方法将渲染后的图片绘制出来
        canvas.drawCircle(200, 200, 200, paint);
        //返回的就是一个圆形的bitmap对象
        return bm;
    }

}
