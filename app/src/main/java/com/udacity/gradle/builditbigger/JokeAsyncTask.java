package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class JokeAsyncTask extends AsyncTask<Void, Void, String>{

    private static MyApi mMyApiService = null;
    private OnJokeTaskCompleted mOnJokeTaskCompleted;

    public JokeAsyncTask(OnJokeTaskCompleted onJokeTaskCompleted){
        mOnJokeTaskCompleted = onJokeTaskCompleted;
    }

    @Override
    protected String doInBackground(Void... voids) {

        if(mMyApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            mMyApiService = builder.build();
        }

        String joke = null;
        try {
            joke = mMyApiService.joke().execute().getData();
        } catch (IOException ioException) {
            Log.e(JokeLoader.class.getSimpleName(), ioException.getMessage());
        }

        return joke;
    }

    @Override
    protected void onPostExecute(String joke) {
        mOnJokeTaskCompleted.onJokeTaskCompleted(joke);
    }

}
