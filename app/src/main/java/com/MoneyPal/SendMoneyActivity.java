package com.MoneyPal;

import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.*;

public class SendMoneyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        final Button button = (Button) findViewById(R.id.transfer_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //AlertDialog();
                Log.d("hell", "how r");


            }
        });
    }

    public void selfDestruct(View view) {
        // Kabloey
    }
}
