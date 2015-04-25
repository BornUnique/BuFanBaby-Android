package com.bufanbaby.android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bufanbaby.android.model.TopicTag;
import com.bufanbaby.android.service.BufanClient;
import com.bufanbaby.android.ui.TagListAdapter;

import java.util.ArrayList;
import java.util.List;


public class TagActivity extends AbstractGenericActivity {

	TagActivity self;
	Button btnAddTag;
	ListView lstTags;

	private List<TopicTag> tags = new ArrayList<TopicTag>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		self = this;

		btnAddTag = (Button) findViewById(R.id.btnAddTag);
		lstTags = (ListView) findViewById(R.id.lstTags);

		btnAddTag.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				reloadTags();
			}
		});

		reloadTags();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_tag, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void reloadTags() {
		tags.clear();
		LoadTagListTask task = new LoadTagListTask();
		task.execute("");
	}

	private class LoadTagListTask extends AsyncTask<String, Integer, Integer> {

		@Override
		protected Integer doInBackground(String... strings) {
			String userId = strings[0];

			BufanClient.doSearch(self, "tags", "user=" + userId);

			TagListAdapter adapter = new TagListAdapter(self, tags);
			lstTags.setAdapter(adapter);
			return null;
		}
	}
}
