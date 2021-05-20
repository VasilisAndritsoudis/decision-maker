package com.android.decisionmaker.database.handlers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.android.decisionmaker.database.models.Category;
import com.android.decisionmaker.database.models.Criteria;
import com.android.decisionmaker.database.models.Decision;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.SubCategory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class DBHandler extends SQLiteOpenHelper {
    private static DBHandler instance = null;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "decision_maker.db";

    private static final String TABLE_DECISION = "Decision";
    private static final String TABLE_SUBCATEGORY = "SubCategory";
    private static final String TABLE_CATEGORY = "Category";
    private static final String TABLE_CRITERIA = "Criteria";
    private static final String TABLE_CHOICE = "Choice";
    private static final String TABLE_SETTINGS = "Settings";

    public static DBHandler getDBHandler(Context ctx) {
        if (instance == null) {
            instance = new DBHandler(ctx.getApplicationContext());
        }
        return instance;
    }

    private DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

        String CREATE_CHOICE_TABLE = "CREATE TABLE " + TABLE_CHOICE + "(" +
                TABLE_CHOICE + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_CHOICE + "Name VARCHAR(30) NOT NULL" +
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

        String DECISION_CHOICE_TABLE = "CREATE TABLE " + TABLE_DECISION + "_" + TABLE_CHOICE + "(" +
                TABLE_DECISION + "ID INTEGER NOT NULL," +
                TABLE_CHOICE + "ID INTEGER NOT NULL," +
                TABLE_CRITERIA + "ID INTEGER NOT NULL," +
                TABLE_CHOICE + "Value INTEGER NOT NULL," +
                "PRIMARY KEY (" + TABLE_DECISION + "ID, " + TABLE_CHOICE + "ID, " + TABLE_CRITERIA + "ID)," +
                "FOREIGN KEY (" + TABLE_DECISION + "ID) REFERENCES " + TABLE_DECISION + "(" + TABLE_DECISION + "ID)," +
                "FOREIGN KEY (" + TABLE_CHOICE + "ID) REFERENCES " + TABLE_CHOICE + "(" + TABLE_CHOICE + "ID)," +
                "FOREIGN KEY (" + TABLE_CRITERIA + "ID) REFERENCES " + TABLE_CRITERIA + "("+ TABLE_CRITERIA + "ID)" +
                ");";

        String SETTINGS_TABLE = "CREATE TABLE " + TABLE_SETTINGS + "(" +
                TABLE_SETTINGS + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " DarkMode INT(1) NOT NULL," +
                " DecisionViewType VARCHAR(30) NOT NULL" +
                ");";

        String INSERT_CATEGORIES = "INSERT INTO " + TABLE_CATEGORY + " (" + TABLE_CATEGORY + "Name) VALUES" +
                "('Shopping')," +
                "('Activities');";

        String INSERT_SUBCATEGORIES = "INSERT INTO " + TABLE_SUBCATEGORY + " (" + TABLE_SUBCATEGORY + "Name, " + TABLE_CATEGORY + "ID) VALUES" +
                "('Phones', 1)," +
                "('Cars', 1)," +
                "('Concerts', 2)," +
                "('Bars', 2);";

        String INSERT_CRITERIA = "INSERT INTO " + TABLE_CRITERIA + " (" + TABLE_CRITERIA + "Name) VALUES" +
                "('Price')," +
                "('Horsepower')," +
                "('RAM')," +
                "('Pleasure')," +
                "('People');";

        String INSERT_SUBCATEGORY_CRITERIA = "INSERT INTO " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA
                + " (" + TABLE_SUBCATEGORY + "ID, " + TABLE_CRITERIA + "ID) VALUES" +
                "(1, 1)," +
                "(1, 3)," +
                "(2, 1)," +
                "(2, 2)," +
                "(3, 1)," +
                "(3, 4)," +
                "(4, 4)," +
                "(4, 5);";

        String INSERT_SETTINGS = "INSERT INTO " + TABLE_SETTINGS + " (DarkMode, DecisionViewType) VALUES" +
                "(0, 'Pie')";

        db.execSQL(CREATE_DECISION_TABLE);
        db.execSQL(CREATE_SUBCATEGORY_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_CRITERIA_TABLE);
        db.execSQL(CREATE_CHOICE_TABLE);
        db.execSQL(CREATE_DECISION_CRITERIA_TABLE);
        db.execSQL(SUBCATEGORY_CRITERIA_TABLE);
        db.execSQL(DECISION_CHOICE_TABLE);
        db.execSQL(SETTINGS_TABLE);
        db.execSQL(INSERT_CATEGORIES);
        db.execSQL(INSERT_SUBCATEGORIES);
        db.execSQL(INSERT_CRITERIA);
        db.execSQL(INSERT_SUBCATEGORY_CRITERIA);
        db.execSQL(INSERT_SETTINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

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
        }

        cursor.close();
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
            cursor.close();
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
        }

        cursor.close();
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
        }

        cursor.close();
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
        }

        cursor.close();
        db.close();
        return response;
    }

    public Decision getDecision(String decisionName, String decisionDate) {
        Decision decision = new Decision();

        String query = "SELECT " + TABLE_CRITERIA + "ID, " + TABLE_CRITERIA + "Name, " + TABLE_CRITERIA + "Weight" +
                " FROM " + TABLE_CRITERIA + " JOIN " + TABLE_DECISION + "_" + TABLE_CRITERIA + " USING(" + TABLE_CRITERIA + "ID)" +
                " JOIN " + TABLE_DECISION + " USING (" + TABLE_DECISION + "ID)" +
                " WHERE " + TABLE_DECISION + "Name = '" + decisionName + "' AND " + TABLE_DECISION + "Date = '" + decisionDate + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Criteria> criteria = null;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            criteria = new ArrayList<>();

            do {
                Criteria item = new Criteria();

                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setWeight(cursor.getInt(2));

                query = "SELECT " + TABLE_CHOICE + "ID, " + TABLE_CHOICE + "Name, " + TABLE_CHOICE + "Value" +
                        " FROM " + TABLE_CHOICE + " JOIN " + TABLE_DECISION + "_" + TABLE_CHOICE + " USING(" + TABLE_CHOICE + "ID)" +
                        " JOIN " + TABLE_CRITERIA + " USING(" + TABLE_CRITERIA + "ID)" +
                        " JOIN " + TABLE_DECISION + " USING (" + TABLE_DECISION + "ID)" +
                        " WHERE " + TABLE_DECISION + "Name = '" + decisionName + "'" +
                        " AND " + TABLE_DECISION + "Date = '" + decisionDate + "'" +
                        " AND " + TABLE_CRITERIA + "ID = " + item.getId();


                Cursor innerCursor = db.rawQuery(query, null);

                ArrayList<Choice> choices = null;

                if (innerCursor.getCount() != 0) {
                    innerCursor.moveToFirst();
                    choices = new ArrayList<>();

                    do {
                        Choice innerItem = new Choice();

                        innerItem.setId(innerCursor.getInt(0));
                        innerItem.setName(innerCursor.getString(1));
                        innerItem.setValue(innerCursor.getInt(2));

                        choices.add(innerItem);
                    } while (innerCursor.moveToNext());
                }

                innerCursor.close();
                item.setChoices(choices);

                criteria.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();

        query = "SELECT " + TABLE_DECISION + "ID, " + TABLE_DECISION + "Name, " + TABLE_DECISION + "Date, " + TABLE_SUBCATEGORY + "ID" +
                " FROM " + TABLE_DECISION +
                " WHERE " + TABLE_DECISION + "Name = '" + decisionName + "' AND " + TABLE_DECISION + "Date = '" + decisionDate + "'";

        cursor = db.rawQuery(query, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            decision.setId(cursor.getInt(0));
            decision.setName(cursor.getString(1));

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(cursor.getString(2));
                    decision.setDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                decision.setDate(null);
            }

            query = "SELECT " + TABLE_SUBCATEGORY + "Name" +
                    " FROM " + TABLE_SUBCATEGORY +
                    " WHERE " + TABLE_SUBCATEGORY + "ID = " + cursor.getInt(3);

            cursor.close();

            cursor = db.rawQuery(query, null);

            if (cursor.getCount() != 0) {
                cursor.moveToFirst();

                decision.setSubCategory(cursor.getString(0));

                cursor.close();
            }

            decision.setCriteria(criteria);
        }

        return decision;
    }

    public ArrayList<Decision> getDecisions() {
        String query = "SELECT " + TABLE_DECISION + "ID" +
                " FROM " + TABLE_DECISION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Decision> response = null;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            response = new ArrayList<>();

            do {
                query = "SELECT " + TABLE_DECISION + "Name, " + TABLE_DECISION + "Date" +
                        " FROM " + TABLE_DECISION +
                        " WHERE " + TABLE_DECISION + "ID = " + cursor.getInt(0);

                Cursor innerCursor = db.rawQuery(query, null);

                String decisionName;
                String decisionDate;

                if (innerCursor.getCount() != 0) {
                    innerCursor.moveToFirst();
                    decisionName = innerCursor.getString(0);
                    decisionDate = innerCursor.getString(1);
                    innerCursor.close();

                    Log.d("Hist DecID", String.valueOf(cursor.getInt(0)));
                    Log.d("Hist DecName", decisionName);
                    Log.d("Hist DecDate", decisionDate);

                    Decision decision = getDecision(decisionName, decisionDate);

                    Log.d("Resp DecID", String.valueOf(decision.getId()));
                    Log.d("Resp DecName", decision.getName());
                    Log.d("Resp DecDate", String.valueOf(decision.getDate()));

                    response.add(decision);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        if (response != null) {
            Collections.reverse(response);
        } else {
            response = new ArrayList<>();
        }

        return response;
    }

    public boolean getDarkMode() {
        String query = "SELECT DarkMode" +
                " FROM " + TABLE_SETTINGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int darkMode = 0;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            darkMode = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return darkMode != 0;
    }

    public String getViewType() {
        String query = "SELECT DecisionViewType" +
                " FROM " + TABLE_SETTINGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        String viewType = "Pie";

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            viewType = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return viewType;
    }

    public boolean saveCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TABLE_CATEGORY + "ID" +
                " FROM " + TABLE_CATEGORY +
                " WHERE " + TABLE_CATEGORY + "Name = '" + category.getName() + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() != 0) {
            cursor.close();
            return false;
        }

        cursor.close();

        String insertion = "INSERT INTO " + TABLE_CATEGORY + " (" + TABLE_CATEGORY + "Name)" +
                " VALUES ('" + category.getName() + "')";

        db.execSQL(insertion);

        return true;
    }

    public boolean saveSubCategory(SubCategory subCategory, ArrayList<Criteria> subCategoryCriteria) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TABLE_SUBCATEGORY + "ID" +
                " FROM " + TABLE_SUBCATEGORY +
                " WHERE " + TABLE_SUBCATEGORY + "Name = '" + subCategory.getName() + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() != 0) {
            cursor.close();
            return false;
        }

        cursor.close();

        query = "SELECT " + TABLE_CATEGORY + "ID" +
                " FROM " + TABLE_CATEGORY +
                " WHERE " + TABLE_CATEGORY + "Name = '" + subCategory.getCategory() + "'";

        cursor = db.rawQuery(query, null);

        int categoryId;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            categoryId = cursor.getInt(0);
            cursor.close();
        } else {
            cursor.close();
            return false;
        }

        String insertion = "INSERT INTO " + TABLE_SUBCATEGORY + " (" + TABLE_SUBCATEGORY + "Name, " + TABLE_CATEGORY + "ID)" +
                " VALUES ('" + subCategory.getName() + "', " + categoryId +")";

        db.execSQL(insertion);

        query = "SELECT " + TABLE_SUBCATEGORY + "ID" +
                " FROM " + TABLE_SUBCATEGORY +
                " WHERE " + TABLE_SUBCATEGORY + "Name = '" + subCategory.getName() + "'";

        cursor = db.rawQuery(query, null);

        int subCategoryId;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            subCategoryId = cursor.getInt(0);
            cursor.close();
        } else {
            cursor.close();
            return false;
        }

        if (!insertCriteria(subCategoryCriteria))
            return false;

        for (Criteria criteria : subCategoryCriteria) {
            query = "SELECT " + TABLE_CRITERIA + "ID" +
                    " FROM " + TABLE_CRITERIA +
                    " WHERE " + TABLE_CRITERIA + "Name = '" + criteria.getName() + "'";

            cursor = db.rawQuery(query, null);

            int criteriaId;

            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                criteriaId = cursor.getInt(0);
                cursor.close();
            } else {
                cursor.close();
                return false;
            }


            insertion = "INSERT INTO " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA + " (" + TABLE_SUBCATEGORY + "ID, " + TABLE_CRITERIA + "ID)" +
                    " VALUES (" + subCategoryId + ", " + criteriaId + ")";

            db.execSQL(insertion);
        }

        return true;
    }

    public boolean saveDecision(Decision decision) {
        if (!insertChoices(decision.getCriteria().get(0).getChoices()))
            return false;

        Log.d("Dec Save Debug", "Choices saved");

        if (!insertCriteria(decision.getCriteria()))
            return false;

        Log.d("Dec Save Debug", "Criteria saved");

        if (!insertDecision(decision))
            return false;

        Log.d("Dec Save Debug", "Decision saved");

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        String date = dateFormat.format(decision.getDate());

        String query = "SELECT " + TABLE_DECISION + "ID" +
                " FROM " + TABLE_DECISION +
                " WHERE " + TABLE_DECISION + "Name = '" + decision.getName() + "' AND " + TABLE_DECISION + "Date = '" + date + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int decisionId;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            decisionId = cursor.getInt(0);
        } else {
            cursor.close();
            Log.d("Dec Save Debug", "Decision was not saved");
            return false;
        }

        cursor.close();

        for (Criteria criteria : decision.getCriteria()) {
            query = "SELECT " + TABLE_CRITERIA + "ID" +
                    " FROM " + TABLE_CRITERIA +
                    " WHERE " + TABLE_CRITERIA + "Name = '" + criteria.getName() + "'";

            cursor = db.rawQuery(query, null);

            int criteriaId;

            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                criteriaId = cursor.getInt(0);
            } else {
                cursor.close();
                Log.d("Dec Save Debug", "Criterion was not saved");
                return false;
            }

            cursor.close();

            String insertion = "INSERT INTO " + TABLE_DECISION + "_" + TABLE_CRITERIA +
                    " (" + TABLE_DECISION + "ID, " + TABLE_CRITERIA + "ID, " + TABLE_CRITERIA + "Weight)" +
                    " VALUES (" + decisionId + ", " + criteriaId + ", " + criteria.getWeight() + ")";

            db.execSQL(insertion);

            for (Choice choice : criteria.getChoices()) {
                query = "SELECT " + TABLE_CHOICE + "ID" +
                        " FROM " + TABLE_CHOICE +
                        " WHERE " + TABLE_CHOICE + "Name = '" + choice.getName() + "'";

                cursor = db.rawQuery(query, null);

                int choiceId;

                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    choiceId = cursor.getInt(0);
                } else {
                    cursor.close();
                    Log.d("Dec Save Debug", "Choice was not saved");
                    return false;
                }

                cursor.close();

                insertion = "INSERT INTO " + TABLE_DECISION + "_" + TABLE_CHOICE +
                        " (" + TABLE_DECISION + "ID, " + TABLE_CHOICE + "ID, " + TABLE_CRITERIA + "ID, " + TABLE_CHOICE + "Value)" +
                        " VALUES (" + decisionId + ", " + choiceId + ", " + criteriaId + ", " + choice.getValue() + ")";

                db.execSQL(insertion);
            }
        }

        db.close();

        return true;
    }

    public void updateDarkMode(boolean darkMode) {
        int dMode = 0;

        if (darkMode)
            dMode = 1;

        String update = "UPDATE " + TABLE_SETTINGS +
                " SET DarkMode = " + dMode;

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        db.close();
    }

    public boolean updateDecision(Decision decision) {
        return true;
    }

    public void updateViewType(String viewType) {
        String update = "UPDATE " + TABLE_SETTINGS +
                " SET DecisionViewType = '" + viewType + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        db.close();
    }

    public boolean deleteDecision(Decision decision) {
        // Check if Decision exists
        String query = "SELECT " + TABLE_DECISION + "ID" +
                " FROM " + TABLE_DECISION +
                " WHERE " + TABLE_DECISION + "ID = " + decision.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() == 0) {
            cursor.close();
            return false;
        }

        cursor.close();
        db.close();

        // Delete Choices and Criteria from table Decision_Choice
        // By deleting Choices from table Decision_Choice, Criteria are deleted from there as well
        for (Choice choice : decision.getCriteria().get(0).getChoices()) {
            if (!deleteChoiceUsages(choice, decision)) {
                return false;
            }

            if (!choiceIsUsed(choice)) {
                if (!deleteChoice(choice)) {
                    return false;
                }
            }
        }

        // Delete Criteria from table Decision_Criteria
        for (Criteria criteria : decision.getCriteria()) {
            if (!deleteCriteriaUsages(criteria, decision)) {
                return false;
            }

            if (!criteriaIsUsed(criteria)) {
                if (!deleteCriteria(criteria)) {
                    return false;
                }
            }
        }

        // Delete Decision from table Decision
        String deletion = "DELETE FROM " + TABLE_DECISION +
                " WHERE " + TABLE_DECISION + "ID =" + decision.getId();

        db = this.getWritableDatabase();
        db.execSQL(deletion);
        db.close();

        return true;
    }

    private boolean choiceIsUsed(Choice choice) {
        String query = "SELECT *" +
                " FROM " + TABLE_DECISION + "_" + TABLE_CHOICE +
                " WHERE " + TABLE_CHOICE + "ID =" + choice.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() != 0) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    private boolean criteriaIsUsed(Criteria criteria) {
        String query = "SELECT *" +
                " FROM " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA +
                " WHERE " + TABLE_CRITERIA + "ID =" + criteria.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() != 0) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    private boolean insertChoices(ArrayList<Choice> choices) {
        if (choices.size() == 0)
            return false;

        SQLiteDatabase db = this.getWritableDatabase();

        for (Choice item : choices) {
            String query = "SELECT " + TABLE_CHOICE + "ID" +
                    " FROM " + TABLE_CHOICE +
                    " WHERE " + TABLE_CHOICE + "Name = '" + item.getName() + "'";

            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() != 0) {
                cursor.close();
                continue;
            }

            cursor.close();

            String insertion = "INSERT INTO " + TABLE_CHOICE + " (" + TABLE_CHOICE + "Name)" +
                    " VALUES ('" + item.getName() + "')";

            db.execSQL(insertion);
        }

        return true;
    }

    private boolean insertCriteria(ArrayList<Criteria> criteria) {
        if (criteria.size() == 0)
            return false;

        SQLiteDatabase db = this.getWritableDatabase();

        for (Criteria item : criteria) {
            String query = "SELECT " + TABLE_CRITERIA + "ID" +
                    " FROM " + TABLE_CRITERIA +
                    " WHERE " + TABLE_CRITERIA + "Name = '" + item.getName() + "'";

            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() != 0) {
                cursor.close();
                continue;
            }

            cursor.close();

            String insertion = "INSERT INTO " + TABLE_CRITERIA + " (" + TABLE_CRITERIA + "Name)" +
                    " VALUES ('" + item.getName() + "')";

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
        } else {
            cursor.close();
            return false;
        }

        cursor.close();

        String insertion;

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        String date = dateFormat.format(decision.getDate());

        insertion = "INSERT INTO " + TABLE_DECISION + " (" + TABLE_DECISION + "Name, " + TABLE_DECISION + "Date, " + TABLE_SUBCATEGORY + "ID)" +
                " VALUES ('" + decision.getName() + "', '" + date + "', " + subCategoryId + ")";

        db.execSQL(insertion);

        return true;
    }

    private boolean deleteChoiceUsages(Choice choice, Decision decision) {
        if (!choiceIsUsed(choice)) {
            return false;
        }

        String deletion = "DELETE FROM " + TABLE_DECISION + "_" + TABLE_CHOICE +
                " WHERE " + TABLE_CHOICE + "ID =" + choice.getId() +
                " AND " + TABLE_DECISION + "ID =" + decision.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deletion);
        db.close();

        return true;
    }

    private boolean deleteCriteriaUsages(Criteria criteria, Decision decision) {
        if (!criteriaIsUsed(criteria)) {
            return false;
        }

        String deletion = "DELETE FROM " + TABLE_DECISION + "_" + TABLE_CRITERIA +
                " WHERE " + TABLE_CRITERIA + "ID =" + criteria.getId() +
                " AND " + TABLE_DECISION + "ID =" + decision.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deletion);
        db.close();

        return true;
    }

    private boolean deleteChoice(Choice choice) {
        String query = "SELECT " + TABLE_CHOICE + "ID" +
                " FROM " + TABLE_CHOICE +
                " WHERE " + TABLE_CHOICE + "ID = " + choice.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return false;
        }

        cursor.close();

        String deletion = "DELETE FROM " + TABLE_CHOICE +
                " WHERE " + TABLE_CHOICE + "ID =" + choice.getId();

        db.execSQL(deletion);
        db.close();

        return true;
    }

    private boolean deleteCriteria(Criteria criteria) {
        String query = "SELECT " + TABLE_CRITERIA + "ID" +
                " FROM " + TABLE_CRITERIA +
                " WHERE " + TABLE_CRITERIA + "ID = " + criteria.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return false;
        }

        cursor.close();

        String deletion = "DELETE FROM " + TABLE_CRITERIA +
                " WHERE " + TABLE_CRITERIA + "ID =" + criteria.getId();

        db.execSQL(deletion);
        db.close();

        return true;
    }
}
