package com.l.j.view.shopitem;

import com.l.j.R;
import com.l.j.option.MyApplication;
import com.l.j.view.LPagePointView;
import com.l.j.view.shopitem.bean.HomePagersItem_101Bean;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
/**
 * 商品列表中的滚动焦点图
 * @author LiuJ
 *
 */
public class HomePagersItem_101 extends FrameLayout implements OnPageChangeListener {
	/**
	 * 上下文
	 */
	private Context context;
	/**
	 * 分页容器
	 */
	private ViewPager pagerView;
	/**
	 * 图片异步加载
	 */
	private ImageLoader imageLoader;
	/**
	 * 下标
	 */
	private LPagePointView pointView;
	/**
	 * 数据bean
	 */
	private HomePagersItem_101Bean bean;
	/**
	 * 图片数组
	 */
	private ImageView[] viewList;
	/**
	 * 点击事件
	 */
	private OnPageClickListener clickListener;
	/**
	 * 根节点
	 */
	private FrameLayout rootLayout;
	
	private void init(){
		LayoutInflater.from(context).inflate(R.layout.item_home_pagers_101,
				this, true);
		pagerView = (ViewPager) findViewById(R.id.item_home_pagers_101_pager);
		pointView = (LPagePointView) findViewById(R.id.item_home_pagers_101_point);
		rootLayout = (FrameLayout) findViewById(R.id.item_home_pagers_101_root);
		imageLoader = ImageLoader.getInstance();
		if(bean==null||bean.getImgUrlSize()==0){
			return;
		}
		DataSet();
	}
	
	private void DataSet(){
		pointView.setPointSize(bean.getImgUrlSize());
		LinearLayout.LayoutParams imgParams;
		viewList = new ImageView[bean.getImgUrlSize()];
		for(int i  = 0;i<viewList.length;i++){
			ImageView image = new ImageView(context);
			imgParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			image.setLayoutParams(imgParams);
			image.setScaleType(ScaleType.CENTER_CROP);
			image.setOnClickListener(new OnImageClickListener(i));
			viewList[i] = image;
			imageLoader.displayImage(bean.getImgUrl(i), viewList[i],MyApplication.getImageLoaderOption());
		}
		pagerView.setOnPageChangeListener(this);
		pagerView.setAdapter(pagerAdapter);
	}
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		WindowManager m = ((Activity) context).getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		ViewGroup.LayoutParams p = getLayoutParams();//getWindow().getAttributes(); // 获取对话框当前的参数值
		p.height = (int)(d.getHeight() * 0.25);
		setLayoutParams(p);
	}
	private class OnImageClickListener implements OnClickListener {
		private int index;
		
		public OnImageClickListener(int index) {
			super();
			this.index = index;
		}
		@Override
		public void onClick(View v) {
			if(clickListener!=null){
				clickListener.onPageClick(HomePagersItem_101.this, index);
			}
			if(bean.getImgIntentSize()<=index)
				return;
			if(bean.getIntentMsgSize()<=index)
				return;
			
			switch (bean.getImgIntent(index)) {
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
	};
	/**
	 * 点击事件
	 * @author LiuJ
	 *
	 */
	public interface OnPageClickListener{
		public void onPageClick(View view,int index);
	}
	
	private PagerAdapter pagerAdapter = new PagerAdapter(){

		@Override
		public int getCount() {
			return viewList.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(viewList[position]);
		}

		/**
		 * 载入图片进去
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(viewList[position], 0);
//			viewList[position].setOnClickListener(new OnPagerClickListener(position));
			return viewList[position];
		}

	};
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		pointView.onChange(arg0, arg1);
	}
	@Override
	public void onPageSelected(int arg0) {
	}
	public HomePagersItem_101(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init();
	}
	public HomePagersItem_101(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	public HomePagersItem_101(Context context) {
		this(context,null);
	}
	public HomePagersItem_101(Context context,HomePagersItem_101Bean bean,OnPageClickListener listener) {
		this(context);
		this.bean = bean;
		DataSet();
	}
	public HomePagersItem_101Bean getBean() {
		return bean;
	}
	public void setBean(HomePagersItem_101Bean bean) {
		this.bean = bean;
		DataSet();
	}
	public OnPageClickListener getClickListener() {
		return clickListener;
	}
	public void setClickListener(OnPageClickListener clickListener) {
		this.clickListener = clickListener;
	}
	
}
