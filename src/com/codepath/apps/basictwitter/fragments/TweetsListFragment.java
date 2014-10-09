package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.ProfileActivity;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapater;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TweetsListFragment extends Fragment {

	public ArrayList<Tweet> tweets;
	public ArrayAdapter<Tweet> aTweets;
	public PullToRefreshListView lvTweets;
	
	private TwitterClient client;
	
	  @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	  }

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapater(getActivity(),tweets);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//Inflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list,container, false);
		//Assign our view refrences.
		
		lvTweets = (PullToRefreshListView) v.findViewById(R.id.lvTweets);
		//lvTweets = (ListView) findViewById(R.id.lvTweets);
		lvTweets.setClickable(true);
		lvTweets.setAdapter(aTweets);

		lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call listView.onRefreshComplete() when
                // once the network request has completed successfully.
                fetchTimelineAsync();
            }
        });

		lvTweets.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(), ProfileActivity.class);
				Tweet tweet = tweets.get(position);
				// i.putExtra("tweet",tweet);
				// startActivity(i);

			}
		});

		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				customLoadMoreDataFromApi();

			}
		});

		
		//lvItems = ...
		//return layout.
		
		return v;
	}
	
	public void customLoadMoreDataFromApi() {
		return ;
	}
	public void fetchTimelineAsync() {
		return;
	}
	
	/*public void fetchTimelineAsync(int page) {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            public void onSuccess(JSONArray json) {
                // ...the data has come back, finish populating listview...
                // Now we call onRefreshComplete to signify refresh has finished
                lvTweets.onRefreshComplete();
            }

            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
        }, 10, Tweet.max_id-10000);
    }*/
	
	  // Append more data into the adapter
	public void addAll(ArrayList <Tweet> tweets) {
		//aTweets.clear();
		aTweets.addAll(tweets);
	}
	
	public ArrayAdapter<Tweet> getAdapter() {
		return aTweets;
	}
	
	public void insert(Tweet tweet, int index) {
		aTweets.insert(tweet, index);
	}

}
