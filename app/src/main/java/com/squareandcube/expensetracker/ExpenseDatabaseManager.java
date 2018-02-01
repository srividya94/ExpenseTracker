package com.squareandcube.expensetracker;

/**
 * Created by SRI on 08/12/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PAVAN KUMAR on 11/1/2017.
 */

public class ExpenseDatabaseManager extends SQLiteOpenHelper

{

    private static final String DATABASE_NAME = "ExpensesDatabase";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "expenses";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EXPENSES_LIST = "expenselist";
    private static final String COLUMN_EXPENSES_ADD_DATE = "expenseaddeddate";
    private static final String COLUMN_EXPENSES = "expense";

    public ExpenseDatabaseManager(Context context) {
        super(context, DATABASE_NAME , null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT expense_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_EXPENSES_LIST + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_EXPENSES + " double NOT NULL,\n" +
                "    " + COLUMN_EXPENSES_ADD_DATE + " datetime NOT NULL\n" +
                ");";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

    }
    boolean addExpense(String expenseList, double amount, String expenseAddedDate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPENSES_LIST, expenseList);
        contentValues.put(COLUMN_EXPENSES, amount);
        contentValues.put(COLUMN_EXPENSES_ADD_DATE, expenseAddedDate);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME, null, contentValues) != -1;
    }

    Cursor getAllExpense() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
    boolean updateExpense(int id, String expenseList, double amount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPENSES_LIST, expenseList);
        contentValues.put(COLUMN_EXPENSES, amount);
        return db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }
    boolean deleteExpense(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }
}
