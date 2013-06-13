package com.shysh.p.notes.database.entity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class User {
	private int id;
	private String login;
	private String name;
	private String password;

	public static String TABLE_NAME = "Users";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_LOGIN = "login";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PASSWORD = "password";

	private static final String CREATE_TABLE = "create table " + TABLE_NAME
			+ " (" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_NAME + " text not null, "
			+ COLUMN_LOGIN + " text not null, "
			+ COLUMN_PASSWORD +" text not null);";

	public static void createTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	public static void upgradeTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		createTable(db);
	}
	
	public User(){
		
	}
	
	public User(Cursor cursor){
		this();
		this.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
		this.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
		this.login = cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN));
		this.password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
