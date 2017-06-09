package com.MoneyPal;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import org.json.*;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendMoneyActivity extends AppCompatActivity {

    public final String url = "http://52.172.213.166:8080/sbi/Account_List/api/EnqBancsAccountsList/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, FRIENDS);
        //TODO drop down menu will be better, slect karo etle list popup thay, wither select existing one or create new one
        Spinner textView = (Spinner)
                findViewById(R.id.spinner);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)
                findViewById(R.id.receiver);
        textView.setAdapter(adapter);
        autoCompleteTextView.setAdapter(adapter);

        final Button button = (Button) findViewById(R.id.transfer_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //AlertDialog();
                Log.d("hell", "how r");
try {

    AsyncTask t = new com.MoneyPal.ServerConnection().execute(url, "{\"AccountNumber\": \"30001512992\" }");

    Log.d("tag", t.get().toString());

} catch (Exception e){
    Log.e("error", e.toString());
}





            }
        });
    }

    private static final String[] FRIENDS = new String[] {
            "Add new", "Mohit", "Turi", "Kabir", "Ali", "Priya"
    };



}
