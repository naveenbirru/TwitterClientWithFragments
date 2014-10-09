package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.basictwitter.ProfileActivity;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	private TwitterClient client;
	private String myScreenName;
	private static String name;
	private String myProfilePicURL;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		client  = TwitterApplication.getRestClient();		
		//myScreenName = getArguments().getString("name", "");
		
		//myProfileInfo();
		//aTweets.clear();
		populate();
		
	}

	public static UserTimelineFragment newInstance(String username) {
		Log.d("naveen", "UserTimelineFragment = " + username);
		UserTimelineFragment userFrag = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putString("name", username);
		name = username;
		userFrag.setArguments(args);
		//userFrag.populate();
		return userFrag;
	}
	
	/*private void myProfileInfo() {
		client.getMyProfileInfo(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				//Log.d("naveen", json.toString());
				//aTweets.addAll(Tweet.fromJSONArray(json));
				try {
					myScreenName = json.getString("screen_name");
					myProfilePicURL = json.getString("profile_image_url");
					
					//Log.d("naveen", myScreenName);
					//Log.d("naveen", myProfilePicURL);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
		
	}*/
	//Toast.makeText(getActivity(), "I am here", Toast.LENGTH_SHORT).show();
	//Toast.makeText(getActivity(), "Sucess" + json.toString(), Toast.LENGTH_SHORT).show();

	
	public void populate() {
		
		aTweets.clear();
		//Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
		//Toast.makeText(this, "max_id = "+ Tweet.max_id , Toast.LENGTH_SHORT).show();
		client.getUserTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				//Toast.makeText(getActivity(), "I am here", Toast.LENGTH_SHORT).show();
				//Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
				//Log.d("naveen2", json.toString());
				addAll(Tweet.fromJSONArray(json));
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, name);
	}


}
