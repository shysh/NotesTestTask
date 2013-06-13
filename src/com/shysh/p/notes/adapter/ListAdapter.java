package com.shysh.p.notes.adapter;

import com.shysh.p.notes.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListAdapter extends CursorAdapter{

	private LayoutInflater layoutInflater;
	
	public ListAdapter(Context context, Cursor c) {
		super(context, c);
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder)view.getTag();
		if(holder.title!=null){
			
		}
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View view = layoutInflater.inflate(R.layout.list_item, parent);
		ViewHolder holder = new ViewHolder();
		holder.title = (TextView)view.findViewById(R.id.list_item_title);
		
		
		view.setTag(holder);
		return view;
	}
	
	private static class ViewHolder{
		TextView title;
	}

}
