package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String name;
	private long uid;
	private String screenName;
	private String tag_line;
	private String profileImageURL;
	private int following_count;
	private int followers_count;
	private int num_tweets;
	
	public static User fromJSON(JSONObject jsonObject) {
		User u = new User();
		//Extract values from json to populate the member variables
		try {
			u.name = jsonObject.getString("name");
			u.uid = jsonObject.getLong("id");
			u.screenName = jsonObject.getString("screen_name");
			u.profileImageURL = jsonObject.getString("profile_image_url");
			u.followers_count = jsonObject.getInt("followers_count");
			u.following_count = jsonObject.getInt("friends_count");
			u.num_tweets = jsonObject.getInt("statuses_count");
			u.tag_line = jsonObject.getString("description");
		
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
		
		return u;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageURL() {
		return profileImageURL;
	}
	public String getTagline() {
		return tag_line;
	}
	public int getFollowersCount() {
		return followers_count;
	}
	public int getFollowingCount() {
		return following_count;
	}
	public int getNUmTweets() {
		return num_tweets;
	}

}


