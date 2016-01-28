package com.l.j;
import com.l.j.view.shopitem.HomeBigThreeItem_102;
import com.l.j.view.shopitem.HomeMediumBannerItem_103;
import com.l.j.view.shopitem.HomePagersItem_101;
import com.l.j.view.shopitem.HomeSmallBannerItem_105;
import com.l.j.view.shopitem.HomeTextTitleItem_104;
import com.l.j.view.shopitem.bean.BeanType;
import com.l.j.view.shopitem.bean.HomeBigThreeItem_102Bean;
import com.l.j.view.shopitem.bean.HomeMediumBannerItemBean;
import com.l.j.view.shopitem.bean.HomePagersItem_101Bean;
import com.l.j.view.shopitem.bean.HomeTextTitleItem_104Bean;
import com.l.j.view.shopitem.bean.ShopListBean;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 商城首页
 * 
 * @author LiuJ
 */
public class ShopListTest extends Activity {

	private ListView listView;

	private TextView hint;

	private ShopListBean bean;
	
	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_list);
		listView = (ListView) findViewById(R.id.shop_list_listview);
		hint = (TextView) findViewById(R.id.shop_list_hint);
		hint.setText("本系列的View都是我在工作中，写商城类APP想要偷懒，封装的一些Item，" 
				+ "为了自己以后方便，也厚颜无耻的上传上来了，本着反正已经加进来的心理，"
				+ "就再做了这个展示页面，哈哈，掠过即可，顺便吐槽一句，想用RecyclerView的，"
				+ "但是死活会报错，人太懒，就用ListView了,Item样式总共有十多个，慢慢更新");
		initData();
	}
	private class MyAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			switch (bean.getTypes(position)) {
			case ShopListBean.HomePagersItem_101:
				convertView = new HomePagersItem_101(ShopListTest.this, (HomePagersItem_101Bean) bean.getBeans(position), null);
				break;
			case ShopListBean.HomeBigThreeItem_102:
				convertView = new HomeBigThreeItem_102(ShopListTest.this, (HomeBigThreeItem_102Bean) bean.getBeans(position), null);
				break;
			case ShopListBean.HomeMediumBannerItem_103:
				convertView = new HomeMediumBannerItem_103(ShopListTest.this, (HomeMediumBannerItemBean) bean.getBeans(position), null);
				break;
			case ShopListBean.HomeTextTitleItem_104:
				convertView = new HomeTextTitleItem_104(ShopListTest.this, (HomeTextTitleItem_104Bean) bean.getBeans(position), null);
				break;
			case ShopListBean.HomeSmallBannerItem_105:
				convertView = new HomeSmallBannerItem_105(ShopListTest.this, (HomeMediumBannerItemBean) bean.getBeans(position), null);
				break;
			default:
				break;
			}
			return convertView;
		}
		
		@Override
		public int getCount() {
			return bean.getBeansSize();
		}

		@Override
		public Object getItem(int position) {
			return bean.getBeans(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}
	private void initData(){
		HomePagersItem_101Bean item_101Bean = new HomePagersItem_101Bean();
		item_101Bean.addImgUrl("http://h.hiphotos.baidu.com/image/pic/item/aa18972bd40735fa09818ab19c510fb30f240841.jpg");
		item_101Bean.addImgUrl("http://img4.duitang.com/uploads/blog/201310/18/20131018213446_smUw4.thumb.700_0.jpeg");
		item_101Bean.addImgUrl("http://img3.3lian.com/2013/s1/65/d/113.jpg");
		item_101Bean.addImgUrl("http://f.hiphotos.baidu.com/zhidao/pic/item/279759ee3d6d55fb4f4071446b224f4a21a4ddca.jpg");
		item_101Bean.addImgUrl("http://img2.3lian.com/2014/f4/102/d/89.jpg");
		bean = new ShopListBean();
		bean.addBean(item_101Bean);
		bean.addType(ShopListBean.HomePagersItem_101);
		HomeBigThreeItem_102Bean item_102Bean = new HomeBigThreeItem_102Bean();
		item_102Bean.addBgUrl("http://h.hiphotos.baidu.com/image/pic/item/aa18972bd40735fa09818ab19c510fb30f240841.jpg");
		item_102Bean.addBgUrl("http://img4.duitang.com/uploads/blog/201310/18/20131018213446_smUw4.thumb.700_0.jpeg");
		item_102Bean.addBgUrl("http://img3.3lian.com/2013/s1/65/d/113.jpg");
		item_102Bean.addIconUrl("http://static.freepik.com/free-photo/circle-arrow-right_318-26614.jpg");
		item_102Bean.addIconUrl("http://static.freepik.com/free-photo/circle-arrow-right_318-26614.jpg");
		item_102Bean.addIconUrl("http://static.freepik.com/free-photo/circle-arrow-right_318-26614.jpg");
		item_102Bean.addMsg("啊啊啊啊，就是测试");
		item_102Bean.addMsg("啊啊啊啊，就是测试");
		item_102Bean.addMsg("啊啊啊啊，就是测试");
		item_102Bean.addTitle("减价大优惠啊");
		item_102Bean.addTitle("减价大优惠啊");
		item_102Bean.addTitle("减价大优惠啊");
		bean.addBean(item_102Bean);
		bean.addType(ShopListBean.HomeBigThreeItem_102);
		HomeMediumBannerItemBean item_103Bean = new HomeMediumBannerItemBean(
				"http://h.hiphotos.baidu.com/image/pic/item/aa18972bd40735fa09818ab19c510fb30f240841.jpg", 
				BeanType.DO_NOTHING, "");
		bean.addBean(item_103Bean);
		bean.addType(ShopListBean.HomeMediumBannerItem_103);
		HomeTextTitleItem_104Bean item_104Bean = new HomeTextTitleItem_104Bean("新店开张，全场5折", BeanType.DO_NOTHING, "");
		for(int i = 0;i<10;i++){
			bean.addBean(item_104Bean);
			bean.addType(ShopListBean.HomeTextTitleItem_104);
		}
		HomeMediumBannerItemBean item_105Bean = new HomeMediumBannerItemBean(
				"http://f.hiphotos.baidu.com/zhidao/pic/item/279759ee3d6d55fb4f4071446b224f4a21a4ddca.jpg", 
				BeanType.DO_NOTHING, "");
		bean.addBean(item_105Bean);
		bean.addType(ShopListBean.HomeSmallBannerItem_105);
		listView.setAdapter(adapter = new MyAdapter());
	}
}
