package com.bufanbaby.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bufanbaby.android.model.CommonConsts;
import com.bufanbaby.android.model.Deed;
import com.bufanbaby.android.service.BufanClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


public class PublishActivity extends AbstractGenericActivity {
	public static final int REQ_PICK_IMAGE = 1;
	public static final int REQ_PICK_TAG = 2;

	PublishActivity self;

	ImageView imgDeed;
	Button btnSelectTag;
	Button btnPublish;
	Button btnPickImage;

	EditText txtDeedDesc;
	TextView txtTagSelected;
	Bitmap selectedBmp;
	Uri selectedImageFile;

	List<String> tags;

	private class PublishRespHandler extends BroadcastReceiver {
		@Override
		public void onReceive(Context ctx, Intent intent) {
			String result = intent.getStringExtra(CommonConsts.IntentResult);
			Toast.makeText(self, result, Toast.LENGTH_LONG).show();
		}
	}

	private void initControls() {
		imgDeed = (ImageView) findViewById(R.id.imgDeed);
		btnSelectTag = (Button) findViewById(R.id.btnSelectTag);
		btnPublish = (Button) findViewById(R.id.btnNewDeed);
		btnPickImage = (Button) findViewById(R.id.btnPickImage);
		txtDeedDesc = (EditText) findViewById(R.id.txtDeedDesc);
		txtTagSelected = (TextView) findViewById(R.id.txtTagSelected);

		btnSelectTag.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent pickTag = new Intent(self, TagActivity.class);
				startActivityForResult(pickTag, REQ_PICK_TAG);
			}
		});

		btnPublish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Deed deed = new Deed();
				deed.setContent(txtDeedDesc.getText().toString());
				deed.setTags(tags);
				deed.setCreateDate(new Date());
				deed.setImageId("");
				BufanClient.doPost(self, deed);
			}
		});

		btnPickImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startGallery();
			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish);
		self = this;

		super.listenToBroadcast(self, new PublishRespHandler());
		initControls();

		Drawable imgDraw = imgDeed.getDrawable();
		if (imgDraw instanceof BitmapDrawable) {
			selectedBmp = ((BitmapDrawable) imgDraw).getBitmap();
		}

		if (null == selectedBmp) {
			startGallery();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQ_PICK_IMAGE:
			if (Activity.RESULT_OK == resultCode) {
				if (null != selectedBmp) {
					selectedBmp.recycle();
					selectedBmp = null;
					imgDeed.setImageDrawable(null);
				}
				selectedImageFile = data.getData();
				InputStream is = null;
				try {
					is = getContentResolver().openInputStream(selectedImageFile);
					selectedBmp = BitmapFactory.decodeStream(is);
					imgDeed.setImageBitmap(selectedBmp);
				} catch (FileNotFoundException e) {
					Log.w(getClass().getSimpleName(), "" + e);
				} finally {
					if (null != is) {
						try {
							is.close();
						} catch (IOException e) {
							Log.w(getClass().getSimpleName(), "" + e);
						}
					}
				}
			}
			break;
		case REQ_PICK_TAG:
			if(Activity.RESULT_OK == resultCode) {
				tags = data.getStringArrayListExtra("tags");
			}
			break;
		}
	}

	protected void startGallery() {
		Intent gal = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		gal.setType("image/*");
		startActivityForResult(gal, REQ_PICK_IMAGE);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_publish, menu);
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
}
