package com.nerdability.android.adapter;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nerdability.android.R;
import com.nerdability.android.rss.domain.Article;
import com.nerdability.android.util.DateUtils;


public class ArticleListAdapter extends ArrayAdapter<Article> {
	
	private Activity myContext;
	
	public ArticleListAdapter(Activity activity, List<Article> articles) {
		super(activity, 0, articles);
		myContext = activity;
	}


	static class ViewHolder{
		TextView textView, dateView;
		LinearLayout row;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		
		if(convertView == null){
			LayoutInflater inflater = myContext.getLayoutInflater();
			convertView = inflater.inflate(R.layout.fragment_article_list, null);
			
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView.findViewById(R.id.article_title_text);
			viewHolder.dateView = (TextView) convertView.findViewById(R.id.article_listing_smallprint);
			viewHolder.row = (LinearLayout) convertView.findViewById(R.id.article_row_layout);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		Article article = getItem(position);
		if(article != null){
			viewHolder.textView.setText(article.getTitle());
			
			String pubDate = article.getPubDate();
			SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z", Locale.ENGLISH);
			Date pDate;
			try {
				pDate = df.parse(pubDate);
				pubDate = "published " + DateUtils.getDateDifference(pDate) + " by " + article.getAuthor();
			} catch (ParseException e) {
				Log.e("DATE PARSING", "Error parsing date..");
				pubDate = "published by " + article.getAuthor();
			}
			viewHolder.dateView.setText(pubDate);
			
			if (!article.isRead()){
				viewHolder.row.setBackgroundColor(Color.WHITE);
				viewHolder.textView.setTypeface(Typeface.DEFAULT_BOLD);
			}
		}
		return convertView;

	} 
}
