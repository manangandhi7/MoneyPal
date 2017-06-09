
package com.MoneyPal;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by manan on 6/9/2017.
 */


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
            Log.d("response", response.body().string());
            return response.body().string();
        } catch (Exception e) {
            this.exception = e;

            return null;
        }
    }
}
