package com.sagarmakhija.spritesheet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.sagarmakhija.spriteanimationslib.animator;

public class MainActivity extends AppCompatActivity {
    animator a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://medium.com/@anujguptawork/how-to-create-your-own-android-library-and-publish-it-750e0f7481bf
        init();
    }

    public void init(){
        ImageView imgMain = findViewById(R.id.imgMain);
        a = new animator();
        a.animateSprite(0,72,"f",30,-10,imgMain,MainActivity.this,0);
        LocalBroadcastManager.getInstance(this).registerReceiver(animatorListener, new IntentFilter("ANIMATOR_LISTENER"));
    }
    public void stopAnimationNow(View v){
        a.cancelAnimationNow();
    }
    public void stopAnimation(View v){
        a.cancelAnimation();
    }

    private BroadcastReceiver animatorListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String TAG = intent.getStringExtra("TAG");
            String resCode = intent.getStringExtra("CODE");
            Log.e("~~~~","--------------------");
            Log.e("TAG",""+TAG);
            Log.e("resCode",""+resCode);
            Log.e("~~~~","--------------------");
            //TAG = OnFrameChange/OnLoopComplete/OnAnimationFinish
        }
    };
}
