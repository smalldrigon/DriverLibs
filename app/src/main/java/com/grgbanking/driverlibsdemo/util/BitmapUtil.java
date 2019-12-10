package com.grgbanking.driverlibsdemo.util;

import android.content.Context;
import android.graphics.*;
import android.widget.CalendarView;
import com.grgbanking.driverlibsdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: gongxiaobiao
 * Date: on 2019/11/28 9:03
 * Email: 904430803@qq.com
 * Description:
 */
public class BitmapUtil {

    public static Bitmap createBitmap(List<String> data){
// 		new BitmapDrawable(mContext.getApplicationContext(),Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888)));
//Bitmap colorbitmap = new ColorDrawable(Color.WHITE);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(20);
         int newBitmapHeight = 80;
        for (String str:data){
          newBitmapHeight+=calcTextHeight(paint,str).get(1)+10;
        }

        Bitmap oldBitmap =createEmptyBitmap(348,newBitmapHeight);

       Bitmap newBitmap =  Bitmap.createBitmap(oldBitmap);//Bitmap.createBitmap(oldBitmap.getWidth(),newBitmapHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(newBitmap);
//        canvas.drawBitmap(oldBitmap,0,0,null);



//水印的位置坐标
        for (int i=0;i<data.size();i++){
//            if (i==0){
                canvas.drawText(data.get(i), 20,50+(calcTextHeight(paint,data.get(i)).get(1)+10)*(i),paint);

//            }else{
//                canvas.drawText(data.get(i), 20,20*i+(calcTextHeight(paint,data.get(i)).get(1))*(i+1 ),paint);

//            }
        }

        canvas.save();
        canvas.restore();


        return newBitmap;
    }

    private static List<Integer> calcTextHeight(Paint paint,String text){
        Rect rec  = new Rect();
        if (paint!=null){
            List<Integer>ls = new ArrayList<>();
                  paint.getTextBounds(text,0,text.length(),rec);
                  ls.add(rec.width());
                  ls.add(rec.height());
                  return ls;
        }else{
              new NullPointerException("paint must not null");
            return null;
        }

    }

    public static Bitmap createEmptyBitmap(int width,int heigh){
        Bitmap bitmap = Bitmap.createBitmap(width,heigh, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(20);
//        canvas.drawRect(0,0,width,heigh,paint);
//        canvas.save();
//        canvas.restore();


        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        Bitmap newbitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), bitmap.getConfig());

        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return bitmap;

    }

}
