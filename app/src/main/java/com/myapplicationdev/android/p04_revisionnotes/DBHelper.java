package com.myapplicationdev.android.p04_revisionnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
	public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	// Start version with 1
	// increment by 1 whenever db schema changes.
	private static final int DATABASE_VER = 1;
	// Filename of the database
	private static final String DATABASE_NAME = "notes.db";

	private static final String TABLE_TASK = "notes";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_CONTENT = "noteContent";
	private static final String COLUMN_STAR = "star";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VER);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableSql = "CREATE TABLE " + TABLE_TASK +  "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_CONTENT + " TEXT,"
				+ COLUMN_STAR + " INTEGER )";
		db.execSQL(createTableSql);
		Log.i("info" ,"created tables");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
		// Create table(s) again
		onCreate(db);

	}

	public void insertTask(String noteContent, Integer star){

		// Get an instance of the database for writing
		SQLiteDatabase db = this.getWritableDatabase();
		// We use ContentValues object to store the values for
		//  the db operation
		ContentValues values = new ContentValues();
		// Store the column name as key and the description as value
		values.put(COLUMN_CONTENT, noteContent);
		// Store the column name as key and the date as value
		values.put(COLUMN_STAR, star);
		// Insert the row into the TABLE_TASK
		db.insert(TABLE_TASK, null, values);
		// Close the database connection
		db.close();
	}

	public ArrayList<String> getTaskContent() {
		// Create an ArrayList that holds String objects
		ArrayList<String> tasks = new ArrayList<String>();
		// Select all the tasks' description
		String selectQuery = "SELECT " + COLUMN_CONTENT
				+ " FROM " + TABLE_TASK;

		// Get the instance of database to read
		SQLiteDatabase db = this.getReadableDatabase();
		// Run the SQL query and get back the Cursor object
		Cursor cursor = db.rawQuery(selectQuery, null);

		// moveToFirst() moves to first row
		if (cursor.moveToFirst()) {
			// Loop while moveToNext() points to next row
			//  and returns true; moveToNext() returns false
			//  when no more next row to move to
			do {
				// Add the task content to the ArrayList object
				//  0 in getString(0) return the data in the first
				//  column in the Cursor object. getString(1)
				//  return second column data and so on.
				//  Use getInt(0) if data is an int
				tasks.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		// Close connection
		cursor.close();
		db.close();

		return tasks;
	}

	public ArrayList<Note> getTasks() {
		ArrayList<Note> tasks = new ArrayList<Note>();
		String selectQuery = "SELECT " + COLUMN_ID + ", "
				+ COLUMN_CONTENT + ", "
				+ COLUMN_STAR
				+ " FROM " + TABLE_TASK;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String noteContent = cursor.getString(1);
				int star = cursor.getInt(2);
				Note obj = new Note(id, noteContent, star);
				tasks.add(obj);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return tasks;
	}
}
