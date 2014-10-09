package com.codepath.apps.basictwitter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapater extends ArrayAdapter<Tweet> {
	View v;
	Tweet tweet;
	ImageView ivProfileImage;
	public TweetArrayAdapater(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		tweet = getItem(position);
		
		
		if(convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvTime = (TextView) v.findViewById(R.id.tvTime);
		tvUserName.setText(tweet.getUser().getScreenName());
		tvBody.setText(tweet.getBody());
		
		ivProfileImage.setTag(tweet.getUser().getScreenName());
		ivProfileImage.setImageResource(0);
		ivProfileImage.setClickable(true);
	
		ivProfileImage.setOnClickListener( new OnClickListener() {	
			ImageView ivProfile = (ImageView) v.findViewById(R.id.ivProfileImage);
			@Override
			public void onClick(View v) {
				if (ivProfile != null) {
					String user = ivProfileImage.getTag().toString();
					Log.d("NAVEEN", "Name in adapter : " + user);
					Intent intent = new Intent().setClass(v.getContext(), ProfileActivity.class);
					intent.putExtra("username", user);
					v.getContext().startActivity(intent);
				}
				
			}
		});
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageURL(), ivProfileImage);

		String relativeTime = getRelativeTimeAgo(tweet.getCreatedAt());
		tvTime.setText(relativeTime);
		return v;
	}
	// getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
	
	

}
