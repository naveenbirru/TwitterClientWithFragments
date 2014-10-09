package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.basictwitter.ComposeActivity;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	private TwitterClient client;
	
	private Boolean check_once = false;
	//private ListView lvTweets;

	private String composed_message;
	
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
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}*/



	   public void customLoadMoreDataFromApi_call() {
		      // This method probably sends out a network request and appends new data items to your adapter. 
		      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
		      // Deserialize API response and then construct new objects to append to the adapter
		    	/*AsyncHttpClient client = new AsyncHttpClient();
		    	*/
		    	
				//Tweet latest_tweet  = (Tweet) lvTweets.getItemAtPosition(0);
		    	//Toast.makeText(this, "max_id = "+ Tweet.max_id , Toast.LENGTH_SHORT).show();
				//Log.d("SinceID", "SinceID = " + latest_tweet.getUid()+1);

				client.getHomeTimeline(new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray json) {
						//Log.d("naveen2", json.toString());
						addAll(Tweet.fromJSONArray(json));
						
					}
					@Override
					public void onFailure(Throwable e, String s) {
						Log.d("debug", e.toString());
						Log.d("debug", s.toString());
					}
				}, 10, Tweet.max_id+1);
			}


	 void myProfileInfo() {
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
		customLoadMoreDataFromApi_call();
		return;
	}

	public void fetchTimelineAsync() {
		return;
	}

	public void fetchTimelineAsync(int page) {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			public void onSuccess(JSONArray json) {
				// ...the data has come back, finish populating listview...
				// Now we call onRefreshComplete to signify refresh has finished
				lvTweets.onRefreshComplete();
			}

			public void onFailure(Throwable e) {
				Log.d("DEBUG", "Fetch timeline error: " + e.toString());
			}
		}, 10, Tweet.max_id - 10000);
	}
	
	public void populateTimeLine() {
		aTweets.clear();
		//Toast.makeText(this, "max_id = "+ Tweet.max_id , Toast.LENGTH_SHORT).show();
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				//Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
				//Log.d("naveen2", json.toString());
				addAll(Tweet.fromJSONArray(json));
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, 10, Tweet.max_id+1);
	}

}
