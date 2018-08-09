package com.udacity.gradle.builditbigger.free;

import android.view.View;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.MainActivityParent;

public class MainActivity extends MainActivityParent {

    @Override
    public void tellJoke(View view) {
        Toast.makeText(this, "Test Extene", Toast.LENGTH_SHORT).show();
        super.tellJoke(view);
    }

}
