package com.l.j;

import java.util.Calendar;

import com.l.j.dialog.CalendarDialog.CalendarDialogListener;
import com.l.j.view.LClockView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

	private ListView listView;

	private String[] names = { "����ѡ��-û������", "����ѡ��-������", "ѡ��л�-������",
			"ѡ��л�-����", "ʱ��ѡ��-Сʱ", "ʱ��ѡ��-����",
			"���صȴ�����", "��ͼ", "����ͼ", "�״�ͼ",
			"Բ��ͼƬ", "������ť", "�¶ȼ�","��������ť","ҳ���·�С��","tabС��"};

	private DialogUtil dialogUtil;
	private Calendar calendar;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.main_list);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//				android.R.layout.activity_list_item, names);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.dialog_list_item, R.id.dialog_list_item_name, names);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		listView.invalidate();
		listView.setOnItemClickListener(this);
		dialogUtil = new DialogUtil();
		calendar = Calendar.getInstance();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
			case 0:
				dialogUtil.getCalendarDialog(this,
						calendar.get(Calendar.MONTH) + 1,
						calendar.get(Calendar.YEAR),
						calendar.get(Calendar.DAY_OF_MONTH),
						new CalendarDialogListener() {
							@Override
							public void calendarDialogListener(int year, int month,
															   int day) {
								t(year + "-" + month + "-" + day);
							}
						});
				break;
			case 1:
				dialogUtil.getCalendarDialog(this,
						calendar.get(Calendar.MONTH) + 1,
						calendar.get(Calendar.YEAR),
						calendar.get(Calendar.DAY_OF_MONTH),
						10,
						2015,
						23,
						new CalendarDialogListener() {
							@Override
							public void calendarDialogListener(int year, int month,
															   int day) {
								t(year + "-" + month + "-" + day);
							}
						});
				break;
			case 2:
				intent = new Intent(this, ChooseBgTest.class);
				intent.putExtra("type", true);
				startActivity(intent);
				break;
			case 3:
				intent = new Intent(this, ChooseBgTest.class);
				intent.putExtra("type", false);
				startActivity(intent);
				break;
			case 4:
				intent = new Intent(this, ClockTest.class);
				intent.putExtra("type", LClockView.TYPE_HOURS);
				startActivity(intent);
				break;
			case 5:
				intent = new Intent(this, ClockTest.class);
				intent.putExtra("type", LClockView.TYPE_MINUTES);
				startActivity(intent);
				break;
			case 6:
				dialogUtil.getLoadDialog(this);
				break;
			case 8:
				dialogUtil.getProgressDialog(this, 100, 100);
				break;
			case 14:
				intent = new Intent(this, PageTest.class);
				intent.putExtra("type", PageTest.thisType_bottom);
				startActivity(intent);
				break;
			case 15:
				intent = new Intent(this, PageTest.class);
				intent.putExtra("type", PageTest.thisType_top);
				startActivity(intent);
				break;
			default:
				intent = new Intent(this, ViewTest.class);
				intent.putExtra("type", arg2-7);
				startActivity(intent);
				break;
		}
	}

	private void t(String s) {
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
}
