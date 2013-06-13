package com.shysh.p.notes;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shysh.p.notes.adapter.NotesListAdapter;
import com.shysh.p.notes.database.SQLiteDBHelper;
import com.shysh.p.notes.database.entity.Notes;
import com.shysh.p.notes.database.entity.Note;
import com.shysh.p.notes.database.entity.User;
import com.shysh.p.notes.utils.Utils;

public class ActivityNotesList extends Activity implements OnClickListener, OnItemClickListener {

	private int user_id = 0;
	private ListView list;
	private TextView userNameTxt, noItemsTxt;
	private Button addNoteBtn, updateBtn;
	private SQLiteDBHelper dbHelper;
	private NotesListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		userNameTxt = (TextView) findViewById(R.id.user_name);
		noItemsTxt = (TextView) findViewById(R.id.no_items);
		addNoteBtn = (Button) findViewById(R.id.add_note);
		addNoteBtn.setOnClickListener(this);
		updateBtn = (Button)findViewById(R.id.update);
		updateBtn.setOnClickListener(this);
		updateBtn.setVisibility(Button.GONE);
		list = (ListView) findViewById(R.id.list);
		list.setOnItemClickListener(this);
		dbHelper = new SQLiteDBHelper(this);

		if (savedInstanceState != null) {
			user_id = savedInstanceState.getInt("user_id", 0);
		} else {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				user_id = extras.getInt("user_id", 0);
			}
		}

		User user = dbHelper.getUser(user_id);
		userNameTxt.setText(user.getName());
		updateList();
		Log.d("user_id", "" + user_id);
	}

	public void updateList() {
		ArrayList<Note> data = dbHelper.getNotes(user_id);
		if (data.size() == 0) {
			noItemsTxt.setVisibility(TextView.VISIBLE);
		} else {
			noItemsTxt.setVisibility(TextView.GONE);
			adapter = new NotesListAdapter(this, data);
			list.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("user_id", user_id);
	}

	private void editNote(boolean isEdit, int noteId) {
		Intent intent = new Intent(this, ActivityNoteEdit.class);
		intent.putExtra("user_id", user_id);
		intent.putExtra("note_id", noteId);
		intent.putExtra("edit", isEdit);

		startActivityForResult(intent, 1);
	}
	
	public void updateNotes(){
		new UpdateTask().execute();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			updateList();
		} else {

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_note:
			editNote(false, 0);
			break;

		case R.id.update:
			updateNotes();
			break;
		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> adp, View contentView, int position, long id) {
		editNote(true, (int)id);
		
	}
	
	private class UpdateTask extends AsyncTask<Void, Void, Boolean>{

		@Override
		public void onPreExecute(){
			super.onPreExecute();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			String notesStr = Utils.getFileFromAssetAsString("mock_data.txt", getBaseContext());
			Gson gson = new Gson();
			Notes notes = gson.fromJson(notesStr, Notes.class);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Boolean result){
			super.onPostExecute(result);
		}
		
		
	}

}
