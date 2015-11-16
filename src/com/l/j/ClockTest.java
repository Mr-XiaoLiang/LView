package com.l.j;

import com.l.j.view.LClockView;
import com.l.j.view.LClockView.ClockViewListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ClockTest extends Activity implements ClockViewListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LClockView clockView = new LClockView(this);
		setContentView(clockView);
		Intent intent = getIntent();
		clockView.setType(intent.getIntExtra("type", LClockView.TYPE_HOURS));
	}

	@Override
	public void onClockChange(int t) {
		Toast.makeText(this, ""+t, Toast.LENGTH_SHORT).show();
	}
}
