package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {

	private TwitterClient client;
	private String myScreenName;
	private String myProfilePicURL;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		client  = TwitterApplication.getRestClient();
		myProfileInfo();
		populateTimeLine();
	}
	private void myProfileInfo() {
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
		
	}
	public void customLoadMoreDataFromApi() {
		return ;
	}
	public void fetchTimelineAsync() {
		return;
	}

	
	public void populateTimeLine() {
		aTweets.clear();
		//Toast.makeText(this, "max_id = "+ Tweet.max_id , Toast.LENGTH_SHORT).show();
		client.getMentionsTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				//Log.d("naveen2", json.toString());
				addAll(Tweet.fromJSONArray(json));
				//Log.d("Naveen", json.toString());
				//Toast.makeText(getActivity(), "Sucess" + json.toString(), Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

}
