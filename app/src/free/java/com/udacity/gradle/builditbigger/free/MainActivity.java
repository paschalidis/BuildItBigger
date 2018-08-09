package com.udacity.gradle.builditbigger.free;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.MainActivityParent;
import com.udacity.gradle.builditbigger.R;

public class MainActivity extends MainActivityParent {

    private InterstitialAd mInterstitialAd;
    private Button mTellJokeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTellJokeButton = findViewById(R.id.tell_joke_button);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.loadAd(getAdRequest());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(getAdRequest());
                MainActivity.super.tellJoke(mTellJokeButton);
            }
        });
    }

    @Override
    public void tellJoke(View view) {
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        } else {
            Log.d(MainActivity.class.getSimpleName(), "The interstitial wasn't loaded yet.");
        }
    }

    private AdRequest getAdRequest(){
        return new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
    }

}
