package com.l.j.dialog;

import com.example.mytest.R;
import com.l.j.view.LProgressView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

public class ProgressDialog extends Dialog {
	/**
	 * ��������
	 * ����100
	 */
	private int allInt = 0;
	/**
	 * ���������
	 * ���鲻Ҫ���ڽ�������
	 */
	private int havingInt = 0;
	/**
	 * ���ȿؼ�
	 */
	private LProgressView progressView;
	
	public ProgressDialog(Context context, int allInt, int havingInt) {
		super(context);
		this.allInt = allInt;
		this.havingInt = havingInt;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ����Ļtitle
		setContentView(R.layout.dialog_progress);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		progressView = (LProgressView) findViewById(R.id.dialog_progress_pro);
		progressView.setAllInt(allInt);
		progressView.setHavingInt(havingInt);
	}
	/**
	 * ��ȡ��������ɫ
	 * @return
	 */
	public int getProColor() {
		return progressView.getProColor();
	}

	/**
	 * ���ý�������ɫ
	 * @param proColor
	 */
	public void setProColor(int proColor) {
		progressView.setProColor(proColor);
	}

	/**
	 * ��ȡ������ɫ
	 * @return
	 */
	public int getTextColor() {
		return progressView.getTextColor();
	}

	/**
	 * ����������ɫ
	 * @param textColor
	 */
	public void setTextColor(int textColor) {
		progressView.setTextColor(textColor);
	}

	/**
	 * ��ȡ������ɫ
	 * @return
	 */
	public int getBgColor() {
		return progressView.getBgColor();
	}

	/**
	 * ���ñ�����ɫ
	 * @param bgColor
	 */
	public void setBgColor(int bgColor) {
		progressView.setBgColor(bgColor);
	}

	/**
	 * ��ȡ����
	 * @return
	 */
	public int getAllInt() {
		return progressView.getAllInt();
	}

	/**
	 * ��������
	 * @param allInt
	 */
	public void setAllInt(int allInt) {
		progressView.setAllInt(allInt);
	}

	/**
	 * ��ȡ�Ѿ���ɽ���
	 * @return
	 */
	public int getHavingInt() {
		return progressView.getHavingInt();
	}

	/**
	 * ��������ɽ���
	 * @param havingInt
	 */
	public void setHavingInt(int havingInt) {
		progressView.setHavingInt(havingInt);
	}

	/**
	 * ��ȡ�Ƿ���ʾ����������
	 * @return
	 */
	public boolean isProHaveBg() {
		return progressView.isProHaveBg();
	}

	/**
	 * �����Ƿ���ʾ����������
	 * @param proHaveBg
	 */
	public void setProHaveBg(boolean proHaveBg) {
		progressView.setProHaveBg(proHaveBg);
	}
}
