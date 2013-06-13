package com.shysh.p.notes.database;

import java.util.ArrayList;

import com.shysh.p.notes.database.entity.Note;
import com.shysh.p.notes.database.entity.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDBHelper extends SQLiteOpenHelper {

	private final static String DATA_BASE_NAME = "db.sqlite";
	private final static int DATA_BASE_VERSION = 1;

	public SQLiteDBHelper(Context context) {
		super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Note.createTable(db);
		User.createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Note.upgradeTable(db);
		User.upgradeTable(db);

	}

	public User insertUser(String login, String password, String name) {

		User user = getUser(login, password);
		if (user != null) {
			return user;
		}

		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(User.COLUMN_NAME, name);
		cv.put(User.COLUMN_LOGIN, login);
		cv.put(User.COLUMN_PASSWORD, password);
		long insert = db.insert(User.TABLE_NAME, null, cv);
		db.close();
		if (insert == -1) {
			return null;
		}

		user = getUser(login, password);
		return user;
	}

	public User getUser(String login, String password) {
		User user = null;

		SQLiteDatabase db = getWritableDatabase();
		String selection = User.COLUMN_LOGIN + "='" + login + "' and "
				+ User.COLUMN_PASSWORD + "='" + password + "'";
		Log.d("selection", selection);
		Cursor cursor = db.query(User.TABLE_NAME, null, selection, null, null,
				null, null);
		if (cursor.moveToFirst() && cursor.getCount() == 1) {
			user = new User(cursor);
		}
		db.close();
		return user;
	}

	public User getUser(int id) {
		User user = null;

		SQLiteDatabase db = getWritableDatabase();
		String selection = User.COLUMN_ID + "='" + id + "'";
		Log.d("selection", selection);
		Cursor cursor = db.query(User.TABLE_NAME, null, selection, null, null,
				null, null);
		if (cursor.moveToFirst() && cursor.getCount() == 1) {
			user = new User(cursor);
		}
		db.close();
		return user;
	}

	public boolean insertNote(Note note) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(Note.COLUMN_TITLE, note.getTitle());
		cv.put(Note.COLUMN_BODY, note.getBody());
		cv.put(Note.COLUMN_URL, note.getUrl());
		cv.put(Note.COLUMN_USER_ID, note.getUser_id());
		long insert = db.insert(Note.TABLE_NAME, null, cv);
		db.close();
		if (insert == -1) {
			return false;
		}
		return true;
	}

	public boolean updateNote(Note note) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(Note.COLUMN_TITLE, note.getTitle());
		cv.put(Note.COLUMN_BODY, note.getBody());
		cv.put(Note.COLUMN_URL, note.getUrl());
		int rows = db.update(Note.TABLE_NAME, cv,
				Note.COLUMN_ID + "='" + note.getId() + "'", null);
		db.close();
		if (rows > 0) {
			return true;
		}
		return false;
	}

	public boolean deleteNote(int id) {
		SQLiteDatabase db = getWritableDatabase();
		int rows = db.delete(Note.TABLE_NAME, Note.COLUMN_ID + "='" + id + "'",
				null);
		db.close();
		if (rows > 0) {
			return true;
		}
		return false;
	}

	public Note getNote(String title, int userId) {
		Note note = null;

		SQLiteDatabase db = getWritableDatabase();
		String selection = Note.COLUMN_TITLE + "=? AND " + Note.COLUMN_USER_ID
				+ "=?";
		String[] selectionArgs = new String[] { title, String.valueOf(userId) };
		Cursor cursor = db.query(Note.TABLE_NAME, null, selection,
				selectionArgs, null, null, null);
		if (cursor.moveToFirst() && cursor.getCount() == 1) {
			note = new Note(cursor);
		}
		db.close();
		return note;
	}

	public Note getNote(int id) {
		Note note = null;

		SQLiteDatabase db = getWritableDatabase();
		String selection = Note.COLUMN_ID + "='" + id + "'";
		Cursor cursor = db.query(Note.TABLE_NAME, null, selection, null, null,
				null, null);
		if (cursor.moveToFirst() && cursor.getCount() == 1) {
			note = new Note(cursor);
		}
		db.close();
		return note;
	}

	public ArrayList<Note> getNotes(int userId) {
		ArrayList<Note> notes = new ArrayList<Note>();
		SQLiteDatabase db = getWritableDatabase();
		String selection = Note.COLUMN_USER_ID + "='" + userId + "'";
		Cursor cursor = db.query(Note.TABLE_NAME, null, selection, null, null,
				null, null);
		if (cursor.moveToFirst() && cursor.getCount() > 0) {
			do {
				notes.add(new Note(cursor));
			} while (cursor.moveToNext());

		}
		db.close();
		return notes;

	}

}
