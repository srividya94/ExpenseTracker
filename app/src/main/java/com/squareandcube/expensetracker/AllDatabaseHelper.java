package com.squareandcube.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreekarsrihari on 15-12-2017.
 */

public class AllDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "employee.db";
    public static final String TABLE_USER = "user";
    public static final String TABLE_NAME = "expenses";
    public static final String TABLE_NAME1 = "income";

    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_FIRST_NAME = "user_firstname";
    public static final String COLUMN_USER_LAST_NAME = "user_lastname";
    public static final String COLUMN_USER_DATE_OF_BIRTH = "user_dateofbirth";
    public static final String COLUMN_USER_GENDER = "user_gender";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_PASSWORD = "user_password";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EXPENSES_LIST = "expenselist";
    public static final String COLUMN_EXPENSES_ADD_DATE = "expenseaddeddate";
    public static final String COLUMN_EXPENSES = "expense";

    public static final String COLUMN_ID1 = "id";
    public static final String COLUMN_SOURCE = "incomesource";
    public static final String COLUMN_DATE_TIME = "joiningdate";
    public static final String COLUMN_INCOME = "income";





    public AllDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_FIRST_NAME + " TEXT," +
                COLUMN_USER_LAST_NAME + " TEXT," + COLUMN_USER_DATE_OF_BIRTH + " TEXT," + COLUMN_USER_GENDER + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

        String sql1 = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT expense_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_EXPENSES_LIST + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_EXPENSES + " double NOT NULL,\n" +
                "    " + COLUMN_EXPENSES_ADD_DATE + " datetime NOT NULL\n" +
                ");";

        String sql2 = "CREATE TABLE " + TABLE_NAME1 + " (\n" +
                "    " + COLUMN_ID1 + " INTEGER NOT NULL CONSTRAINT income_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_SOURCE + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_INCOME + " double NOT NULL,\n" +
                "    " + COLUMN_DATE_TIME + " datetime NOT NULL\n" +
                ");";


        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int
            oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME1);

        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, user.getFirstname());
        values.put(COLUMN_USER_LAST_NAME, user.getLastname());
        values.put(COLUMN_USER_DATE_OF_BIRTH, user.getDateofbirth());
        values.put(COLUMN_USER_GENDER, user.getGender());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_FIRST_NAME,
                COLUMN_USER_LAST_NAME,
                COLUMN_USER_DATE_OF_BIRTH,
                COLUMN_USER_GENDER,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_FIRST_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setFirstname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME)));
                user.setLastname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME)));
                user.setDateofbirth(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DATE_OF_BIRTH)));
                user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_USER_GENDER)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, user.getFirstname());
        values.put(COLUMN_USER_LAST_NAME, user.getLastname());
        values.put(COLUMN_USER_DATE_OF_BIRTH, user.getDateofbirth());
        values.put(COLUMN_USER_GENDER, user.getGender());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    boolean addExpenses(String expenseList, double amount, String expenseAddedDate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPENSES_LIST, expenseList);
        contentValues.put(COLUMN_EXPENSES, amount);
        contentValues.put(COLUMN_EXPENSES_ADD_DATE, expenseAddedDate);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME, null, contentValues) != -1;
    }

    Cursor getAllExpenses() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
    boolean updateExpenses(int id, String expenseList, double amount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPENSES_LIST, expenseList);
        contentValues.put(COLUMN_EXPENSES, amount);
        return db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }
    boolean deleteExpenses(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }
    Cursor getAllSumExpenses(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT SUM(COLUMN_EXPENSE) " + TABLE_NAME, null);
    }
    boolean addIncome(String dept, double income, String joiningdate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SOURCE, dept);
        contentValues.put(COLUMN_INCOME, income);
        contentValues.put(COLUMN_DATE_TIME, joiningdate);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME1, null, contentValues) != -1;
    }

    Cursor getAllIncome() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
    }
    boolean updateIncome(int id, String source, double salary) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SOURCE, source);
        contentValues.put(COLUMN_INCOME, salary);
        return db.update(TABLE_NAME1, contentValues, COLUMN_ID1 + "=?", new String[]{String.valueOf(id)}) == 1;
    }//
    boolean deleteIncome(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME1, COLUMN_ID1 + "=?", new String[]{String.valueOf(id)}) == 1;
    }
}
