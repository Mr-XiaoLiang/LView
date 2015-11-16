package com.l.j;

import com.l.j.dialog.CalendarDialog;
import com.l.j.dialog.LoadDialog;
import com.l.j.dialog.ProgressDialog;
import com.l.j.dialog.CalendarDialog.CalendarDialogListener;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class DialogUtil {

	private double dialogWidthProportion = 0.7;
	private double dialogHeightProportion = 0.4;

	/**
	 * ��ȡһ��û�����Ƶ�����ѡ��Ի���
	 * 
	 * @param context
	 * @param month
	 * @param year
	 * @param day
	 * @param lis
	 * @return
	 */
	public Dialog getCalendarDialog(Activity context, int month, int year,
			int day, CalendarDialogListener lis) {
		CalendarDialog dialog = new CalendarDialog(context, month, year, day,
				lis);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER); // �˴���������dialog��ʾ��λ��
		dialog.setCancelable(true);
		dialog.show();
		WindowManager m = context.getWindowManager();
		Display d = m.getDefaultDisplay(); // ��ȡ��Ļ������
		WindowManager.LayoutParams p = window.getAttributes(); // ��ȡ�Ի���ǰ�Ĳ���ֵ
		p.height = (int) (d.getHeight() * dialogHeightProportion);
		p.width = (int) (d.getWidth() * dialogWidthProportion);
		dialog.onWindowAttributesChanged(p);
		window.setAttributes(p);
		return dialog;
	}
	/**
	 * ��ȡһ�������Ƶ�����ѡ��Ի���
	 * @param context
	 * @param month
	 * @param year
	 * @param day
	 * @param startMonth ��ʼ���·�
	 * @param startYear ��ʼ���
	 * @param startDay ��ʼ����
	 * @param lis
	 * @return
	 */
	public Dialog getCalendarDialog(Activity context, int month, int year,
			int day, int startMonth, int startYear, int startDay,
			CalendarDialogListener lis) {
		CalendarDialog dialog = new CalendarDialog(context, month, year, day,
				startMonth, startYear, startDay, lis);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER); // �˴���������dialog��ʾ��λ��
		dialog.setCancelable(true);
		dialog.show();
		WindowManager m = context.getWindowManager();
		Display d = m.getDefaultDisplay(); // ��ȡ��Ļ������
		WindowManager.LayoutParams p = window.getAttributes(); // ��ȡ�Ի���ǰ�Ĳ���ֵ
		p.height = (int) (d.getHeight() * dialogHeightProportion);
		p.width = (int) (d.getWidth() * dialogWidthProportion);
		dialog.onWindowAttributesChanged(p);
		window.setAttributes(p);
		return dialog;
	}


	/**
	 * ��ȡһ��������ʾdialog
	 * 
	 * @param context
	 * @return
	 */
	public Dialog getLoadDialog(Activity context) {
		LoadDialog dialog = new LoadDialog(context);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER); // �˴���������dialog��ʾ��λ��
		dialog.setCancelable(true);
		dialog.show();
		WindowManager m = context.getWindowManager();
		Display d = m.getDefaultDisplay(); // ��ȡ��Ļ������
		WindowManager.LayoutParams p = window.getAttributes(); // ��ȡ�Ի���ǰ�Ĳ���ֵ
		p.height = (int) (d.getHeight() * 0.2);
		p.width = (int) (d.getWidth() * 0.2);
		dialog.onWindowAttributesChanged(p);
		p.dimAmount = 0f;// ���ñ������䰵
		window.setAttributes(p);
		return dialog;
	}

	/**
	 * ��ȡһ���ύ����dialog
	 * 
	 * @param context
	 * @return
	 */
	public Dialog getProgressDialog(Activity context, int allInt, int havingInt) {
		ProgressDialog dialog = new ProgressDialog(context, allInt, havingInt);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER); // �˴���������dialog��ʾ��λ��
		dialog.setCancelable(true);
		dialog.show();
		WindowManager m = context.getWindowManager();
		Display d = m.getDefaultDisplay(); // ��ȡ��Ļ������
		WindowManager.LayoutParams p = window.getAttributes(); // ��ȡ�Ի���ǰ�Ĳ���ֵ
		p.height = (int) (d.getHeight() * 0.6);
		p.width = (int) (d.getWidth() * 0.6);
		dialog.onWindowAttributesChanged(p);
		p.dimAmount = 0f;// ���ñ������䰵
		window.setAttributes(p);
		return dialog;
	}


	public DialogUtil(double dialogWidthProportion,
			double dialogHeightProportion) {
		super();
		this.dialogWidthProportion = dialogWidthProportion;
		this.dialogHeightProportion = dialogHeightProportion;
	}

	public DialogUtil() {
		super();
	}

	public double getDialogWidthProportion() {
		return dialogWidthProportion;
	}

	public void setDialogWidthProportion(double dialogWidthProportion) {
		this.dialogWidthProportion = dialogWidthProportion;
	}

	public double getDialogHeightProportion() {
		return dialogHeightProportion;
	}

	public void setDialogHeightProportion(double dialogHeightProportion) {
		this.dialogHeightProportion = dialogHeightProportion;
	}

	public void setWidthAndHeight(double width, double height) {
		this.dialogHeightProportion = height;
		this.dialogWidthProportion = width;
	}

}
