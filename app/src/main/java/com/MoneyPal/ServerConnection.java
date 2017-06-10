
package com.MoneyPal;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by manan on 6/9/2017.
 */


//TODO use IntentService instead
public class ServerConnection extends AsyncTask<String, Void, String> {

    private Exception exception;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    @Override
   protected String doInBackground(String... urls) {

        OkHttpClient client = new OkHttpClient();
        //TODO create new asynctask and its ready to go!!
        try {

            RequestBody body = RequestBody.create(JSON, urls[1]);
            Request request = new Request.Builder()
                    .url(urls[0])
                    .post(body)
                    .build();

            request = request.newBuilder().addHeader("apikey", "VP81nkyVLqrNgrf").build();
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            Log.d("response", s);
            return s;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }
}


/*

Server API Key help
AIzaSyAMYVE_UMU0bG5gfTRbYRi4_zkb0jyYsLw
Sender ID help
511654669517
 */