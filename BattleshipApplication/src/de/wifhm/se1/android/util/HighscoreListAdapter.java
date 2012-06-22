package de.wifhm.se1.android.util;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.wifhm.se1.android.activity.R;
import de.wifhm.se1.android.common.User;

public class HighscoreListAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private List<User> highscorelist;
	
	public HighscoreListAdapter(Context context, List<User> highscorelist){
		this.inflater = LayoutInflater.from(context);
		this.highscorelist = highscorelist;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.highscorelist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.highscorelist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		View v = convertView;
		Log.d("Test", " " + highscorelist.size());
		User highscore = this.highscorelist.get(position);
		if(v == null){
			holder = new ViewHolder();
			
			v = inflater.inflate(R.layout.highscorelist_item, null);
			
			holder.username = (TextView)v.findViewById(R.id.highscoreuser);
			holder.highscorepoints = (TextView)v.findViewById(R.id.highscorepoints);
			
			v.setTag(holder);
			
		}
		else{
			holder = (ViewHolder) v.getTag();
		}
		Log.d("Test", "Username: " + highscore.getUsername());
		Log.d("Test", "Points: " + highscore.getHighscore());
		holder.username.setText(highscore.getUsername());
		holder.highscorepoints.setText("Punkte: " + highscore.getHighscore().toString());
		return v;
	}
	
	static class ViewHolder{
		TextView username, highscorepoints;
	}

}
