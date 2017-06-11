package com.MoneyPal.UI;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MoneyPal.R;

public class ShareMoneyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_money);
        Button button = (Button) findViewById(R.id.add_payer);
        button.setOnClickListener(onClick());
        button = (Button) findViewById(R.id.add_participant);
        button.setOnClickListener(onClick());
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //mLayout.addView(createNewTextView(mEditText.getText().toString()));
                if(v == findViewById(R.id.add_payer)){
                    addChildLayout(findViewById(R.id.payer_list));
                } else if (v == findViewById(R.id.add_participant)){
                    addChildLayout(findViewById(R.id.participant_list));
                } else if (v == findViewById(R.id.remove_part)){

                }
            }
        };
    }

    public void addChildLayout(View v){
        //Inflater service
        LayoutInflater layoutInfralte=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //parent layout xml refrence
        LinearLayout linearLayout=(LinearLayout) v;
        //Child layout xml refrence
        View view=layoutInfralte.inflate(R.layout.item_part_details, null);
        //add child to parent
        linearLayout.addView(view);
    }
}
