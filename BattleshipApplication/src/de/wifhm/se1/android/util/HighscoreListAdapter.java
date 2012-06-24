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
	private List<String> highscorelist;
	
	public HighscoreListAdapter(Context context, List<String> highscorelist){
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
		String highscore = this.highscorelist.get(position);
		String[] temp = highscore.split(";");
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
		
		try{
			Log.d("Test", "Username: " + temp[0]);
			Log.d("Test", "Points: " + temp[1]);
			holder.username.setText(temp[0]);
			holder.highscorepoints.setText("Punkte: " + temp[1]);
		}
		catch(Exception ex)
		{
			holder.username.setText("tmpSpieler");
			holder.highscorepoints.setText("Punkte: 53");
		}
	
		
		
		
		return v;
	}
	
	static class ViewHolder{
		TextView username, highscorepoints;
	}

}
