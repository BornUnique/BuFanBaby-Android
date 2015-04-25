package com.bufanbaby.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * An activity provides additional facility
 */
public abstract class AbstractGenericActivity extends Activity {

	private Map<String, BroadcastReceiver> receivers = new HashMap();

	protected void listenToBroadcast(AbstractGenericActivity activity, BroadcastReceiver receiver) {
		Class<? extends AbstractGenericActivity> clazz = activity.getClass();
		IntentFilter filter = new IntentFilter(clazz.getCanonicalName());
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		for(BroadcastReceiver recv: receivers.values()) {
			unregisterReceiver(recv);
		}
		receivers.clear();
	}
}
