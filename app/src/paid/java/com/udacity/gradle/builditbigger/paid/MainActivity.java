package com.udacity.gradle.builditbigger.paid;


import android.view.View;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.MainActivityParent;

public class MainActivity extends MainActivityParent{

    @Override
    public void tellJoke(View view) {
        Toast.makeText(this, "Paid Joke", Toast.LENGTH_LONG).show();
        super.tellJoke(view);
    }
}
