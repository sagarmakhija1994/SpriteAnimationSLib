package com.sagarmakhija.spriteanimationslib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class animator {
    String[] superImagesArray;
    int superRepeat = -10;
    CountDownTimer T;
    public void animateSprite(int framesFrom, int totalFrames, String fileName, int fps, int repeat, ImageView imgView, Activity activity, int requestCode){
        String[] imgArray = spriteArray(framesFrom,totalFrames,fileName);
        superImagesArray = imgArray;
        superRepeat = repeat;
        playanimation (superRepeat,superImagesArray,fps,imgView,activity,requestCode);
    }
    public String[] spriteArray(int framesFrom, int frames, String fileName){
        String[] imgArray = new String[frames];
        for(int i=0;i<frames;i++) {
            imgArray[i] = "@drawable/"+fileName+(i+framesFrom);
            Log.e("" + i, "" + imgArray[i]);
        }
        return imgArray;
    }

    public void playanimation(final int repeat, final String[] imagesArray, final int speedPerFrameInMiliSec, final ImageView img, final Activity activity, final int requestCode){
        //(repeat = -10) unlimited loops
        T = new CountDownTimer(imagesArray.length*speedPerFrameInMiliSec, speedPerFrameInMiliSec) {
            public void onTick(long millisUntilFinished) {
                int currNode = (int) (((imagesArray.length*speedPerFrameInMiliSec)-millisUntilFinished)/speedPerFrameInMiliSec);
                String uri = imagesArray[currNode];
                int imageResource = activity.getResources().getIdentifier(uri, null, activity.getPackageName());
                Drawable res = activity.getResources().getDrawable(imageResource);
                img.setImageDrawable(res);
                setOnFrameChange(activity.getApplicationContext(),requestCode);
            }
            public void onFinish() {
                if(repeat==-10){
                    playanimation (superRepeat,superImagesArray,speedPerFrameInMiliSec,img,activity,requestCode);
                    setOnLoopComplete(activity.getApplicationContext(),requestCode);
                }else {
                    if(repeat>0){
                        int repeatX = repeat-1;
                        playanimation (repeatX,superImagesArray,speedPerFrameInMiliSec,img,activity,requestCode);
                        setOnLoopComplete(activity.getApplicationContext(),requestCode);
                    }else {
                        img.setImageBitmap(null);
                        /*String uri = imagesArray[0];
                        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                        Drawable res = getResources().getDrawable(imageResource);
                        img.setImageDrawable(res);*/
                        setOnAnimationFinish(activity.getApplicationContext(),requestCode);
                    }
                }
            }
        };
        T.start();
    }
    public static void setOnFrameChange(Context context, int code){
        Intent intent = new Intent("ANIMATOR_LISTENER")
                .putExtra("TAG", "OnFrameChange")
                .putExtra("CODE", ""+code);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
    public static void setOnLoopComplete(Context context, int code){
        Intent intent = new Intent("ANIMATOR_LISTENER")
                .putExtra("TAG", "OnLoopComplete")
                .putExtra("CODE", ""+code);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
    public static void setOnAnimationFinish(Context context, int code){
        Intent intent = new Intent("ANIMATOR_LISTENER")
                .putExtra("TAG", "OnAnimationFinish")
                .putExtra("CODE", ""+code);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void cancelAnimationNow(){
        try{T.cancel();}catch (Exception e){}
    }
    public void cancelAnimation(){
        try{superRepeat=0;}catch (Exception e){}
    }
}
