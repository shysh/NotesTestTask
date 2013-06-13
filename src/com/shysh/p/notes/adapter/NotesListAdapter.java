package com.shysh.p.notes.adapter;

import java.util.ArrayList;

import com.shysh.p.notes.R;
import com.shysh.p.notes.database.entity.Note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class NotesListAdapter extends BaseAdapter {

	private ArrayList<Note> notes = new ArrayList<Note>();
	private Context context;
	private LayoutInflater inflanter;
	
	public NotesListAdapter(Context context, ArrayList<Note> data){
		this.context = context;
		this.notes = data;
		inflanter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notes.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return notes.get(arg0);
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return notes.get(index).getId();
	}

	@Override
	public View getView(int position, View contentView, ViewGroup parent) {
		ViewHolder holder;
		
		if(contentView == null){
			contentView = inflanter.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.title = (TextView)contentView.findViewById(R.id.list_item_title);
			contentView.setTag(holder);
		}else{
			holder = (ViewHolder)contentView.getTag();
		}
		
		if(holder.title!=null){
			holder.title.setText(notes.get(position).getTitle());
		}
		return contentView;
	}
	
	private static class ViewHolder{
		TextView title;
	}

}
