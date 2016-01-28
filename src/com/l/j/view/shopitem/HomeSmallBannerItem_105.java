package com.l.j.view.shopitem;

import com.l.j.R;
import com.l.j.option.MyApplication;
import com.l.j.view.shopitem.bean.HomeMediumBannerItemBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
/**
 * 小型横幅
 * @author LiuJ
 *
 */
public class HomeSmallBannerItem_105 extends LinearLayout implements OnClickListener {

	private HomeMediumBannerItemBean bean;
	private ImageView imageView;
	private ImageLoader loader;
	private Context context;
	private OnClickListener listener;
	
	public HomeSmallBannerItem_105(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.item_home_small_banner_105,
				this, true);
		imageView = (ImageView) findViewById(R.id.item_home_small_banner_105_img);
		imageView.setOnClickListener(this);
		loader = ImageLoader.getInstance();
		if(bean!=null)
			dataSet();
	}

	private void dataSet(){
		loader.displayImage(bean.getImgUrl(), imageView, MyApplication.getImageLoaderOption());
	}
	
	@Override
	public void onClick(View v) {
		if(listener!=null)
			listener.onClick(v);
		if(bean==null)
			return;
		switch (v.getId()) {
		case R.id.item_home_medium_banner_103_img:
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
			break;
		default:
			break;
		}
	}

	public HomeSmallBannerItem_105(Context context) {
		this(context,null);
	}
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		WindowManager m = ((Activity) context).getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		ViewGroup.LayoutParams p = getLayoutParams();//getWindow().getAttributes(); // 获取对话框当前的参数值
		p.height = (int)(d.getHeight() * 0.1);
		setLayoutParams(p);
	}

	public HomeSmallBannerItem_105(Context context, HomeMediumBannerItemBean bean,OnClickListener listener) {
		this(context);
		this.bean = bean;
		this.listener = listener;
		dataSet();
	}
}
