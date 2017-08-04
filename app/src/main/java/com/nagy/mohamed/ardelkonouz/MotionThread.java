package com.nagy.mohamed.ardelkonouz;

import android.util.Log;
import android.view.View;

/**
 * Created by mohamednagy on 8/3/2017.
 */

public class MotionThread extends Thread {

    private View lineView;

    public MotionThread(View lineView){
        this.lineView = lineView;
    }

    @Override
    public void run() {
        boolean forward = false;
        float counter = 0;

        while(true){
            if(isInterrupted())
                break;
            if(!forward){
                counter++;
                if(counter == 306) {
                    forward = true;
                }
//                try {
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                if(!isInterrupted())
                    lineView.setTranslationX(counter);
            }else{
                counter--;
                if(counter == 0){
                    forward = false;
                }
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                if(!isInterrupted())
                    lineView.setTranslationX(counter);
            }

            Log.e("counter", String.valueOf(counter));
        }
        Log.e("thread is closed","done");
    }
}
