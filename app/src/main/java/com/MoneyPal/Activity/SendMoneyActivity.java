package com.MoneyPal.Activity;

import android.app.DialogFragment;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.MoneyPal.Common.Utility;
import com.MoneyPal.Inventory.Notification;
import com.MoneyPal.Inventory.Storage;
import com.MoneyPal.R;

import com.MoneyPal.ServerConnection;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.MoneyPal.Common.Utility.SendToFCM;
import static com.MoneyPal.Common.Utility.SendToSBI;

public class SendMoneyActivity extends AppCompatActivity {

    private static final String TAG = "SendMoneyActivity";

    //public final String url = "http://52.172.213.166:8080/sbi/Account_List/api/EnqBancsAccountsList/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_send_money);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, Storage.getInstance().getUsersArray());
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)
                findViewById(R.id.receiver);
        autoCompleteTextView.setAdapter(adapter);

        final Button button = (Button) findViewById(R.id.transfer_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox transfer_now = (CheckBox) findViewById(R.id.transfer_now);
                if(transfer_now.isChecked()){
                    try {

                        JSONObject json = new JSONObject();
                        json.put("AccountNumber", "30001512992");
                        String s = new ServerConnection().execute(Utility.Account_List, json.toString(), SendToSBI).get();
                        //String s = new com.MoneyPal.ServerConnection().execute(Utility.Account_List, "{\"AccountNumber\": \"30001512992\" }").get();
                        Log.d("SBI", s);

                        Notification notification = new Notification();

                        String s2 = new ServerConnection().execute(Utility.FCM_URL, notification.getJSONData(getApplicationContext()).toString(), SendToFCM).get();Log.d("FCM", s2);
                        Toast.makeText(getApplicationContext(), "Money Transferred", Toast.LENGTH_LONG).show();

                        finish();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error occurred, please try again!", Toast.LENGTH_LONG).show();
                        Log.e("error", e.toString());
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Money will be transferred", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private boolean sendMoney(String from, String to, double amount) {
        Gson gson = new Gson();
        return true;
    }

    private boolean requestMoney(String from, String to, double amount) {
        return true;
    }

    public void onTransferNowChanged(View view) {
        CheckBox transfer_now = (CheckBox) view;
        if(!transfer_now.isChecked()){
            SublimePickerFragment pickerFrag = new SublimePickerFragment();
            pickerFrag.setCallback(mFragmentCallback);
            pickerFrag.show(getSupportFragmentManager(), "Select date/time");
        }
    }

    SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {

        @Override
        public void onCancelled() {
            CheckBox transfer_now = (CheckBox) findViewById(R.id.transfer_now);
            transfer_now.setChecked(true);
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {
            //Toast.makeText(getApplicationContext(), recurrenceOption.toString(), Toast.LENGTH_LONG).show();
        }
    };
}