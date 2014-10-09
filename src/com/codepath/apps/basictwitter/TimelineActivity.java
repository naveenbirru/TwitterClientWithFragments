package com.codepath.apps.basictwitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity {
	private TwitterClient client;
	private String myScreenName;
	private String myProfilePicURL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Log.d("INFO", "I am Here");
		setContentView(R.layout.activity_timeline);
		client  = TwitterApplication.getRestClient();
		setupTabs();
		myProfileInfo();
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
						HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
			    		MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose, menu);
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.miCompose:
			composeMessage();
			return true;
		case R.id.miProfile:
			profileView(item);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void composeMessage() {

		//Toast.makeText(this, "Compose ", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, ComposeActivity.class);
		// ImageResult result = imageResults.get(position);
		i.putExtra("screen_name", myScreenName);//need to serializable or parceble.
		i.putExtra("profile_image_url", myProfilePicURL);
		//startActivity(i);
		startActivityForResult(i, 20);
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == -1 && requestCode == 20) {

		   String composed_message = data.getExtras().getString("message");

			//Toast.makeText(this, "Composed Message ", Toast.LENGTH_SHORT).show();
		 	//View v = this.findViewById(android.R.id.content);
		 	//onImageSearch(v);
		 	postStatus(composed_message);
		 	HomeTimelineFragment homeFragment = (HomeTimelineFragment) getSupportFragmentManager().findFragmentByTag("home");
		 	if(homeFragment != null) {
		 		//Toast.makeText(this, "Composed Message ", Toast.LENGTH_SHORT).show();
		 		Tweet tweet;
		 		//homeFragment.getAdapter().insert(object, 0);
		 		homeFragment.populateTimeLine();
		 	} 
		 
	  }
	}

	private void postStatus(String message) {
		client.postStatus(new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject json) {
				//Log.d("naveen1", json.toString());
				//insert(Tweet.fromJson(json), 0);
				//aTweets.notifyDataSetChanged();
				//populateTimeLine();
			}
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, message);
		
	}
	
	public void profileView(MenuItem menu) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);	
	}
	
}
