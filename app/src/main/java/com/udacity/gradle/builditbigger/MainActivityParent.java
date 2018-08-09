package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.jokeactivity.JokeActivity;


public class MainActivityParent extends AppCompatActivity implements OnJokeTaskCompleted {

    public final static String IDLING_RESOURCE_LOADER = "JOKE_LOADER";
    CountingIdlingResource mIdlingResource = new CountingIdlingResource(IDLING_RESOURCE_LOADER);

    private ProgressBar mTellJokeProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTellJokeProgressBar = findViewById(R.id.tell_joke_progress_bar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        mIdlingResource.increment();
        mTellJokeProgressBar.setVisibility(View.VISIBLE);
        new JokeAsyncTask(this).execute();
    }

    @Override
    public void onJokeTaskCompleted(String joke) {
        mIdlingResource.decrement();
        mTellJokeProgressBar.setVisibility(View.INVISIBLE);
        startJokeActivity(joke);
    }

    private void startJokeActivity(String joke) {
        Intent intentToJokeActivity = new Intent(this, JokeActivity.class);
        intentToJokeActivity.putExtra(JokeActivity.JOKE_EXTRA, joke);
        startActivity(intentToJokeActivity);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new CountingIdlingResource(IDLING_RESOURCE_LOADER);
        }
        return mIdlingResource;
    }

}
