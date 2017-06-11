package com.MoneyPal.UI;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.MoneyPal.Common.Utility;
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

        addChildLayout(findViewById(R.id.payer_list));
        addChildLayout(findViewById(R.id.participant_list));


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
                } else {
//                    ((LinearLayout) (v.getParent().getParent())).removeView(v);
                    ((LinearLayout) (v.getParent())).removeAllViews();
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, Utility.FRIENDS);
        Spinner participants =  (Spinner) view.findViewById(R.id.participant_name);
        participants.setAdapter(adapter);

        CheckBox evenSplit =  (CheckBox) findViewById(R.id.even_split);
        if(findViewById(R.id.participant_list) == v && evenSplit.isChecked()) {
            EditText partAmount = (EditText) view.findViewById(R.id.participant_contribution);
            partAmount.setVisibility(View.INVISIBLE);
        }

        Button button = (Button) view.findViewById(R.id.remove_part);
        button.setOnClickListener(onClick());
        //add child to parent
        linearLayout.addView(view);
    }
}
