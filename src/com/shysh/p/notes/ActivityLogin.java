package com.shysh.p.notes;

import com.shysh.p.notes.database.SQLiteDBHelper;
import com.shysh.p.notes.database.entity.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityLogin extends Activity implements OnClickListener{
	
	private SQLiteDBHelper dbHelper;
	
	private EditText loginEdTxt;
	private EditText passwordEdTxt;
	private Button loginBtn, signBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		loginEdTxt = (EditText)findViewById(R.id.activity_login_login_edit);
		passwordEdTxt = (EditText)findViewById(R.id.activity_login_password_edit);
		
		loginBtn = (Button)findViewById(R.id.activity_login_login_btn);
		loginBtn.setOnClickListener(this);
		signBtn = (Button)findViewById(R.id.activity_login_sign_in_btn);
		signBtn.setOnClickListener(this);
		
		if(savedInstanceState!=null){
			loginEdTxt.setText(savedInstanceState.getString("login"));
			passwordEdTxt.setText(savedInstanceState.getString("password"));
		}
		
		dbHelper = new SQLiteDBHelper(this);
	}
	
	@Override 
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putString("password", passwordEdTxt.getText().toString());
		outState.putString("login", loginEdTxt.getText().toString());
	}
	
	public void login(){
		User user = new User();
		if(!loginEdTxt.getText().toString().equals("") && !passwordEdTxt.getText().toString().equals("")){
			user = dbHelper.getUser(loginEdTxt.getText().toString(), passwordEdTxt.getText().toString());
		}
		if(user!=null){
			Intent intent = new Intent(this, ActivityNotesList.class);
			intent.putExtra("user_id", user.getId());
			startActivity(intent);
			finish();
		}else{
			Toast.makeText(this, getResources().getString(R.string.Login_error), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void signIn(){
		Intent intent = new Intent(this, ActivitySigIn.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_login_login_btn:
			login();
			break;
		case R.id.activity_login_sign_in_btn:
			signIn();
			break;

			
		default:
			break;
		}
		
	}

}
