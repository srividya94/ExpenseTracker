package com.squareandcube.expensetracker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SavingsActivity extends AppCompatActivity {

    TextView totalIncome,totalExpenses,totalSavings;
    Button mSavings;
    Double income,expenses,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        totalIncome=(TextView)findViewById(R.id.totalIncomeView);
        totalExpenses=(TextView)findViewById(R.id.totalExpenseView);
        totalSavings=(TextView)findViewById(R.id.totalSavingView);

        Bundle b=getIntent().getExtras();
        totalIncome.setText(b.getCharSequence("amount"));
        totalExpenses.setText(b.getCharSequence("expenses"));


        mSavings=(Button)findViewById(R.id.savingsButton);
        mSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                income = Double.parseDouble(totalIncome.getText().toString());
                expenses = Double.parseDouble(totalExpenses.getText().toString());
                total = income-expenses;
                totalSavings.setText(Double.toString(total));



            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.savings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_email:
                Intent mEmailIntent=new Intent(Intent.ACTION_SENDTO);
                mEmailIntent.setData(Uri.parse("mailto:"));
                startActivity(mEmailIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
