package com.squareandcube.expensetracker;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DisplayExpenseDetailsActivity extends AppCompatActivity {

    List<ExpenseDataModel> expenseDataModelList;
    ListView listView;
    AllDatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_expense_details);

        mDatabase = new AllDatabaseHelper(this);

        expenseDataModelList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewIncomeDetail);

        loadIncomesFromDatabase();
    }

    private void loadIncomesFromDatabase() {
        Cursor cursor = mDatabase.getAllExpenses();

        if (cursor.moveToFirst()) {
            do {
                expenseDataModelList.add(new ExpenseDataModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getDouble(2)
                ));
            } while (cursor.moveToNext());

            ExpenseAdapter adapter = new ExpenseAdapter(this, R.layout.expense_display_layout, expenseDataModelList, mDatabase);
            listView.setAdapter(adapter);
        }
    }
}
