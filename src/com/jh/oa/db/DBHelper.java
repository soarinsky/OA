package com.jh.oa.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	private static final String DBNAME = "jh.oa.db";
	private static final int VERSION = 1;
	
	public DBHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				"friends(id integer primary key autoincrement,student_id varchar(20),name vchar(20)," +
				"department vchar(20),email vchar(30),short_phone vchar(20),long_phone vchar(20)," +
				"academy vchar(20),campus vchar(20),jh_id vchar(20),sex vchar(20),birthday vchar(20)," +
				"qq vchar(20),introduction vchar(100),course vchar(100),py vchar(6))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS friends");
		onCreate(db);
		
	}

}
