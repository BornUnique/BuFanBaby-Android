package com.bufanbaby.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BufanService extends Service {
	private final IBinder binder = new BinderToActivity();

	public BufanService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	private class BinderToActivity extends Binder {
		BufanService getService() {
			return BufanService.this;
		}
	}
}
