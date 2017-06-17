package com.MoneyPal.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.MoneyPal.Inventory.Storage;
import com.MoneyPal.R;

import static com.MoneyPal.Common.IDGenerator.generateUniqueID;
import static com.MoneyPal.Common.Utility.UNIQUE_ID;
import static com.MoneyPal.Common.Utility.USERNAME;

public class ProfileActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String uniqueID = mPreferences.getString(UNIQUE_ID, "");

        TextView textView = (TextView) findViewById(R.id.uniue_id_profile);

        textView.setText(uniqueID);

        ImageView imageView = (ImageView) findViewById(R.id.barcode);

        android.view.ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = 800;
        layoutParams.height = 800;
        imageView.setLayoutParams(layoutParams);

        String userName = mPreferences.getString(USERNAME, "");

        if (userName != "") {
            setTitle(userName);
        }
    }
}
