package de.wifhm.se1.android.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import de.wifhm.se1.android.common.Highscore;

public class HighscoreListAdapter extends BaseAdapter {
	
	private List<Highscore> highscorelist;
	private LayoutInflater infalter;
	
	public HighscoreListAdapter(Context context, List<Highscore> highscorelist){
		this.highscorelist = highscorelist;
		this.infalter = LayoutInflater.from(context);
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	static class ViewHolder{
		TextView username, points;
	}
}
