package com.bufanbaby.android.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * A simple wrapper adds Checkable interface to LinearLayout
 */
public class CheckableLinearLayout extends LinearLayout implements Checkable {

	private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

	private boolean checkedState = false;

	public CheckableLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		int [] states = super.onCreateDrawableState(extraSpace + 1);
		if(isChecked()) {
			mergeDrawableStates(states, CHECKED_STATE_SET);
		}
		return states;
	}

	@Override
	public void setChecked(boolean b) {
		if(b != checkedState) {
			checkedState = b;
			refreshDrawableState();
		}
	}

	@Override
	public boolean isChecked() {
		return checkedState;
	}

	@Override
	public void toggle() {
		setChecked(!checkedState);
	}
}
