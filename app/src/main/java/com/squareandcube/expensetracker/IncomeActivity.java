package com.squareandcube.expensetracker;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IncomeActivity extends AppCompatActivity implements View.OnClickListener {


    EditText mEditTextExpense;
    Spinner mSpinnerExpenseSource;
    Button mButtonAddExpense, mButtonViewAllExpense;
    AllDatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        mDatabase = new AllDatabaseHelper(this);

        mEditTextExpense = (EditText) findViewById(R.id.editTextExpense);
        mSpinnerExpenseSource = (Spinner) findViewById(R.id.spinnerExpenseSource);
        mButtonAddExpense = (Button) findViewById(R.id.buttonAddExpense);
        mButtonViewAllExpense = (Button) findViewById(R.id.buttonViewAllExpense);
        mButtonAddExpense.setOnClickListener(this);
        mButtonViewAllExpense.setOnClickListener(this);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddExpense:
                mAddIncome();
                break;
            case R.id.buttonViewAllExpense:
                startActivity(new Intent(this, DisplayIncomeDetailsActivity.class));
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void mAddIncome() {
        String Expense = mEditTextExpense.getText().toString().trim();
        String source = mSpinnerExpenseSource.getSelectedItem().toString();


        if (Expense.isEmpty()) {
            mEditTextExpense.setError("IncomeDataModel can't be empty");
            mEditTextExpense.requestFocus();
            return;
        }


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = sdf.format(cal.getTime());

        if (mDatabase.addIncome(source, Double.parseDouble(Expense),joiningDate))
            Toast.makeText(this, "Expense Added Successfully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Could not add Expenses", Toast.LENGTH_SHORT).show();
    }

}
