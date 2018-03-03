package com.getinfocia.infocia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "sn.db";
	public static final String TABLE_NAME = "Table_News";
	public static final String KEY_GUIDE = "guid";
	public static final String KEY_TITLE = "title";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_POSTDATE = "postdate";
	public static final String KEY_IMAGEURL = "imgurl";
	public static final String KEY_VIDEOURL = "videourl";
	public static final String KEY_READED = "readed";
	public static final String KEY_SOURCETITLE = "source_title";
	public static final String KEY_SOURCELINK = "source_link";


	public static final String TABLE_CATEGORY_NAME = "Table_Category";
	public static final String KEY_CATEGORY_ID = "id";
	public static final String KEY_CATEGORY_NAME = "name";
	public static final String KEY_CATEGORY_IMAGE = "image";


	static SQLiteDatabase db;

	public DatabaseHelper(Context context) {
		super(context,DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ KEY_GUIDE + " INTEGER UNIQUE," 
				+ KEY_TITLE + " VARCHAR,"
				+ KEY_CATEGORY + " VARCHAR," 
				+ KEY_DESCRIPTION + " VARCHAR,"
				+ KEY_POSTDATE + " VARCHAR,"
				+ KEY_IMAGEURL + " VARCHAR,"
				+ KEY_VIDEOURL + " VARCHAR,"
				+ KEY_READED + " INTEGER,"
				+ KEY_SOURCETITLE + " VARCHAR,"
				+ KEY_SOURCELINK + " VARCHAR"
				+ ")";

		String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY_NAME + "("
				+ KEY_CATEGORY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_CATEGORY_NAME + " TEXT,"
				+ KEY_CATEGORY_IMAGE + " VARCHAR"
				+ ")";

		db.execSQL(CREATE_CONTACTS_TABLE);
		db.execSQL(CREATE_CATEGORY_TABLE);	

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

		// Create tables again
		onCreate(db);
	}

	public Cursor getAllNewsTableInfo()
	{
		db = getWritableDatabase();
		return db.rawQuery("select * from " + TABLE_NAME, null);
	}

	public Cursor getCategoryTableInfo()
	{
		db = getWritableDatabase();
		return db.rawQuery("select * from " + TABLE_CATEGORY_NAME, null);
	}


	//Return Max Guide Id
	public int getNewsMaxColumnData()
	{
		db = getWritableDatabase();
		return (int)db.compileStatement("SELECT MAX("+KEY_GUIDE+") FROM " + TABLE_NAME).simpleQueryForLong();
	}

	//Insert Data into Database
	public long InsertData(String TableName, ContentValues contentvalues, String s1)
	{
		db = getWritableDatabase();
		return db.insert(TableName, s1, contentvalues);
	}
	 


	//return Small Guide in database
	public int getNews_1stDataGuid()
	{
		db = getWritableDatabase();
		int i = 0;
		Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" ORDER BY "+KEY_GUIDE+" ASC", null);
		if (cursor.moveToFirst())
		{
			i = cursor.getInt(cursor.getColumnIndex(KEY_GUIDE));
		}
		return i;
	}

	public Cursor getAllNewsByCategory(String s)
	{
		db = getWritableDatabase();
		String s1 = "SELECT * FROM "+TABLE_NAME;
		if (!s.equals("all"))
		{
			s1 = (new StringBuilder(String.valueOf("SELECT * FROM Table_News"))).append(" where category = ").append(s).toString();
		}
		s = (new StringBuilder(String.valueOf(s1))).append(" ORDER BY readed, guid DESC").toString();
		return db.rawQuery(s, null);
	}

	public Cursor getAllCategory()
	{
		db=getWritableDatabase();
		String s1 = "SELECT * FROM "+TABLE_CATEGORY_NAME;
		return db.rawQuery(s1, null);
	}

	public void deleteCategory()
	{
		db.execSQL("delete from "+ TABLE_CATEGORY_NAME);
	}

}
