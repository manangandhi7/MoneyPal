package com.MoneyPal.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.test.espresso.core.deps.dagger.internal.DoubleCheckLazy;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MoneyPal.Common.Utility;
import com.MoneyPal.Inventory.Notification;
import com.MoneyPal.Inventory.Storage;
import com.MoneyPal.MessageNotification;
import com.MoneyPal.R;
import com.MoneyPal.Content.TransactionContent;
import com.MoneyPal.ServerConnection;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.MoneyPal.Common.IDGenerator.generateUniqueID;
import static com.MoneyPal.Common.Utility.GLOBAL_SUBSCRIBE;
import static com.MoneyPal.Common.Utility.NOTIFICATION_AMOUNT;
import static com.MoneyPal.Common.Utility.NOTIFICATION_MSG_TYPE;
import static com.MoneyPal.Common.Utility.SETTLEMENT_REQ;
import static com.MoneyPal.Common.Utility.SETTLEMENT_RES_OK;
import static com.MoneyPal.Common.Utility.SETTLEMENT_USER_IN_QUE;
import static com.MoneyPal.Common.Utility.SendToFCM;
import static com.MoneyPal.Common.Utility.UNIQUE_ID;
import static com.MoneyPal.Common.Utility.USERNAME;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private SharedPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Storage.getInstance();
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.request_money);
        button.setOnClickListener(onClick());
        button = (FloatingActionButton) findViewById(R.id.send_money);
        button.setOnClickListener(onClick());
        button = (FloatingActionButton) findViewById(R.id.add_transaction);
        button.setOnClickListener(onClick());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bootMeUp();

        FirebaseMessaging.getInstance().subscribeToTopic(GLOBAL_SUBSCRIBE);
