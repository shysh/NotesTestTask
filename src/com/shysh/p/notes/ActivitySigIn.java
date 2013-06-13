package com.shysh.p.notes;

import com.shysh.p.notes.database.SQLiteDBHelper;
import com.shysh.p.notes.database.entity.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivitySigIn extends Activity implements
		android.view.View.OnClickListener {

	private SQLiteDBHelper dbHelper;

	private EditText loginEdTxt;
	private EditText passwordEdTxt;
	private EditText password2EdTxt;
	private EditText nameEdTxt;
	private Button signBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		loginEdTxt = (EditText) findViewById(R.id.activity_login_login_edit);
		passwordEdTxt = (EditText) findViewById(R.id.activity_login_password_edit);
		password2EdTxt = (EditText) findViewById(R.id.activity_login_password2_edit);
		nameEdTxt = (EditText) findViewById(R.id.activity_login_name_edit);
		signBtn = (Button) findViewById(R.id.activity_login_sign_in_btn);
		signBtn.setOnClickListener(this);

		if (savedInstanceState != null) {
			loginEdTxt.setText(savedInstanceState.getString("login"));
			passwordEdTxt.setText(savedInstanceState.getString("password"));
			password2EdTxt.setText(savedInstanceState.getString("password2"));
			nameEdTxt.setText(savedInstanceState.getString("name"));
		}

		dbHelper = new SQLiteDBHelper(this);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("password", passwordEdTxt.getText().toString());
		outState.putString("password2", password2EdTxt.getText().toString());
		outState.putString("login", loginEdTxt.getText().toString());
		outState.putString("name", nameEdTxt.getText().toString());
	}

	public void signIn() {
		User user = null;
		Log.d("name", nameEdTxt.getText().toString());
		Log.d("login", loginEdTxt.getText().toString());
		Log.d("pass1", passwordEdTxt.getText().toString());
		Log.d("pass2", passwordEdTxt.getText().toString());

		if (!loginEdTxt.getText().toString().equals("")
				&& !passwordEdTxt.getText().toString().equals("")
				&& !password2EdTxt.getText().toString().equals("")
				&& !nameEdTxt.getText().toString().equals("")
				&& (passwordEdTxt.getText().toString().equals(password2EdTxt
						.getText().toString()))) {
			user = dbHelper.insertUser(loginEdTxt.getText().toString(),
					passwordEdTxt.getText().toString(), nameEdTxt.getText()
							.toString());
		} 
		if (user != null) {
			Intent intent = new Intent(this, ActivityNotesList.class);
			intent.putExtra("user_id", user.getId());
			startActivity(intent);
			finish();
		}else {
			Toast.makeText(this,
					getResources().getString(R.string.Sign_in_error),
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_login_sign_in_btn:
			signIn();
			break;

		default:
			break;
		}

	}
}
