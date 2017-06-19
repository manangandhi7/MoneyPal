package com.MoneyPal.Activity;

import android.content.Context;
import android.support.test.espresso.core.deps.dagger.internal.DoubleCheckLazy;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.MoneyPal.Common.Utility;
import com.MoneyPal.Inventory.Storage;
import com.MoneyPal.Inventory.Transaction;
import com.MoneyPal.R;
import com.google.firebase.iid.FirebaseInstanceId;

import static com.MoneyPal.Common.Utility.YOU;


public class ShareMoneyActivity extends AppCompatActivity {

    private static final String TAG = "ShareMoneyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_money);
        Button button = (Button) findViewById(R.id.add_payer);
        button.setOnClickListener(onClick());
        button = (Button) findViewById(R.id.add_participant);
        button.setOnClickListener(onClick());
        button = (Button) findViewById(R.id.create_transaction);
        button.setOnClickListener(onClick());
        addChildLayout(findViewById(R.id.payer_list));
        addChildLayout(findViewById(R.id.participant_list));

//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        if(refreshedToken != null) {
//            Log.d("token", refreshedToken);
//            makeToast(refreshedToken);
//        }
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //mLayout.addView(createNewTextView(mEditText.getText().toString()));
                if (v == findViewById(R.id.add_payer)) {
                    addChildLayout(findViewById(R.id.payer_list));
                } else if (v == findViewById(R.id.add_participant)) {
                    addChildLayout(findViewById(R.id.participant_list));
                } else if (v == findViewById(R.id.create_transaction)) {
                    if(countEverything() == true){
                        makeToast("Transaction added");
                        finish();
                    } else {
                        makeToast("Wrong transaction data");
                    }
                } else {
                    ((LinearLayout) (v.getParent())).removeAllViews();
                }
            }
        };
    }

    private boolean countEverything() {
        LinearLayout payers = (LinearLayout) findViewById(R.id.payer_list);
        LinearLayout participants = (LinearLayout) findViewById(R.id.participant_list);

        Transaction transaction = new Transaction();
        //TODO validation of the fields
        for (int i = 0; i < payers.getChildCount(); i++) {

            AutoCompleteTextView spinner = (AutoCompleteTextView) payers.getChildAt(i).findViewById(R.id.participant_name);
            String participant = spinner.getText().toString();
            if(participant.compareTo("") == 0){
                participant = YOU;
            }
            EditText editText = (EditText) payers.getChildAt(i).findViewById(R.id.participant_contribution);
            Double amount = Double.parseDouble(editText.getText().toString());
            transaction.addPayer(participant, amount);
        }

        for (int i = 0; i < participants.getChildCount(); i++) {
            //TODO get amount only if checked
            AutoCompleteTextView spinner = (AutoCompleteTextView) participants.getChildAt(i).findViewById(R.id.participant_name);
            String participant = spinner.getText().toString();

            if(participant.compareTo("") == 0){
                participant = YOU;
            }

            CheckBox evenSplit = (CheckBox) findViewById(R.id.even_split);
            if (!evenSplit.isChecked()) {
                EditText editText = (EditText) participants.getChildAt(i).findViewById(R.id.participant_contribution);
                Double amount = Double.parseDouble(editText.getText().toString());
                transaction.addParticipant(participant, amount);
            } else {
                transaction.addParticipant(participant);
            }
        }

        //transaction.calculateEverything();
        if (!Storage.getInstance().addTransaction(transaction)){
            return false;
        }
        return true;
    }

    private void addChildLayout(View v) {
        //Inflater service
        LayoutInflater layoutInfralte = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //parent layout xml refrence
        LinearLayout linearLayout = (LinearLayout) v;
        //Child layout xml refrence
        View view = layoutInfralte.inflate(R.layout.item_part_details, null);


        ;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, Storage.getInstance().getUsersArray());
        AutoCompleteTextView participants = (AutoCompleteTextView) view.findViewById(R.id.participant_name);
        participants.setAdapter(adapter);

        CheckBox evenSplit = (CheckBox) findViewById(R.id.even_split);
        if (findViewById(R.id.participant_list) == v && evenSplit.isChecked()) {
            EditText partAmount = (EditText) view.findViewById(R.id.participant_contribution);
            partAmount.setVisibility(View.INVISIBLE);
        }

        Button button = (Button) view.findViewById(R.id.remove_part);
        button.setOnClickListener(onClick());
        //add child to parent
        linearLayout.addView(view);

        //TODO IMP : even uneven split ma ek j drop down rakh, add karie etle e niche jatu re ane niche
        // text box, participation ane remove button dekhay. on remove click, e participation jatu re.

        //TODO very IMP, send money wala UI ma bank details vade pan transfer karva devu pade.
        //TODO send and request ma same j UI pan khali request money ma reason hoy ane default ma bija options set hoy

        //TODO on create, select a random account number and IFSC from the given list for each profile and save it in extras for teh remaining presentation
    }


    public void onSplitSettingChanged(View v) {
        //makeToast("changed");
        LinearLayout l = (LinearLayout) findViewById(R.id.participant_list);
        l.removeAllViews();
        addChildLayout(l);
    }

    private void makeToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


}
