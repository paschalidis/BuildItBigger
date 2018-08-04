package com.example.android.jokeactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_EXTRA = "joke_extra";

    private String mJoke;
    private TextView mJokeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        mJokeView = findViewById(R.id.joke_text_view);

        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra(JOKE_EXTRA)) {
                mJoke = intent.getStringExtra(JOKE_EXTRA);
                if(mJoke != null){
                    mJokeView.setText(mJoke);
                }
            }
        }
    }
}
