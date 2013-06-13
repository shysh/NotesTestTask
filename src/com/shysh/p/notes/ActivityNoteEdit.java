package com.shysh.p.notes;

import com.google.gson.Gson;
import com.shysh.p.notes.database.SQLiteDBHelper;
import com.shysh.p.notes.database.entity.Note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityNoteEdit extends Activity implements OnClickListener {

	private int user_id;
	private int note_id;
	private boolean isEdit;
	private EditText titleTxt, linkTxt, bodyTxt;
	private Button closeBtn, saveBtn, deleteBtn;
	private Note note;

	private SQLiteDBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);

		closeBtn = (Button) findViewById(R.id.ectivity_edit_close);
		closeBtn.setOnClickListener(this);
		saveBtn = (Button) findViewById(R.id.ectivity_edit_save);
		saveBtn.setOnClickListener(this);
		deleteBtn = (Button) findViewById(R.id.ectivity_edit_delete);
		deleteBtn.setOnClickListener(this);

		titleTxt = (EditText) findViewById(R.id.ectivity_edit_title);
		linkTxt = (EditText) findViewById(R.id.ectivity_edit_link);
		bodyTxt = (EditText) findViewById(R.id.ectivity_edit_body);

		dbHelper = new SQLiteDBHelper(this);
		if (savedInstanceState != null) {
			user_id = savedInstanceState.getInt("user_id", 0);
			note_id = savedInstanceState.getInt("note_id", 0);
			isEdit = savedInstanceState.getBoolean("edit", false);

			titleTxt.setText(savedInstanceState.getString("title", ""));
			linkTxt.setText(savedInstanceState.getString("link", ""));
			bodyTxt.setText(savedInstanceState.getString("body", ""));
		} else {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				user_id = extras.getInt("user_id", 0);
				note_id = extras.getInt("note_id", 0);
				isEdit = extras.getBoolean("edit", false);
			}
		}

		if (isEdit && note_id != 0) {
			deleteBtn.setVisibility(Button.VISIBLE);
			note = dbHelper.getNote(note_id);
			if (note != null) {
				titleTxt.setText(note.getTitle());
				linkTxt.setText(note.getUrl());
				bodyTxt.setText(note.getBody());
			}
		} else {
			note = new Note();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("user_id", user_id);
		outState.putInt("note_id", note_id);
		outState.putBoolean("edit", isEdit);

		outState.putString("title", titleTxt.getText().toString());
		outState.putString("link", linkTxt.getText().toString());
		outState.putString("body", bodyTxt.getText().toString());
	}

	private void saveNote() {

		if (!titleTxt.getText().toString().equals("")
				&& !bodyTxt.getText().toString().equals("")) {
			note.setId(note_id);
			note.setUser_id(user_id);
			note.setTitle(titleTxt.getText().toString());
			note.setUrl(linkTxt.getText().toString());
			note.setBody(bodyTxt.getText().toString());
			Intent intent = new Intent();
			if (isEdit) {
				dbHelper.updateNote(note);
				 
			} else {
				dbHelper.insertNote(note);
			}
			Gson gson = new Gson();
			String json  = gson.toJson(note, Note.class);
			Log.d("json",json);
			setResult(RESULT_OK, intent);
			finish();
		}else{
			Toast.makeText(this, getResources().getString(R.string.Edit_note_warning), Toast.LENGTH_SHORT).show();
		}
	}
	
	private void deleteNote(){
		dbHelper.deleteNote(note_id);
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ectivity_edit_close:
			setResult(RESULT_CANCELED);
			finish();
			break;
		case R.id.ectivity_edit_save:
			saveNote();
			break;

		case R.id.ectivity_edit_delete:
			deleteNote();
			break;

		default:
			break;
		}

	}

}
