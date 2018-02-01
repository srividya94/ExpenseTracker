package com.squareandcube.expensetracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class SignIn extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private Button mIncomeButton;
    private Button mExpenseButton;
    private  Button mSavingsButton;
    private Button mInvestmentNews;
    AllDatabaseHelper incomeDatabase;
    AllDatabaseHelper expenseDatabase;
    SQLiteDatabase incomedb,expensedb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        incomeDatabase = new AllDatabaseHelper(this);
        expenseDatabase=new AllDatabaseHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mIncomeButton = (Button) findViewById(R.id.income_Button);
        mExpenseButton = (Button) findViewById(R.id.expense_Button);
        mSavingsButton=(Button)findViewById(R.id.Content_Savings_Button);
        mInvestmentNews=(Button)findViewById(R.id.investment_Button);
        mIncomeButton.setOnClickListener(this);
        mExpenseButton.setOnClickListener(this);
        mSavingsButton.setOnClickListener(this);
        mInvestmentNews.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        char id = 0;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }
        else  {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent mIntentSettings = new Intent(Settings.ACTION_SETTINGS);
//            Intent mIntentSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            mIntentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Uri uri = Uri.fromParts("package", getPackageName(), null);
//            mIntentSettings.setData(uri);
            startActivity(mIntentSettings);
           // mIntentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         //   Uri uri = Uri.fromParts("package", getPackageName(), null);
        //    mIntentSettings.setData(uri);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent ,2);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);

        } else if (id == R.id.nav_slideshow) {
            Intent mSLideshow =new Intent(SignIn.this,SlideShowActivity.class);
            startActivity(mSLideshow);
            return true;

        } else if (id == R.id.nav_share) {
            Intent mShareIntent=new Intent(Intent.ACTION_SEND);
            mShareIntent.setType("text/plain");
            String shareBody="Here is the share content body";
            mShareIntent.putExtra(Intent.EXTRA_SUBJECT,"Subject here");
            mShareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(mShareIntent,"Share Via"));

        } else if (id == R.id.nav_audio) {
            Intent maudio = new Intent(SignIn.this, AudioRecorderActivity.class);
            startActivity(maudio);
            return true;
        } else if (id == R.id.Scan_Qrcode) {
            Intent mScanner = new Intent(SignIn.this, QRCodeScanner.class);
            startActivity(mScanner);
            return true;


        }
        else if (id == R.id.nav_LogOut) {

            AlertDialog.Builder mLogout = new AlertDialog.Builder(SignIn.this);
            mLogout.setMessage("Are you sure you want to Logout your Account?");
            mLogout.setCancelable(true);

            mLogout.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent mLogoutIntent = new Intent(SignIn.this, LoginActivity.class);
                            startActivity(mLogoutIntent);
                        }
                    });

            mLogout.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            mLogout.show();


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.income_Button:
                Intent mIncomeActivity = new Intent(SignIn.this, IncomeActivity.class);
                startActivity(mIncomeActivity);
                break;

            case R.id.expense_Button:
                Intent mExpenseActivity = new Intent(SignIn.this, ExpenseActivity.class);
                startActivity(mExpenseActivity);
                break;

            case R.id.Content_Savings_Button:
                Intent mSavingActivity = new Intent(SignIn.this, SavingsActivity.class);
                String incomeData;
                incomedb=incomeDatabase.getReadableDatabase();
                Cursor incomeCursor=incomedb.rawQuery("select sum(income) from income", null);
                incomeCursor.moveToFirst();
                incomeData=incomeCursor.getString(0);

                String expenseData;
                expensedb=expenseDatabase.getReadableDatabase();
                Cursor expenseCursor=expensedb.rawQuery("select sum(expense) from expenses", null);
                expenseCursor.moveToFirst();
                expenseData=expenseCursor.getString(0);

                Bundle b = new Bundle();
                b.putString("amount", String.valueOf(incomeData));
                b.putString("expenses",String.valueOf(expenseData));
                mSavingActivity.putExtras(b);
                startActivity(mSavingActivity);
                break;
            case R.id.investment_Button:
                Intent mInvestmentNewsActivity = new Intent(SignIn.this, InvestmentNewsActivity.class);
                startActivity(mInvestmentNewsActivity);
                break;
        }
    }
}
