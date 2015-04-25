package com.bufanbaby.android.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bufanbaby.android.R;
import com.bufanbaby.android.model.TopicTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter List with Checkbox
 */
public class TagListAdapter extends ArrayAdapter<TopicTag> {
	private class TagItemView {
		TextView txtDesc;
	}

	private LayoutInflater layout;
	private List<TopicTag> tags;

	public TagListAdapter(Context context, List<TopicTag> objects) {
		super(context, 0, objects);
		this.tags = new ArrayList<TopicTag>(objects);
		layout = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public TagListAdapter(Context context, int resource, List<TopicTag> objects) {
		super(context, resource, objects);
		this.tags = new ArrayList<TopicTag>(objects);
		layout = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TagItemView itemView = null;
		if(null == convertView) {
			convertView = layout.inflate(R.layout.itemview_tag, null);
			itemView = new TagItemView();
			itemView.txtDesc = (TextView) convertView.findViewById(R.id.txtTagDesc);
			convertView.setTag(itemView);

		} else {
			itemView = (TagItemView) convertView.getTag();
		}

		refreshItemView(itemView, position);

		return convertView;
	}

	private void refreshItemView(TagItemView itemView, int position) {
		TopicTag tag = getItem(position);
	}
}
