package com.example.decisionmaker.database.handlers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;

import com.example.decisionmaker.database.models.Category;
import com.example.decisionmaker.database.models.Criteria;
import com.example.decisionmaker.database.models.Decision;
import com.example.decisionmaker.database.models.Choice;
import com.example.decisionmaker.database.models.SubCategory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
                TABLE_DECISION + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_DECISION + "Name VARCHAR(30) NOT NULL," +
                TABLE_DECISION + "Date DATETIME NOT NULL," +
                TABLE_SUBCATEGORY + "ID INTEGER NOT NULL," +
                "FOREIGN KEY (" + TABLE_SUBCATEGORY + "ID) REFERENCES " + TABLE_SUBCATEGORY + "(" + TABLE_SUBCATEGORY + "ID)" +
                ");";

        String CREATE_SUBCATEGORY_TABLE = "CREATE TABLE " + TABLE_SUBCATEGORY + "(" +
                TABLE_SUBCATEGORY + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_SUBCATEGORY + "Name VARCHAR(30) NOT NULL," +
                TABLE_CATEGORY + "ID INTEGER NOT NULL," +
                "FOREIGN KEY (" + TABLE_CATEGORY + "ID) REFERENCES " + TABLE_CATEGORY + "(" + TABLE_CATEGORY + "ID)" +
                ");";

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "(" +
                TABLE_CATEGORY + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_CATEGORY + "Name VARCHAR(30) NOT NULL" +
                ");";

        String CREATE_CRITERIA_TABLE = "CREATE TABLE " + TABLE_CRITERIA + "(" +
                TABLE_CRITERIA + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_CRITERIA + "Name VARCHAR(30) NOT NULL" +
                ");";

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "(" +
                TABLE_PRODUCT + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_PRODUCT + "Name VARCHAR(30) NOT NULL" +
                ");";

        String CREATE_DECISION_CRITERIA_TABLE = "CREATE TABLE " + TABLE_DECISION + "_" + TABLE_CRITERIA + "(" +
                TABLE_DECISION + "ID INTEGER NOT NULL," +
                TABLE_CRITERIA + "ID INTEGER NOT NULL," +
                TABLE_CRITERIA + "Weight INTEGER NOT NULL," +
                "PRIMARY KEY (" + TABLE_DECISION + "ID, " + TABLE_CRITERIA + "ID)," +
                "FOREIGN KEY (" + TABLE_DECISION + "ID) REFERENCES " + TABLE_DECISION + "(" + TABLE_DECISION + "ID)," +
                "FOREIGN KEY (" + TABLE_CRITERIA + "ID) REFERENCES " + TABLE_CRITERIA + "(" + TABLE_CRITERIA + "ID)" +
                ");";

        String SUBCATEGORY_CRITERIA_TABLE = "CREATE TABLE " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA + "(" +
                TABLE_SUBCATEGORY + "ID INTEGER NOT NULL," +
                TABLE_CRITERIA + "ID INTEGER NOT NULL," +
                "PRIMARY KEY (" + TABLE_SUBCATEGORY + "ID, " + TABLE_CRITERIA + "ID)," +
                "FOREIGN KEY (" + TABLE_SUBCATEGORY + "ID) REFERENCES " + TABLE_SUBCATEGORY + "(" + TABLE_SUBCATEGORY + "ID)," +
                "FOREIGN KEY (" + TABLE_CRITERIA + "ID) REFERENCES " + TABLE_CRITERIA + "(" + TABLE_CRITERIA + "ID)" +
                ");";

        String DECISION_PRODUCT_TABLE = "CREATE TABLE " + TABLE_DECISION + "_" + TABLE_PRODUCT + "(" +
                TABLE_DECISION + "ID INTEGER NOT NULL," +
                TABLE_PRODUCT + "ID INTEGER NOT NULL," +
                TABLE_CRITERIA + "ID INTEGER NOT NULL," +
                TABLE_PRODUCT + "Value INTEGER NOT NULL," +
                "PRIMARY KEY (" + TABLE_DECISION + "ID, " + TABLE_PRODUCT + "ID, " + TABLE_CRITERIA + "ID)," +
                "FOREIGN KEY (" + TABLE_DECISION + "ID) REFERENCES " + TABLE_DECISION + "(" + TABLE_DECISION + "ID)," +
                "FOREIGN KEY (" + TABLE_PRODUCT + "ID) REFERENCES " + TABLE_PRODUCT + "(" + TABLE_PRODUCT + "ID)," +
                "FOREIGN KEY (" + TABLE_CRITERIA + "ID) REFERENCES " + TABLE_CRITERIA + "("+ TABLE_CRITERIA + "ID)" +
                ");";

        String SETTINGS_TABLE = "CREATE TABLE " + TABLE_SETTINGS + "(" +
                "DarkMode BOOLEAN NOT NULL" +
                ");";

        ArrayList<String> INSERT_VALUES = new ArrayList<>();

        INSERT_VALUES.add("INSERT INTO " + TABLE_CATEGORY + " (" + TABLE_CATEGORY + "Name)" +
                "VALUES ('Shopping');");
        INSERT_VALUES.add("INSERT INTO " + TABLE_SUBCATEGORY + " (" + TABLE_SUBCATEGORY + "Name, " + TABLE_CATEGORY + "ID)" +
                "VALUES ('Phones', 1);");
        INSERT_VALUES.add("INSERT INTO " + TABLE_SUBCATEGORY + " (" + TABLE_SUBCATEGORY + "Name, " + TABLE_CATEGORY + "ID)" +
                "VALUES ('Cars', 1);");
        INSERT_VALUES.add("INSERT INTO " + TABLE_CRITERIA + " (" + TABLE_CRITERIA + "Name)" +
                "VALUES ('Price');");
        INSERT_VALUES.add("INSERT INTO " + TABLE_CRITERIA + " (" + TABLE_CRITERIA + "Name)" +
                "VALUES ('Horsepower');");
        INSERT_VALUES.add("INSERT INTO " + TABLE_CRITERIA + " (" + TABLE_CRITERIA + "Name)" +
                "VALUES ('RAM');");
        INSERT_VALUES.add("INSERT INTO " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA + " (" + TABLE_SUBCATEGORY + "ID, " + TABLE_CRITERIA + "ID)" +
                "VALUES (1, 1);");
        INSERT_VALUES.add("INSERT INTO " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA + " (" + TABLE_SUBCATEGORY + "ID, " + TABLE_CRITERIA + "ID)" +
                "VALUES (1, 3);");
        INSERT_VALUES.add("INSERT INTO " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA + " (" + TABLE_SUBCATEGORY + "ID, " + TABLE_CRITERIA + "ID)" +
                "VALUES (2, 1);");
        INSERT_VALUES.add("INSERT INTO " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA + " (" + TABLE_SUBCATEGORY + "ID, " + TABLE_CRITERIA + "ID)" +
                "VALUES (2, 2);");

        db.execSQL(CREATE_DECISION_TABLE);
        db.execSQL(CREATE_SUBCATEGORY_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_CRITERIA_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_DECISION_CRITERIA_TABLE);
        db.execSQL(SUBCATEGORY_CRITERIA_TABLE);
        db.execSQL(DECISION_PRODUCT_TABLE);
        for (String INSERT_VALUE : INSERT_VALUES) {
            db.execSQL(INSERT_VALUE);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // My methods
    // . . .

    public ArrayList<Category> getCategories() {
        String query = "SELECT *" +
                " FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Category> response = null;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            response = new ArrayList<>();

            do {
                Category cat = new Category();

                cat.setId(cursor.getInt(0));
                cat.setName(cursor.getString(1));

                response.add(cat);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return response;
    }

    public ArrayList<SubCategory> getSubCategoriesOfCategory(String categoryName) {
        String query = "SELECT " + TABLE_CATEGORY + "ID " +
                " FROM " + TABLE_CATEGORY +
                " WHERE " + TABLE_CATEGORY + "Name = '" + categoryName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int categoryId;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            categoryId = cursor.getInt(0);
            cursor.close();
        } else {
            return null;
        }

        query = "SELECT *" +
                " FROM " + TABLE_SUBCATEGORY +
                " WHERE " + TABLE_CATEGORY + "ID = " + categoryId;

        cursor = db.rawQuery(query, null);

        ArrayList<SubCategory> response = null;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            response = new ArrayList<>();

            do {
                SubCategory subCat = new SubCategory();

                subCat.setId(cursor.getInt(0));
                subCat.setName(cursor.getString(1));
                subCat.setCategory(categoryName);

                response.add(subCat);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return response;
    }

    public ArrayList<Criteria> getSubCategoryCriteria(String subCategoryName) {
        String query = "SELECT DISTINCT " + TABLE_CRITERIA + "ID, " + TABLE_CRITERIA + "Name" +
                " FROM " + TABLE_CRITERIA + " JOIN " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA + " USING (" + TABLE_CRITERIA + "ID)" +
                " JOIN " + TABLE_SUBCATEGORY + " USING (" + TABLE_SUBCATEGORY + "ID)" +
                " WHERE " + TABLE_SUBCATEGORY + "Name = '" + subCategoryName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Criteria> response = null;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            response = new ArrayList<>();

            do {
                Criteria criteria = new Criteria();

                criteria.setId(cursor.getInt(0));
                criteria.setName(cursor.getString(1));

                response.add(criteria);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return response;
    }

    public ArrayList<Criteria> getCategoryCriteria(String categoryName) {
        String query = "SELECT DISTINCT " + TABLE_CRITERIA + "ID, " + TABLE_CRITERIA + "Name" +
                " FROM " + TABLE_CRITERIA + " JOIN " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA + " USING (" + TABLE_CRITERIA + "ID)" +
                " JOIN " + TABLE_SUBCATEGORY + " USING (" + TABLE_SUBCATEGORY + "ID)" +
                " JOIN " + TABLE_CATEGORY + " USING (" + TABLE_CATEGORY + "ID)" +
                " WHERE " + TABLE_CATEGORY + "Name = '" + categoryName + "'" +
                " GROUP BY " + TABLE_CRITERIA + "Name" +
                " HAVING COUNT(*) > 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Criteria> response = null;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            response = new ArrayList<>();

            do {
                Criteria criteria = new Criteria();

                criteria.setId(cursor.getInt(0));
                criteria.setName(cursor.getString(1));

                response.add(criteria);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return response;
    }

    public boolean saveDecision(Decision decision) {
        if (!insertProducts(decision.getChoices()))
            return false;

        if (!insertCriteria(decision.getCriteria()))
            return false;

        // Associate Decision-Products-Criteria

        return insertDecision(decision);
    }

    private boolean insertProducts(ArrayList<Choice> choices) {
        if (choices.size() == 0)
            return false;

        SQLiteDatabase db = this.getWritableDatabase();

        for (Choice item : choices) {
            String insertion = "INSERT INTO " + TABLE_PRODUCT + " (" + TABLE_PRODUCT + "Name)" +
                    " VALUES (" + item.getName() + ")";

            db.execSQL(insertion);
        }

        return true;
    }

    private boolean insertCriteria(ArrayList<Criteria> criteria) {
        if (criteria.size() == 0)
            return false;

        SQLiteDatabase db = this.getWritableDatabase();

        for (Criteria item : criteria) {
            String insertion = "INSERT INTO " + TABLE_CRITERIA + " (" + TABLE_CRITERIA + "Name)" +
                    " VALUES (" + item.getName() + ")";

            db.execSQL(insertion);
        }

        return true;
    }

    private boolean insertDecision(Decision decision) {
        String query = "SELECT " + TABLE_SUBCATEGORY + "ID" +
                " FROM " + TABLE_SUBCATEGORY +
                " WHERE " + TABLE_SUBCATEGORY + "Name = '" + decision.getSubCategory() + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int subCategoryId;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            subCategoryId = cursor.getInt(0);
            cursor.close();
        } else {
            return false;
        }

        String insertion;

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = dateFormat.format(new Date());

        insertion = "INSERT INTO " + TABLE_DECISION + " (" + TABLE_DECISION + "Name, " + TABLE_DECISION + "Date, " + TABLE_SUBCATEGORY + "ID)" +
                " VALUES (" + decision.getName() + ", " + date + ", " + subCategoryId + ")";

        db.execSQL(insertion);

        return true;
    }
}
