package com.MoneyPal.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.MoneyPal.Inventory.Storage;
import com.MoneyPal.R;

import static com.MoneyPal.Common.IDGenerator.UniqueIDs;

public class AddFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, UniqueIDs);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)
                findViewById(R.id.id_part1);
        autoCompleteTextView.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, UniqueIDs);
        AutoCompleteTextView autoCompleteTextView2 = (AutoCompleteTextView)
                findViewById(R.id.id_part2);
        autoCompleteTextView2.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, UniqueIDs);
        AutoCompleteTextView autoCompleteTextView3 = (AutoCompleteTextView)
                findViewById(R.id.id_part3);
        autoCompleteTextView3.setAdapter(adapter3);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_friend_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)
                        findViewById(R.id.id_part1);

                AutoCompleteTextView autoCompleteTextView2 = (AutoCompleteTextView)
                        findViewById(R.id.id_part2);

                AutoCompleteTextView autoCompleteTextView3 = (AutoCompleteTextView)
                        findViewById(R.id.id_part3);

                EditText name = (EditText) findViewById(R.id.friend_name);

                if (name.getText().toString().compareToIgnoreCase("") == 0) {
                    Snackbar.make(view, "Name cannot be empty", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                if (isPartCorrect(autoCompleteTextView.getText().toString()) &&
                        isPartCorrect(autoCompleteTextView2.getText().toString()) &&
                        isPartCorrect(autoCompleteTextView3.getText().toString())) {

                } else {
                    Snackbar.make(view, "Invalid ID", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                String uniqueID = "";
                uniqueID += autoCompleteTextView.getText().toString();
                uniqueID += "_";
                uniqueID += autoCompleteTextView2.getText().toString();
                uniqueID += "_";
                uniqueID += autoCompleteTextView3.getText().toString();
                if (!Storage.getInstance().AddUser(name.getText().toString(), uniqueID)) {
                    Snackbar.make(view, "Invalid name/ID combination", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Friend added", Toast.LENGTH_LONG).show();
                    finish();
                }


            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean isPartCorrect(String text) {
        for (int i = 0; i < UniqueIDs.length; i++) {
            if (text.compareToIgnoreCase(UniqueIDs[i]) == 0) {
                return true;
            }
        }
        return false;
    }
}
