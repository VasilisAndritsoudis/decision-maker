package com.example.decisionmaker.database.handlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "decision_maker.db";

    private static final String TABLE_DECISION = "Decision";
    private static final String TABLE_SUBCATEGORY = "SubCategory";
    private static final String TABLE_CATEGORY = "Category";
    private static final String TABLE_CRITERIA = "Criteria";
    private static final String TABLE_PRODUCT = "Product";
    private static final String TABLE_SETTINGS = "Settings";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DECISION_TABLE = "CREATE TABLE "+ TABLE_DECISION + "(" +
                TABLE_DECISION + "ID INT NOT NULL," +
                TABLE_DECISION + "Name VARCHAR(30) NOT NULL," +
                TABLE_DECISION + "Date DATETIME NOT NULL," +
                TABLE_SUBCATEGORY + "ID INT NOT NULL," +
                "PRIMARY KEY (" + TABLE_DECISION + "ID)," +
                "FOREIGN KEY (" + TABLE_SUBCATEGORY + "ID) REFERENCES " + TABLE_SUBCATEGORY + "(" + TABLE_SUBCATEGORY + "ID)" +
                ");";

        String CREATE_SUBCATEGORY_TABLE = "CREATE TABLE " + TABLE_SUBCATEGORY + "(" +
                TABLE_SUBCATEGORY + "ID INT NOT NULL," +
                TABLE_SUBCATEGORY + "Name VARCHAR(30) NOT NULL," +
                TABLE_CATEGORY + "ID INT NOT NULL," +
                "PRIMARY KEY (" + TABLE_SUBCATEGORY + "ID)," +
                "FOREIGN KEY (" + TABLE_CATEGORY + "ID) REFERENCES " + TABLE_CATEGORY + "(" + TABLE_CATEGORY + "ID)" +
                ");";

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "(" +
                TABLE_CATEGORY + "ID INT NOT NULL," +
                TABLE_CATEGORY + "Name VARCHAR(30) NOT NULL," +
                "PRIMARY KEY (" + TABLE_CATEGORY + "ID)" +
                ");";

        String CREATE_CRITERIA_TABLE = "CREATE TABLE " + TABLE_CRITERIA + "(" +
                TABLE_CRITERIA + "ID INT NOT NULL," +
                TABLE_CRITERIA + "Name VARCHAR(30) NOT NULL," +
                "PRIMARY KEY (" + TABLE_CRITERIA + "ID)" +
                ");";

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "(" +
                TABLE_PRODUCT + "ID INT NOT NULL," +
                TABLE_PRODUCT + "Name VARCHAR(30) NOT NULL," +
                "PRIMARY KEY (" + TABLE_PRODUCT + "ID)" +
                ");";

        String CREATE_DECISION_CRITERIA_TABLE = "CREATE TABLE " + TABLE_DECISION + "_" + TABLE_CRITERIA + "(" +
                TABLE_DECISION + "ID INT NOT NULL," +
                TABLE_CRITERIA + "ID INT NOT NULL," +
                TABLE_CRITERIA + "Weight INT NOT NULL," +
                "PRIMARY KEY (" + TABLE_DECISION + "ID, " + TABLE_CRITERIA + "ID)," +
                "FOREIGN KEY (" + TABLE_DECISION + "ID) REFERENCES " + TABLE_DECISION + "(" + TABLE_DECISION + "ID)," +
                "FOREIGN KEY (" + TABLE_CRITERIA + "ID) REFERENCES " + TABLE_CRITERIA + "(" + TABLE_CRITERIA + "ID)" +
                ");";

        String SUBCATEGORY_CRITERIA_TABLE = "CREATE TABLE " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA + "(" +
                TABLE_SUBCATEGORY + "ID INT NOT NULL," +
                TABLE_CRITERIA + "ID INT NOT NULL," +
                "PRIMARY KEY (" + TABLE_SUBCATEGORY + "ID, " + TABLE_CRITERIA + "ID)," +
                "FOREIGN KEY (" + TABLE_SUBCATEGORY + "ID) REFERENCES " + TABLE_SUBCATEGORY + "(" + TABLE_SUBCATEGORY + "ID)," +
                "FOREIGN KEY (" + TABLE_CRITERIA + "ID) REFERENCES " + TABLE_CRITERIA + "(" + TABLE_CRITERIA + "ID)" +
                ");";

        String DECISION_PRODUCT_TABLE = "CREATE TABLE " + TABLE_DECISION + "_" + TABLE_PRODUCT + "(" +
                TABLE_DECISION + "ID INT NOT NULL," +
                TABLE_PRODUCT + "ID INT NOT NULL," +
                TABLE_PRODUCT + "Value INT NOT NULL," +
                "PRIMARY KEY (" + TABLE_DECISION + "ID, " + TABLE_PRODUCT + "ID)," +
                "FOREIGN KEY (" + TABLE_DECISION + "ID) REFERENCES " + TABLE_DECISION + "(" + TABLE_DECISION + "ID)," +
                "FOREIGN KEY (" + TABLE_PRODUCT + "ID) REFERENCES " + TABLE_PRODUCT + "(" + TABLE_PRODUCT + "ID)" +
                ");";

        String SETTINGS_TABLE = "CREATE TABLE " + TABLE_SETTINGS + "(" +
                "DarkMode BOOLEAN NOT NULL" +
                ");";

        db.execSQL(CREATE_DECISION_TABLE);
        db.execSQL(CREATE_SUBCATEGORY_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_CRITERIA_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_DECISION_CRITERIA_TABLE);
        db.execSQL(SUBCATEGORY_CRITERIA_TABLE);
        db.execSQL(DECISION_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // My methods
    // . . .
}
