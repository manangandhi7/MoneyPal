
package com.MoneyPal;


import android.os.AsyncTask;
import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.MoneyPal.Common.Utility.AUTHORIZATION_KEY;
import static com.MoneyPal.Common.Utility.AUTHORIZATION_STRING;
import static com.MoneyPal.Common.Utility.SBI_API_KEY;
import static com.MoneyPal.Common.Utility.SBI_API_KEY_STRING;
import static com.MoneyPal.Common.Utility.SendToFCM;
import static com.MoneyPal.Common.Utility.SendToSBI;

/**
 * Created by manan on 6/9/2017.
 */

//TODO use IntentService instead
public class ServerConnection extends AsyncTask<String, Void, String> {

    private static final String TAG = "ServerConnection";

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

            if (urls.length > 2 && urls[2] == SendToSBI) {
                request = request.newBuilder().addHeader(SBI_API_KEY_STRING, SBI_API_KEY).build();
            } else if (urls.length > 2 && urls[2] == SendToFCM){
                request = request.newBuilder().addHeader(AUTHORIZATION_STRING, AUTHORIZATION_KEY).build();
            }

            Log.d ("request", request.toString());

            Response response = client.newCall(request).execute();
            String s = response.body().string();
            Log.d("response", s);
            return s;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    private Response sendToSBIAPI(String... urls){
        Response response = null;



        return response;
    }

    private Response sendMessageToFCM(String... urls){
        Response response = null;



        return response;
    }
}

/*
Server API Key help
AIzaSyAMYVE_UMU0bG5gfTRbYRi4_zkb0jyYsLw
Sender ID help
511654669517
 */