package com.udacity.gradle.builditbigger;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class JokeLoader extends AsyncTaskLoader<String> {

    private String mJoke;
    private static MyApi myApiService = null;

    // options for running against local devappserver
    // - 10.0.2.2 is localhost's IP address in Android emulator
    // - turn off compression when running against local devappserver
    private static final String ROOT_URL = "http://10.0.2.2:8080/_ah/api/";

    public JokeLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (mJoke == null) {
            forceLoad();
        } else {
            deliverResult(mJoke);
        }
    }

    @Override
    public String loadInBackground() {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(ROOT_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        String joke = null;// = "Error on Joke Handling. Please Check Internet Connection And Try again.";

        try {
//            joke = myApiService.sayHi("test").execute().getData();
            joke = myApiService.joke().execute().getData();
        } catch (IOException ioException) {
            Log.e(JokeLoader.class.getSimpleName(), ioException.getMessage());
        }

        return joke;
    }

    @Override
    public void deliverResult(String data) {
        mJoke = data;
        super.deliverResult(data);
    }
}