//        if (getToken() != null) {
//            makeToast(getToken());
//            Log.d("token", getToken());
//        }

        //makeToast("hiii" + getIntent().getStringExtra("extra") );

        //unique ID
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String uniqueID = mPreferences.getString(UNIQUE_ID, "");

        if (uniqueID == "") {
            uniqueID = generateUniqueID();
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(UNIQUE_ID, uniqueID);
            editor.commit();
        }

        //makeToast(uniqueID);

        View v = navigationView.getHeaderView(0);
        TextView textView = (TextView) v.findViewById(R.id.nav_unique_id);

        textView.setText(uniqueID);

        String userName = mPreferences.getString(USERNAME, "");

        if (userName == "") {
            userName = "User";
        }

        TextView textView2 = (TextView) v.findViewById(R.id.nav_user_name);
        textView2.setText(userName);

        handleNotification();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bootMeUp();


        String userName = mPreferences.getString(USERNAME, "");

        if (userName == "") {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter your name");

// Set up the input
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String userName = input.getText().toString();
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putString(USERNAME, userName);
                    editor.commit();

                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    View v = navigationView.getHeaderView(0);
                    TextView textView2 = (TextView) v.findViewById(R.id.nav_user_name);
                    textView2.setText(userName);

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }

    private void settleDialogCode(final String user, final double amount){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Settle Money");
        final CharSequence[] items = {"Cash Settlement ","Request Money"};
        final boolean[] bools = {false, true};
        final ArrayList seletedItems=new ArrayList();


        builder.setMultiChoiceItems(items, bools,
                new DialogInterface.OnMultiChoiceClickListener() {
                    // indexSelected contains the index of item (of which checkbox checked)
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            // write your code when user checked the checkbox
                            seletedItems.add(indexSelected);
                        } else if (seletedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            // write your code when user Uchecked the checkbox
                            seletedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                });


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    Notification notification = new Notification();
                    notification.toUniqueID = Storage.getInstance().getUserMap().get(user).name;
                    notification.MessageType = SETTLEMENT_REQ;
                    notification.amount = Double.toString(amount);
                    notification.userInQue = user;
                    String msg = notification.getSettleRequestJSON(getApplicationContext()).toString();
                    String s2 = new ServerConnection().execute(Utility.FCM_URL, msg, SendToFCM).get();
                    Log.d("FCM", s2);
                    //Toast.makeText(getApplicationContext(), "Money Transferred", Toast.LENGTH_LONG).show();
                } catch (Exception ex){

                }

                //finish();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void handleNotification(){
        try {

            String msgType = getIntent().getStringExtra(NOTIFICATION_MSG_TYPE);
            if(msgType == null){
                return;
            }

            getIntent().removeExtra(NOTIFICATION_MSG_TYPE);

            Notification notification = new Notification();

            if(msgType.compareToIgnoreCase(SETTLEMENT_REQ) == 0){
                notification.MessageType = SETTLEMENT_RES_OK;
                notification.userInQue =             getIntent().getStringExtra(SETTLEMENT_USER_IN_QUE);
                notification.amount = getIntent().getStringExtra(NOTIFICATION_AMOUNT);
            } else if(msgType.compareToIgnoreCase(SETTLEMENT_RES_OK) == 0){
                String userInQue =             getIntent().getStringExtra(SETTLEMENT_USER_IN_QUE);

                HashMap map = Storage.getInstance().balanceMap;
                map.remove(userInQue);
            }

            //notification.toUniqueID = Storage.getInstance().getUserMap().get(user).name;

            String msg = notification.getSettleRequestJSON(getApplicationContext()).toString();
//            lastSettlmentUser = user;
            String s2 = new ServerConnection().execute(Utility.FCM_URL, msg, SendToFCM).get();
            Log.d("FCM", s2);
            //Toast.makeText(getApplicationContext(), "Money Transferred", Toast.LENGTH_LONG).show();
        } catch (Exception ex){

        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        TransactionContent transactionContent = TransactionContent.getInstance();
        recyclerView.setAdapter(new MainActivity.SimpleItemRecyclerViewAdapter(transactionContent.ITEMS));
    }

    private void bootMeUp() {
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //mLayout.addView(createNewTextView(mEditText.getText().toString()));
                if (v == findViewById(R.id.request_money)) {
                    Intent intent = new Intent(getApplicationContext(), SendMoneyActivity.class);
                    //intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                } else if (v == findViewById(R.id.send_money)) {
                    Intent intent = new Intent(getApplicationContext(), SendMoneyActivity.class);
                    //intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                } else if (v == findViewById(R.id.add_transaction)) {
                    Intent intent = new Intent(getApplicationContext(), ShareMoneyActivity.class);
                    startActivity(intent);
                }
            }
        };
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        } else if (id == R.id.nav_dostaro) {
            Intent intent = new Intent(getApplicationContext(), DostarListActivity.class);
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        } else if (id == R.id.nav_bank_details) {
            Intent intent = new Intent(getApplicationContext(), BankDetailsActivity.class);
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        } else if (id == R.id.nav_add_friend) {
            Intent intent = new Intent(getApplicationContext(), AddFriendActivity.class);
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            //MessageNotification.notify(getApplicationContext(), "he ne?", "maja aai gai", 2);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onSendSettleRequestChanged(View view) {
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<MainActivity.SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<TransactionContent.TransationItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<TransactionContent.TransationItem> items) {
            mValues = items;
        }

        @Override
        public MainActivity.SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new MainActivity.SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MainActivity.SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            double amount = Double.parseDouble(mValues.get(position).content);
            if (amount < 0) {
                holder.mContentView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            } else {
                holder.mContentView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(TransactionDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        TransactionDetailFragment fragment = new TransactionDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, TransactionDetailActivity.class);
                        intent.putExtra(TransactionDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public TransactionContent.TransationItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

    private void makeToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void settleKar(View v) {
        try {
            Button button = (Button) v;
            //show alert dialog

            LinearLayout ll = (LinearLayout) v.getParent();
            TextView tv = (TextView) ll.findViewById(R.id.content);
            double amount = Double.parseDouble(tv.getText().toString());

            TextView user = (TextView) ll.findViewById(R.id.id);


            settleDialogCode(user.getText().toString(), amount);
        } catch (Exception ex){
            //LOG
        }

        //handle alert events

        //redraw the UI

        //voila
    }
}
