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

    /**
     * Makes the DBHandler class work as a singleton class
     * @param ctx the context from which it is called
     * @return returns the only DBHandler object instantiated
     */
    public static DBHandler getDBHandler(Context ctx) {
        if (instance == null) {
            instance = new DBHandler(ctx.getApplicationContext());
        }
        return instance;
    }

    private DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Initializes the whole database and inserts some data in it
     * @param db the database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the tables
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
                " DecisionViewType VARCHAR(30) NOT NULL" +
                ");";

        // Create the data to be inserted
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

        String INSERT_SETTINGS = "INSERT INTO " + TABLE_SETTINGS + " (DecisionViewType) VALUES" +
                "('Pie')";

        // Execute the SQL statements
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

    /**
     * Returns all the Categories saved in the database
     * @return an ArrayList of Category objects
     */
    public ArrayList<Category> getCategories() {
        // The query to find all categories
        String query = "SELECT *" +
                " FROM " + TABLE_CATEGORY;

        // Execute the query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Category> response = null;

        // Check if the response is not empty
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            response = new ArrayList<>();

            // Iterate through the response and save every item
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

    /**
     * Returns all the SubCategories that belong to one Category
     * @param categoryName the name of the category
     * @return an ArrayList of SubCategory objects
     */
    public ArrayList<SubCategory> getSubCategoriesOfCategory(String categoryName) {
        // The query to find the category id based on its name
        String query = "SELECT " + TABLE_CATEGORY + "ID " +
                " FROM " + TABLE_CATEGORY +
                " WHERE " + TABLE_CATEGORY + "Name = '" + categoryName + "'";

        // Execute the query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int categoryId;

        // Check if the response is not empty
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            // Extract the category id
            categoryId = cursor.getInt(0);
            cursor.close();
        } else {
            cursor.close();
            return null;
        }

        // The query to find all subcategories of a category
        query = "SELECT *" +
                " FROM " + TABLE_SUBCATEGORY +
                " WHERE " + TABLE_CATEGORY + "ID = " + categoryId;

        // Execute the query
        cursor = db.rawQuery(query, null);

        ArrayList<SubCategory> response = null;

        // Check if the response is not empty
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            response = new ArrayList<>();

            // Iterate through the response and save every item
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

    /**
     * Returns all the Criteria that are linked to one SubCategory
     * @param subCategoryName the name of the SubCategory
     * @return an ArrayList of Criteria objects
     */
    public ArrayList<Criteria> getSubCategoryCriteria(String subCategoryName) {
        // The query to find all criteria that belong to one subcategory
        String query = "SELECT DISTINCT " + TABLE_CRITERIA + "ID, " + TABLE_CRITERIA + "Name" +
                " FROM " + TABLE_CRITERIA + " JOIN " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA + " USING (" + TABLE_CRITERIA + "ID)" +
                " JOIN " + TABLE_SUBCATEGORY + " USING (" + TABLE_SUBCATEGORY + "ID)" +
                " WHERE " + TABLE_SUBCATEGORY + "Name = '" + subCategoryName + "'";

        // Execute the query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Criteria> response = null;

        // Check if the response is not empty
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            response = new ArrayList<>();

            // Iterate through the response and save every item
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

    /**
     * Returns all the Criteria that are linked to one Category
     * @param categoryName the name of the Category
     * @return an ArrayList of Criteria objects
     */
    public ArrayList<Criteria> getCategoryCriteria(String categoryName) {
        // The query to find all criteria that belong to one category
        String query = "SELECT DISTINCT " + TABLE_CRITERIA + "ID, " + TABLE_CRITERIA + "Name" +
                " FROM " + TABLE_CRITERIA + " JOIN " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA + " USING (" + TABLE_CRITERIA + "ID)" +
                " JOIN " + TABLE_SUBCATEGORY + " USING (" + TABLE_SUBCATEGORY + "ID)" +
                " JOIN " + TABLE_CATEGORY + " USING (" + TABLE_CATEGORY + "ID)" +
                " WHERE " + TABLE_CATEGORY + "Name = '" + categoryName + "'" +
                " GROUP BY " + TABLE_CRITERIA + "Name" +
                " HAVING COUNT(*) > 1";

        // Execute the query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Criteria> response = null;

        // Check if the response is not empty
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            response = new ArrayList<>();

            // Iterate through the response and save every item
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

    /**
     * Returns a Decision with all of its Criteria and Choices linked to it
     * @param decisionName the name of the Decision
     * @param decisionDate the date of the Decision
     * @return a Decision object
     */
    public Decision getDecision(String decisionName, String decisionDate) {
        Decision decision = new Decision();

        // The query to find all decision criteria
        String query = "SELECT " + TABLE_CRITERIA + "ID, " + TABLE_CRITERIA + "Name, " + TABLE_CRITERIA + "Weight" +
                " FROM " + TABLE_CRITERIA + " JOIN " + TABLE_DECISION + "_" + TABLE_CRITERIA + " USING(" + TABLE_CRITERIA + "ID)" +
                " JOIN " + TABLE_DECISION + " USING (" + TABLE_DECISION + "ID)" +
                " WHERE " + TABLE_DECISION + "Name = '" + decisionName + "' AND " + TABLE_DECISION + "Date = '" + decisionDate + "'";

        // Execute the query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Criteria> criteria = null;

        // Check if the response is not empty
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            criteria = new ArrayList<>();

            // Iterate through the response and save every item
            do {
                Criteria item = new Criteria();

                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setWeight(cursor.getInt(2));

                // The query to find all decision choices
                query = "SELECT " + TABLE_CHOICE + "ID, " + TABLE_CHOICE + "Name, " + TABLE_CHOICE + "Value" +
                        " FROM " + TABLE_CHOICE + " JOIN " + TABLE_DECISION + "_" + TABLE_CHOICE + " USING(" + TABLE_CHOICE + "ID)" +
                        " JOIN " + TABLE_CRITERIA + " USING(" + TABLE_CRITERIA + "ID)" +
                        " JOIN " + TABLE_DECISION + " USING (" + TABLE_DECISION + "ID)" +
                        " WHERE " + TABLE_DECISION + "Name = '" + decisionName + "'" +
                        " AND " + TABLE_DECISION + "Date = '" + decisionDate + "'" +
                        " AND " + TABLE_CRITERIA + "ID = " + item.getId();

                // Execute the query
                Cursor innerCursor = db.rawQuery(query, null);

                ArrayList<Choice> choices = null;

                // Check if the response is not empty
                if (innerCursor.getCount() != 0) {
                    innerCursor.moveToFirst();
                    choices = new ArrayList<>();

                    // Iterate through the response and save every item
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

        // The query to find the decision
        query = "SELECT " + TABLE_DECISION + "ID, " + TABLE_DECISION + "Name, " + TABLE_DECISION + "Date, " + TABLE_SUBCATEGORY + "ID" +
                " FROM " + TABLE_DECISION +
                " WHERE " + TABLE_DECISION + "Name = '" + decisionName + "' AND " + TABLE_DECISION + "Date = '" + decisionDate + "'";

        // Execute the query
        cursor = db.rawQuery(query, null);

        // Check if the response is not empty
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

            // The query to find the subcategory of the decision
            query = "SELECT " + TABLE_SUBCATEGORY + "Name" +
                    " FROM " + TABLE_SUBCATEGORY +
                    " WHERE " + TABLE_SUBCATEGORY + "ID = " + cursor.getInt(3);

            cursor.close();

            // Execute the query
            cursor = db.rawQuery(query, null);

            // Check if the response is not empty
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();

                decision.setSubCategory(cursor.getString(0));

                cursor.close();
            } else {
                decision.setSubCategory(null);
            }

            decision.setCriteria(criteria);
        }

        return decision;
    }

    /**
     * Returns all the Decisions saved in the database with all of their Criteria and Choices linked to them
     * @return an ArrayList of Decision objects sorted by date (decreasing)
     */
    public ArrayList<Decision> getDecisions() {
        // The query to find all decisions
        String query = "SELECT " + TABLE_DECISION + "ID" +
                " FROM " + TABLE_DECISION +
                " ORDER BY dateTime(" + TABLE_DECISION + "Date) DESC";

        // Execute the query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Decision> response = null;

        // Check if the response is not empty
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            response = new ArrayList<>();

            // Iterate through the response and save every item
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

                    Decision decision = getDecision(decisionName, decisionDate);

                    response.add(decision);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        if (response == null) {
            response = new ArrayList<>();
        }

        return response;
    }

    /**
     * Returns the View Type of a decision saved in the database
     * @return a string
     */
    public String getViewType() {
        // The query to find the view type
        String query = "SELECT DecisionViewType" +
                " FROM " + TABLE_SETTINGS;

        // Execute the query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        String viewType = "Pie";

        // Check if the response is not empty
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            viewType = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return viewType;
    }

    /**
     * Saves a Category to the database
     * @param category the Category object to be saved
     * @return true if success / false otherwise
     */
    public boolean saveCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        // The query to find the category to be saved
        String query = "SELECT " + TABLE_CATEGORY + "ID" +
                " FROM " + TABLE_CATEGORY +
                " WHERE " + TABLE_CATEGORY + "Name = '" + category.getName() + "'";

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Check if the response is empty
        if (cursor.getCount() != 0) {
            cursor.close();
            return false;
        }

        cursor.close();

        // The insertion to insert the category
        String insertion = "INSERT INTO " + TABLE_CATEGORY + " (" + TABLE_CATEGORY + "Name)" +
                " VALUES ('" + category.getName() + "')";

        // Execute the insertion
        db.execSQL(insertion);

        return true;
    }

    /**
     * Saves a SubCategory to the database
     * @param subCategory the SubCategory object to be saved
     * @param subCategoryCriteria all the Criteria objects to be linked to it
     * @return true if success / false otherwise
     */
    public boolean saveSubCategory(SubCategory subCategory, ArrayList<Criteria> subCategoryCriteria) {
        SQLiteDatabase db = this.getWritableDatabase();

        // The query to find the subcategory
        String query = "SELECT " + TABLE_SUBCATEGORY + "ID" +
                " FROM " + TABLE_SUBCATEGORY +
                " WHERE " + TABLE_SUBCATEGORY + "Name = '" + subCategory.getName() + "'";

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Check if the response is empty
        if (cursor.getCount() != 0) {
            cursor.close();
            return false;
        }

        cursor.close();

        // The query to find the category of the subcategory
        query = "SELECT " + TABLE_CATEGORY + "ID" +
                " FROM " + TABLE_CATEGORY +
                " WHERE " + TABLE_CATEGORY + "Name = '" + subCategory.getCategory() + "'";

        // Execute the query
        cursor = db.rawQuery(query, null);

        int categoryId;

        // Check if the response is not empty
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            categoryId = cursor.getInt(0);
            cursor.close();
        } else {
            cursor.close();
            return false;
        }

        // The insertion to insert the subcategory
        String insertion = "INSERT INTO " + TABLE_SUBCATEGORY + " (" + TABLE_SUBCATEGORY + "Name, " + TABLE_CATEGORY + "ID)" +
                " VALUES ('" + subCategory.getName() + "', " + categoryId +")";

        // Execute the insertion
        db.execSQL(insertion);

        // The query to find the new subcategory id
        query = "SELECT " + TABLE_SUBCATEGORY + "ID" +
                " FROM " + TABLE_SUBCATEGORY +
                " WHERE " + TABLE_SUBCATEGORY + "Name = '" + subCategory.getName() + "'";

        // Execute the query
        cursor = db.rawQuery(query, null);

        int subCategoryId;

        // Check if the response is not empty
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            subCategoryId = cursor.getInt(0);
            cursor.close();
        } else {
            cursor.close();
            return false;
        }

        // Insert the criteria
        if (!insertCriteria(subCategoryCriteria))
            return false;

        // Link each criterion to the subcategory
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

    /**
     * Saves a Decision to the database
     * @param decision the Decision to be saved
     * @return true if success / false otherwise
     */
    public boolean saveDecision(Decision decision) {
        // Insert the choices
        if (!insertChoices(decision.getCriteria().get(0).getChoices()))
            return false;

        // Insert the criteria
        if (!insertCriteria(decision.getCriteria()))
            return false;

        // Insert the decision
        if (!insertDecision(decision))
            return false;

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

        // Link all the criteria to the decision
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

            // Link all the choices to the decision
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

    /**
     * Updates a Decision in the database
     * @param decision the Decision to be updated
     * @return true if success / false otherwise
     */
    public boolean updateDecision(Decision decision) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        String date = dateFormat.format(decision.getDate());

        // The query to find the decision id
        String query = "SELECT " + TABLE_DECISION + "ID" +
                " FROM " + TABLE_DECISION +
                " WHERE " + TABLE_DECISION + "Name = '" + decision.getName() + "'" +
                " AND " + TABLE_DECISION + "Date = '" + date + "'";

        // Execute the query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Check if the response is not empty
        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return false;
        }

        cursor.close();

        String newDate = dateFormat.format(new Date());

        // The update for the decision
        String update = "UPDATE " + TABLE_DECISION +
                " SET " + TABLE_DECISION + "Date = '" + newDate + "'" +
                " WHERE " + TABLE_DECISION + "ID = " + decision.getId();

        db.execSQL(update);

        // Update every criterion linked to the decision
        for (Criteria criteria : decision.getCriteria()) {
            update = "UPDATE " + TABLE_DECISION + "_" + TABLE_CRITERIA +
                    " SET " + TABLE_CRITERIA + "Weight = " + criteria.getWeight() +
                    " WHERE " + TABLE_DECISION + "ID = " + decision.getId() +
                    " AND " + TABLE_CRITERIA + "ID = " + criteria.getId();

            db.execSQL(update);

            // Update every choice linked to the decision
            for (Choice choice : criteria.getChoices()) {
                update = "UPDATE " + TABLE_DECISION + "_" + TABLE_CHOICE +
                        " SET " + TABLE_CHOICE + "Value = " + choice.getValue() +
                        " WHERE " + TABLE_DECISION + "ID = " + decision.getId() +
                        " AND " + TABLE_CRITERIA + "ID = " + criteria.getId() +
                        " AND " + TABLE_CHOICE + "ID = " + choice.getId();

                db.execSQL(update);
            }
        }

        return true;
    }

    /**
     * Updates the View Type in the database
     * @param viewType the string View Type
     */
    public void updateViewType(String viewType) {
        String update = "UPDATE " + TABLE_SETTINGS +
                " SET DecisionViewType = '" + viewType + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        db.close();
    }

    /**
     * Deletes a Decision from the database
     * @param decision the Decision to be deleted
     * @return true if success / false otherwise
     */
    public boolean deleteDecision(Decision decision) {
        // Check if Decision exists
        String query = "SELECT " + TABLE_DECISION + "ID" +
                " FROM " + TABLE_DECISION +
                " WHERE " + TABLE_DECISION + "ID = " + decision.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return false;
        }

        cursor.close();

        // Delete Choices and Criteria from table Decision_Choice
        // By deleting Choices from table Decision_Choice, Criteria are deleted from there as well
        for (Choice choice : decision.getCriteria().get(0).getChoices()) {
            deleteChoiceUsages(choice, decision);

            if (!choiceIsUsed(choice)) {
                if (!deleteChoice(choice)) {
                    return false;
                }
            }
        }

        // Delete Criteria from table Decision_Criteria
        for (Criteria criteria : decision.getCriteria()) {
            deleteCriteriaUsages(criteria, decision);

            if (!criteriaIsUsed(criteria)) {
                if (!deleteCriteria(criteria)) {
                    Log.d("Delete", "Delete Criteria fail");
                    return false;
                }
            }
        }

        // Delete Decision from table Decision
        String deletion = "DELETE FROM " + TABLE_DECISION +
                " WHERE " + TABLE_DECISION + "ID =" + decision.getId();

        db.execSQL(deletion);
        db.close();

        return true;
    }

    /**
     * Checks if a Choice is linked to any decisions
     * @param choice the Choice to be checked
     * @return true if it is linked / false otherwise
     */
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

    /**
     * Checks if a Criterion is linked to any decisions
     * @param criteria the Criterion to be checked
     * @return true if it is linked / false otherwise
     */
    private boolean criteriaIsUsed(Criteria criteria) {
        String query = "SELECT *" +
                " FROM " + TABLE_DECISION + "_" + TABLE_CRITERIA +
                " WHERE " + TABLE_CRITERIA + "ID =" + criteria.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() != 0) {
            cursor.close();
            db.close();
            return true;
        }

        cursor.close();

        query = "SELECT *" +
                " FROM " + TABLE_SUBCATEGORY + "_" + TABLE_CRITERIA +
                " WHERE " + TABLE_CRITERIA + "ID =" + criteria.getId();

        cursor = db.rawQuery(query, null);

        if (cursor.getCount() != 0) {
            cursor.close();
            db.close();
            return true;
        }

        cursor.close();
        db.close();

        return false;
    }

    /**
     * Inserts a list of Choices to the database
     * @param choices the ArrayList of Choices to be inserted
     * @return true if success / false otherwise
     */
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

    /**
     * Inserts a list of Criteria to the database
     * @param criteria the ArrayList of Criteria to be inserted
     * @return true if success / false otherwise
     */
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

    /**
     * Inserts a decision to the database
     * @param decision the Decision to be inserted
     * @return true if success / false otherwise
     */
    private boolean insertDecision(Decision decision) {
        SQLiteDatabase db = this.getWritableDatabase();

        int subCategoryId = -1;

        if (decision.getSubCategory() != null) {
            String query = "SELECT " + TABLE_SUBCATEGORY + "ID" +
                    " FROM " + TABLE_SUBCATEGORY +
                    " WHERE " + TABLE_SUBCATEGORY + "Name = '" + decision.getSubCategory() + "'";

            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                subCategoryId = cursor.getInt(0);
            } else {
                cursor.close();
                return false;
            }

            cursor.close();
        }

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

    /**
     * Deletes all links between a Choice and a Decision
     * @param choice the Choice
     * @param decision the Decision
     */
    private void deleteChoiceUsages(Choice choice, Decision decision) {
        String deletion = "DELETE FROM " + TABLE_DECISION + "_" + TABLE_CHOICE +
                " WHERE " + TABLE_CHOICE + "ID =" + choice.getId() +
                " AND " + TABLE_DECISION + "ID =" + decision.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deletion);
        db.close();
    }

    /**
     * Deletes all links between a Criterion and a Decision
     * @param criteria the Criterion
     * @param decision the Decision
     */
    private void deleteCriteriaUsages(Criteria criteria, Decision decision) {
        String deletion = "DELETE FROM " + TABLE_DECISION + "_" + TABLE_CRITERIA +
                " WHERE " + TABLE_CRITERIA + "ID =" + criteria.getId() +
                " AND " + TABLE_DECISION + "ID =" + decision.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deletion);
        db.close();
    }

    /**
     * Deletes a Choice from the database
     * @param choice the Choice to be deleted
     * @return true if success / false otherwise
     */
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

    /**
     * Deletes a Criterion from the database
     * @param criteria the Criterion to be deleted
     * @return true if success / false otherwise
     */
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
