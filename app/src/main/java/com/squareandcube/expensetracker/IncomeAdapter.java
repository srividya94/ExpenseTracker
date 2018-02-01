package com.squareandcube.expensetracker;

/**
 * Created by SRI on 07/12/2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by VIVEK on 10/30/2017.
 */

class IncomeAdapter extends ArrayAdapter<IncomeDataModel> {

    Context mCtx;
    int layoutRes;
    List<IncomeDataModel> expenseDataModelList;
    AllDatabaseHelper mDatabase;

    public IncomeAdapter(Context mCtx, int layoutRes, List<IncomeDataModel> expenseDataModelList, AllDatabaseHelper mDatabase) {
        super(mCtx, layoutRes, expenseDataModelList);

        this.mCtx = mCtx;
        this.layoutRes = layoutRes;
        this.expenseDataModelList = expenseDataModelList;
        this.mDatabase = mDatabase;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(layoutRes, null);

        TextView textViewDisplayExpenseSource = view.findViewById(R.id.textViewDisplayExpenseSource);
        TextView textViewDisplayExpense = view.findViewById(R.id.textViewDisplayExpense);
        TextView textViewDisplayDateAndTime = view.findViewById(R.id.textViewDisplayDateAndTime);

        final IncomeDataModel expenseDataModel = expenseDataModelList.get(position);

        textViewDisplayExpenseSource.setText(expenseDataModel.getSource());
        textViewDisplayExpense.setText(String.valueOf(expenseDataModel.getExpense()));
        textViewDisplayDateAndTime.setText(expenseDataModel.getExpenseAddDate());

        view.findViewById(R.id.buttonDeleteExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteIncome(expenseDataModel);
            }
        });

        view.findViewById(R.id.buttonEditExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateIncome(expenseDataModel);
            }
        });

        return view;
    }

    private void updateIncome(final IncomeDataModel expenseDataModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_expense, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final EditText editTextupdateIncome = view.findViewById(R.id.editTextUpdateExpense);
        final Spinner spinnerUpdateExpenseSource = view.findViewById(R.id.spinnerUpadateExpenseSource);

        editTextupdateIncome.setText(String.valueOf(expenseDataModel.getExpense()));


        view.findViewById(R.id.buttonUpdateExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String source = spinnerUpdateExpenseSource.getSelectedItem().toString().trim();
                String ExpenseData = editTextupdateIncome.getText().toString().trim();


                if (ExpenseData.isEmpty()) {
                    editTextupdateIncome.setError("IncomeDataModel can't be empty");
                    editTextupdateIncome.requestFocus();
                    return;
                }

                if (mDatabase.updateIncome(expenseDataModel.getId(),source, Double.valueOf(ExpenseData))) {
                    Toast.makeText(mCtx, "Expense Updated", Toast.LENGTH_SHORT).show();
                    loadExpenseFromDatabaseAgain();
                }
                alertDialog.dismiss();
            }
        });
    }

    private void deleteIncome(final IncomeDataModel expenseDataModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (mDatabase.deleteIncome(expenseDataModel.getId()))
                    loadExpenseFromDatabaseAgain();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadExpenseFromDatabaseAgain() {
        Cursor cursor = mDatabase.getAllIncome();

        expenseDataModelList.clear();
        if (cursor.moveToFirst()) {
            do {
                expenseDataModelList.add(new IncomeDataModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getDouble(2)
                ));
            } while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }
}