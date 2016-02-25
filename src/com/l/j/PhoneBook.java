package com.l.j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import com.l.j.view.phoneBookSuite.ContactBean;
import com.l.j.view.phoneBookSuite.LInitialsIndexView;
import com.l.j.view.phoneBookSuite.LInitialsIndexView.LInitialsIndexViewListener;
import com.l.j.view.phoneBookSuite.PhoneBookAdapter;
import com.l.j.view.phoneBookSuite.ShowLetterDialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 电话本列表
 * 
 * @author LiuJ 电话本列表套件之一
 */
public class PhoneBook extends Activity
		implements OnClickListener, LInitialsIndexViewListener, OnCheckedChangeListener {

	private View back;
	private RecyclerView recyclerView;
	private LInitialsIndexView indexView;
	private TextView letterView;
//	private ShowLetterDialog letterDialog;//经过测试不适用
	private CheckBox checkAll;
	private TextView enter;
	private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象
	private ArrayList<ContactBean> dataList;
	private HashMap<String, Integer> letterMap = new HashMap<String, Integer>();
	private Adapter<ViewHolder> adapter;
	private LinearLayoutManager linearLayoutManager;
	private boolean onTouch = false;
	private static final int REQUEST_CODE_ASK_READ_CONTACTS = 666666;
	private TextView showLetter;//dialog替补方案

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_book);
		letterView = (TextView) findViewById(R.id.activity_phone_book_first_letter_text);
		recyclerView = (RecyclerView) findViewById(R.id.activity_phone_book_recyclerview);
		indexView = (LInitialsIndexView) findViewById(R.id.activity_phone_book_bar);
		checkAll = (CheckBox) findViewById(R.id.activity_phone_book_check_all);
		enter = (TextView) findViewById(R.id.activity_phone_book_enter);
		back = findViewById(R.id.activity_phone_book_back);
		showLetter = (TextView) findViewById(R.id.dialog_show_letter_text);
		back.setOnClickListener(this);
		enter.setOnClickListener(this);
		checkAll.setOnCheckedChangeListener(this);
		indexView.setListener(this);
		// 实例化
		asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());
		if (Build.VERSION.SDK_INT >= 23) {
			int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
			if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_CONTACTS },
						REQUEST_CODE_ASK_READ_CONTACTS);
				return;
			} else {
				init();
			}
		} else {
			init();
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
		case REQUEST_CODE_ASK_READ_CONTACTS:
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				init();
			} else {
				// Permission Denied
				Toast.makeText(this, "权限不允许", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}

	/**
	 * 初始化数据库查询参数
	 */
	private void init() {
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
		// 查询的字段
		String[] projection = { ContactsContract.CommonDataKinds.Phone._ID,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.DATA1,
				"sort_key", ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
				ContactsContract.CommonDataKinds.Phone.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY };
		// 按照sort_key升序查詢
		asyncQueryHandler.startQuery(0, null, uri, projection, null, null, "sort_key COLLATE LOCALIZED asc");

	}

	/**
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAsyncQueryHandler extends AsyncQueryHandler {

		private HashMap<String, Integer> alphaIndexer; // 字母索引
		private String[] sections; // 存储每个章节

		public MyAsyncQueryHandler(ContentResolver cr) {
			super(cr);
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			if (cursor != null && cursor.getCount() > 0) {
				HashMap<Integer, ContactBean> contactIdMap = new HashMap<Integer, ContactBean>();
				dataList = new ArrayList<ContactBean>();
				cursor.moveToFirst(); // 游标移动到第一项
				for (int i = 0; i < cursor.getCount(); i++) {
					cursor.moveToPosition(i);
					String name = cursor.getString(1);
					String number = cursor.getString(2);
					String sortKey = cursor.getString(3);
					int contactId = cursor.getInt(4);
					Long photoId = cursor.getLong(5);
					String lookUpKey = cursor.getString(6);

					if (!contactIdMap.containsKey(contactId)) {
						// 创建联系人对象
						ContactBean contact = new ContactBean();
						contact.setDesplayName(name);
						contact.setPhoneNum(number);
						contact.setSortKey(sortKey);
						contact.setPhotoId(photoId);
						contact.setLookUpKey(lookUpKey);
						contact.setLetter(getAlpha(sortKey));
						contact.setChecked(false);
						contact.setPhone(true);
						dataList.add(contact);
						contactIdMap.put(contactId, contact);
					}
				}
				if (dataList.size() > 0) {
					Iterator<ContactBean> iter = dataList.iterator();
					ArrayList<ContactBean> dataList2 = new ArrayList<ContactBean>();
					String letter = "";
					int index = 0;
					ContactBean bean;
					while (iter.hasNext()) {
						bean = iter.next();
						if (!letter.equals(bean.getLetter())) {
							ContactBean b = new ContactBean();
							b.setLetter(bean.getLetter());
							b.setPhone(false);
							dataList2.add(index, b);
							letter = bean.getLetter();
							letterMap.put(letter, index);
							index++;
						}
						index++;
					}
					dataList = dataList2;
					initRecyclerView();
				}
			}
			super.onQueryComplete(token, cookie, cursor);
		}
	}

	private void initRecyclerView() {
		adapter = new PhoneBookAdapter(PhoneBook.this, dataList);
		linearLayoutManager = new LinearLayoutManager(this);
		// 设置列表类型（列表，宫格）
		recyclerView.setLayoutManager(linearLayoutManager);
		// 绑定适配器
		recyclerView.setAdapter(adapter);
		// 设置item动画
		recyclerView.setItemAnimator(new DefaultItemAnimator());
	}

	/**
	 * 获取首字母
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式匹配
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase(); // 将小写字母转换为大写
		} else {
			return "#";
		}
	}

	private class SortByName implements Comparator<ContactBean> {

		public SortByName() {
			super();
		}

		@Override
		public int compare(ContactBean lhs, ContactBean rhs) {
			return (lhs.getLetter().charAt(0)) - (rhs.getLetter().charAt(0));
		}

	}

	@Override
	public void onInitialsIndexSelected(View v, int i, String s) {
		showLetter.setText(s);
//		linearLayoutManager.smoothScrollToPosition(recyclerView, new State(), letterMap.get(s));
	}

	@Override
	public void onInitialsIndexDown(View v, int i, String s) {
		onTouch = true;
		showLetter.setVisibility(View.VISIBLE);
		showLetter.setText(s);
	}

	@Override
	public void onInitialsIndexUp(View v, int i, String s) {
		onTouch = false;
		showLetter.setVisibility(View.GONE);
	}

	RecyclerView.OnScrollListener onListScroll = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
			// TODO Auto-generated method stub
			super.onScrollStateChanged(recyclerView, newState);
		}

	};

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
