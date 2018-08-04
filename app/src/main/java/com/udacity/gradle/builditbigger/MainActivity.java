package com.udacity.gradle.builditbigger;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.jokeactivity.JokeActivity;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new JokeLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        startJokeActivity(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    private void startJokeActivity(String joke) {
        Intent intentToJokeActivity = new Intent(this, JokeActivity.class);
        intentToJokeActivity.putExtra(JokeActivity.JOKE_EXTRA, joke);
        startActivity(intentToJokeActivity);
    }
}
