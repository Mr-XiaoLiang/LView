package com.l.j.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.example.mytest.R;

public class LoadDialog extends Dialog {
	
	public LoadDialog(Context context) {
		super(context);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// È¥³ýÆÁÄ»title
		setContentView(R.layout.dialog_load);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	}
}
