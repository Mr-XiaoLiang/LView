package com.l.j.dialog;

import java.util.Calendar;

import com.example.mytest.R;
import com.l.j.view.LCalendarView;
import com.l.j.view.LCalendarView.CalendarViewListener;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.TextView;

public class CalendarDialog extends Dialog implements OnClickListener,
		CalendarViewListener {
	/**
	 * ������ͼ
	 */
	private LCalendarView calendarView;
	/**
	 * �·�
	 */
	private TextView monthView;
	/**
	 * ���
	 */
	private TextView yearView;
	/**
	 * ��ť
	 */
	private View leftView;
	/**
	 * �Ұ�ť
	 */
	private View rightView;
	/**
	 * ȷ����ť
	 */
	private TextView enterView;
	/**
	 * ȡ����ť
	 */
	private TextView escView;
	/**
	 * ��
	 */
	private int month;
	/**
	 * ��
	 */
	private int year;
	/**
	 * ��
	 */
	private int day;
	/**
	 * ����
	 */
	private int today;
	/**
	 * ����
	 */
	private int thisYear;
	/**
	 * ����
	 */
	private int thisMonth;
	/**
	 * �ص�
	 */
	private CalendarDialogListener lis;
	/**
	 * ��ʼ��
	 */
	private int startYear = -1;
	/**
	 * ��ʼ��
	 */
	private int startMonth = -1;
	/**
	 * ��ʼ��
	 */
	private int startDay = -1;
	
	public interface CalendarDialogListener {
		public void calendarDialogListener(int year, int month, int day);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ����Ļtitle
		setContentView(R.layout.dialog_calendar);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		calendarView = (LCalendarView) findViewById(R.id.dialog_calender_calender);
		monthView = (TextView) findViewById(R.id.dialog_calender_month);
		yearView = (TextView) findViewById(R.id.dialog_calender_year);
		leftView = findViewById(R.id.dialog_calender_left);
		rightView = findViewById(R.id.dialog_calender_right);
		enterView = (TextView) findViewById(R.id.dialog_calender_enter);
		escView = (TextView) findViewById(R.id.dialog_calender_esc);
		leftView.setOnClickListener(this);
		rightView.setOnClickListener(this);
		enterView.setOnClickListener(this);
		escView.setOnClickListener(this);
		calendarView.setCalendarViewListener(this);
		init();
	}

	private void init() {
		enterView.setText("ȷ��");
		escView.setText("ȡ��");
		monthView.setText(month + "��");
		yearView.setText(year + "��");
		Calendar calendar = Calendar.getInstance();
		today = calendar.get(Calendar.DAY_OF_MONTH);
		thisMonth = calendar.get(Calendar.MONTH) + 1;
		thisYear = calendar.get(Calendar.YEAR);
		calendarView.setData(year, month, today, day);
		calendarStartSet();
	}
	
	private void calendarStartSet(){
		if(startYear>0 && startMonth>0 && startDay>0){
			if(year < startYear && month < startMonth){
				calendarView.setItems(0, 0);
			}else if(year == startYear && month == startMonth){
				calendarView.setItems(startDay, 31);
			}
		}
	}
	
	/**
	 * ����һ������ѡ����
	 * ����Ĭ�ϵ�ʱ��
	 * @param context
	 * @param month
	 * @param year
	 * @param day
	 * @param lis
	 */
	public CalendarDialog(Context context, int month, int year, int day,
			CalendarDialogListener lis) {
		super(context);
		this.month = month;
		this.year = year;
		this.day = day;
		this.lis = lis;
	}
	/**
	 * ����һ�������Ƶ�ʱ��ѡ����
	 * @param context
	 * @param month
	 * @param year
	 * @param day
	 * @param startMonth ��Чѡ��Ŀ�ʼ�·�
	 * @param startYear ��Чѡ��Ŀ�ʼ���
	 * @param startDay ��Чѡ��Ŀ�ʼ��
	 * @param lis
	 */
	public CalendarDialog(Context context, int month, int year, int day,int startMonth, int startYear, int startDay,
			CalendarDialogListener lis) {
		super(context);
		this.month = month;
		this.year = year;
		this.day = day;
		this.lis = lis;
		this.startMonth = startMonth;
		this.startYear = startYear;
		this.startDay = startDay;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_calender_left:
			month--;
			if(startMonth>0&&startYear>0&&month<=startMonth&&year <= startYear){
				leftView.setVisibility(View.INVISIBLE);
				month=startMonth;
			}
			if(month<1){
				year--;
				month = 12;
			}
			yearView.setText(year+"��");
			monthView.setText(month+"��");
			if(year == thisYear && month==thisMonth){
				calendarView.setData(year, month, today, day);
			}else{
				calendarView.setData(year, month, 0, day);
			}
			calendarStartSet();
			break;
		case R.id.dialog_calender_right:
			month++;
			if(startMonth>0&&startYear>0&&month>startMonth&&year >= startYear){
				leftView.setVisibility(View.VISIBLE);
			}
			if(month>12){
				year++;
				month = 1;
			}
			yearView.setText(year+"��");
			monthView.setText(month+"��");
			if(year == thisYear && month==thisMonth){
				calendarView.setData(year, month, today, day);
			}else{
				calendarView.setData(year, month, 0, day);
			}
			calendarStartSet();
			break;
		case R.id.dialog_calender_enter:
			lis.calendarDialogListener(year, month, day);
			dismiss();
			break;
		case R.id.dialog_calender_esc:
			dismiss();
			break;

		}
	}

	@Override
	public void calendarSelected(int d) {
		day = d;
	}

}
