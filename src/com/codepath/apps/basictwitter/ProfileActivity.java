package com.codepath.apps.basictwitter;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	
	private String screenName;
	private String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		username = (String)getIntent().getStringExtra("username");
		//Toast.makeText(this, "username = " + username, Toast.LENGTH_SHORT).show();
		
		TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray json) {
				//Log.d("naveen1", json.toString());
				//insert(Tweet.fromJson(json), 0);
				//aTweets.notifyDataSetChanged();
				//populateTimeLine();
				User u;
				try {
					u = User.fromJSON(json.getJSONObject(0));
					getActionBar().setTitle("@" + u.getScreenName());
					TextView tvName = (TextView) findViewById(R.id.tvUserName);
					
							screenName = json.getJSONObject(0).getString("screen_name");
							tvName.setText(screenName);
							//myProfilePicURL = json.getString("profile_image_url");

								// Log.d("naveen", myScreenName);
								// Log.d("naveen", myProfilePicURL);
					
					populateProfileHeader(u);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Log.d("NAVEEN", json.toString());

			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, username);
		
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTimelineFragment Userfragment = UserTimelineFragment.newInstance(username);
		//Userfragment.populate();
		ft.replace(R.id.fragmentUserTimeline, Userfragment);
		ft.commit();
		
		
		
	}
	public String getUserNameFromProfile(){
		return username;
	}
	public void populateProfileHeader(User user) {
		//Toast.makeText(this, "I am here", Toast.LENGTH_SHORT).show();
		TextView tvName = (TextView) findViewById(R.id.tvUserName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
		
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(String.valueOf(user.getFollowersCount()) + " Followers");
		tvFollowing.setText(String.valueOf(user.getFollowingCount()) + " Following");
		
		ImageLoader.getInstance().displayImage(user.getProfileImageURL(),ivProfileImage );
		//tvFollowers.setText(u.getFollowersCount());
	}
}
