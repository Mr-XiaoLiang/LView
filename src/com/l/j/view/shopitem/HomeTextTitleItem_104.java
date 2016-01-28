package com.l.j.view.shopitem;

import com.l.j.R;
import com.l.j.option.MyApplication;
import com.l.j.view.shopitem.bean.HomeTextTitleItem_104Bean;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 文字标题
 * @author LiuJ
 *
 */
public class HomeTextTitleItem_104 extends LinearLayout implements OnClickListener {

	private Context context;
	private OnClickListener listener;
	private TextView textView;
	private HomeTextTitleItem_104Bean bean;
	
	@Override
	public void onClick(View v) {
		if(listener!=null){
			listener.onClick(v);
		}
		if(bean==null)
			return;
		switch (bean.getIntent()) {
		case GO_TO_EVENT:
			
			break;
		case GO_TO_FUNCTION:
			
			break;
		case GO_TO_GOODS:
			
			break;
		case GO_TO_SEARCH:
			
			break;
		case GO_TO_SHOP:
			
			break;
		case GO_TO_WEB:
			
			break;
		default:
			break;
		}
	}

	public HomeTextTitleItem_104(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.item_home_text_title_104,
				this, true);
		textView = (TextView) findViewById(R.id.item_home_text_title_104_text);
		textView.setOnClickListener(this);
		if(bean==null){
			return;
		}
		dataSet();
	}
	private void dataSet(){
		textView.setText(bean.getText());
	}
	public HomeTextTitleItem_104(Context context) {
		this(context,null);
	}

	public HomeTextTitleItem_104(Context context, HomeTextTitleItem_104Bean bean, OnClickListener listener) {
		this(context);
		this.listener = listener;
		this.bean = bean;
		dataSet();
	}
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		WindowManager m = ((Activity) context).getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		ViewGroup.LayoutParams p = getLayoutParams();//getWindow().getAttributes(); // 获取对话框当前的参数值
		p.height = (int)(d.getHeight() * 0.08);
		setLayoutParams(p);
	}
}