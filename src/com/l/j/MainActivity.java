package com.l.j;

import java.util.Calendar;

import com.l.j.dialog.CalendarDialog.CalendarDialogListener;
import com.l.j.dialog.LWheelDialog.LWheelDialogType;
import com.l.j.view.LCalendarView;
import com.l.j.view.LClockView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

	private ListView listView;

	private String[] names = { 
			"日历选择-日期没有限制&全向拖动", "日历选择-日期有限制&单向", "选项卡切换-三角形", "选项卡切换-线形", "时间选择-小时", 
			"时间选择-分钟","加载等待动画", "饼图", "进度图", "雷达图", 
			"圆形图片", "滑动按钮", "温度计", "进度条按钮", "页面下方小点", 
			"tab小点", "日期滚轮", "时间滚轮", "全套滚轮","tab条形", 
			"倒计时View","商品列表","支付宝咻一咻","系统自带的抽屉用法演示","现在较流行的抽屉样式","带涟漪的Layout" };

	private DialogUtil dialogUtil;
	private Calendar calendar;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.main_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dialog_list_item,
				R.id.dialog_list_item_name, names);
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
			dialogUtil.getCalendarDialog(this, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.DAY_OF_MONTH), LCalendarView.SlideType.Both, new CalendarDialogListener() {
						@Override
						public void calendarDialogListener(int year, int month, int day) {
							t(year + "-" + month + "-" + day);
						}
					});
			break;
		case 1:
			dialogUtil.getCalendarDialog(this, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.DAY_OF_MONTH), LCalendarView.SlideType.Horizontal,
					new CalendarDialogListener() {
						@Override
						public void calendarDialogListener(int year, int month, int day) {
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
		case 16:
			dialogUtil.getWheelDialog(this, LWheelDialogType.DATE, null);
			break;
		case 17:
			dialogUtil.getWheelDialog(this, LWheelDialogType.TIME, null);
			break;
		case 18:
			dialogUtil.getWheelDialog(this, LWheelDialogType.ALL, null);
			break;
		case 19:
			intent = new Intent(this, PageTest.class);
			intent.putExtra("type", PageTest.thisType_top_line);
			startActivity(intent);
			break;
		case 20:
			intent = new Intent(this, CountDownListActivity.class);
			startActivity(intent);
			break;
		case 21:
			intent = new Intent(this, ShopListTest.class);
			startActivity(intent);
			break;
		default:
			intent = new Intent(this, ViewTest.class);
			intent.putExtra("type", arg2 - 7);
			startActivity(intent);
			break;
		}
	}

	private void t(String s) {
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
}
