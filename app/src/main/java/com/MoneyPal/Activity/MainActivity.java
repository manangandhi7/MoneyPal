package com.MoneyPal.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.MoneyPal.Inventory.Storage;
import com.MoneyPal.ItemDetailFragment;
import com.MoneyPal.R;
import com.MoneyPal.dummy.DummyContent;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import static com.MoneyPal.Common.IDGenerator.generateUniqueID;
import static com.MoneyPal.Common.Utility.GLOBAL_CATEGORY;
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

        FirebaseMessaging.getInstance().subscribeToTopic(GLOBAL_CATEGORY);
//        if(getToken() != null) {
//            makeToast(getToken());
//        }

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
    }

    @Override
    protected void onStart() {
        super.onStart();

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

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        DummyContent dummyContent = DummyContent.getInstance();
        recyclerView.setAdapter(new MainActivity.SimpleItemRecyclerViewAdapter(dummyContent.ITEMS));
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
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<MainActivity.SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
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

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);

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
            public DummyContent.DummyItem mItem;

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
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void settleKar(View v) {
        //parent layout xml refrence
        Button button = (Button) v;

        makeToast("clicked");
    }
}
