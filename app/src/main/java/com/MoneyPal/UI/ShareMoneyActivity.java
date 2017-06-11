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

    private LinearLayout mLayout;
    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_money);
        mLayout = (LinearLayout) findViewById(R.id.payers);
        //mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.add_payer);
        mButton.setOnClickListener(onClick());
        TextView textView = new TextView(this);
        textView.setText("New text");
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //mLayout.addView(createNewTextView(mEditText.getText().toString()));
                addChildLayout();
            }
        };
    }

    private TextView createNewTextView(String text) {
        final ActionBar.LayoutParams lparams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText("New text: " + text);
        return textView;
    }

    public void addChildLayout(){
        //Inflater service
        LayoutInflater layoutInfralte=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //parent layout xml refrence
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.payer_list);
        //Child layout xml refrence
        View view=layoutInfralte.inflate(R.layout.item_part_details, null);
        //add child to parent
        linearLayout.addView(view);
    }
}
