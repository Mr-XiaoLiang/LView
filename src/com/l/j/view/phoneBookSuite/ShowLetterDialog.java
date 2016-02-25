package com.l.j.view.phoneBookSuite;

import com.l.j.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
/**
 * 用来显示当前选中首字母的dialog
 * @author LiuJ
 * 电话本列表套件之一
 */
public class ShowLetterDialog extends Dialog {

	private TextView textView;
	private String letter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除屏幕title
		setContentView(R.layout.dialog_show_letter);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		textView = (TextView) findViewById(R.id.dialog_show_letter_text);
		textView.setText(letter);
	}
	public ShowLetterDialog(Context context,String letter) {
		super(context);
		this.letter = letter;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
		textView.setText(letter);
	}
	
}
