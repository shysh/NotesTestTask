package com.shysh.p.notes.database.entity;

import com.google.gson.annotations.SerializedName;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Note {
	
	private int id;
	private int user_id;
	@SerializedName("title")
	private String title;
	@SerializedName("body")
	private String body;
	@SerializedName("url")
	private String url;

	public static String TABLE_NAME = "Notes";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_USER_ID = "userid";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_BODY = "body";
	public static final String COLUMN_URL = "url";
	

	private static final String CREATE_TABLE = "create table " + TABLE_NAME
			+ " (" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_USER_ID + " integer not null, "
			+ COLUMN_TITLE + " text not null, "
			+ COLUMN_BODY + " text not null, "
			+ COLUMN_URL + " text);";
	
	public static void createTable(SQLiteDatabase db){
		db.execSQL(CREATE_TABLE);
	}
	
	public static void upgradeTable(SQLiteDatabase db){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	    createTable(db);
	}
	
	public Note(){
		
	}
	
	public Note(Cursor cursor){
		this();
		this.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
		this.title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
		this.body = cursor.getString(cursor.getColumnIndex(COLUMN_BODY));
		this.user_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
