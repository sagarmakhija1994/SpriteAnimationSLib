Control/play 2D animation (Sprite sheet animation)/ sequence image animations
1) play in a loop for unlimited times.
2) play in a loop with defined numbers of the loop.
3) cancel the animation on the current frame.
4) cancel the animation on completion current loop.

#Usage:

#Project Level:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	
#App Level:

	dependencies {
	        implementation 'com.github.sagarmakhija1994:SpriteAnimationSLib:1.0.0'
	}
	
    
# JAVA Code
    animator a = new animator();
    a.animateSprite(0,72,"img",30,-10,imgMain,MainActivity.this,0);
    
    //1# (0): 
    //frame file name start with 0 (image file name: img0.png)
    
    //2# (72):
    //frame file name ends with 73 (image file name: img73.png)
    
    //3# ("img"):
    //frame file name "img(n).png"(eg: img0.png)
    
    //4# (30):
    //speed of animation, 30fps
   
    //5# (-10)
    //-10 for unlimited loops/0 for single animation(without repeat)/1 for repeats animation for 1 time
    
    //6# (imgMain)
    //Imageview in your activity where to animate
    
    //7# (MainActivity.this)
    //activity class for context
    
    //8# (0)
    //id of animation request (you will get return this id on "OnFrameChange"/"OnLoopComplete"/"OnAnimationFinish" listner)
    
    //don't forgot to Add all images in "drawable" folder
    //eg: img0.png to img73.png (all 74 files!)


#Animation Listner

    //Animation listner
    private BroadcastReceiver animatorListener = new BroadcastReceiver() {
          @Override
          public void onReceive(Context context, Intent intent) {
              String TAG = intent.getStringExtra("TAG");
              String resCode = intent.getStringExtra("CODE");
              //TAG = OnFrameChange/OnLoopComplete/OnAnimationFinish
              //resCode = code you put while starting the animation
          }
     };


    //don't forgot to Add all images in "drawable" folder
    //eg: img0.png to img73.png (all 74 files!)

#Register Animation Listner

        LocalBroadcastManager.getInstance(this).registerReceiver(animatorListener, new IntentFilter("ANIMATOR_LISTENER"));

#Cancel Animation

1) cancel animation on current frame
		
        a.cancelAnimationNow();
        
2) cancel animation when current loop finish
		
        a.cancelAnimation();
        
