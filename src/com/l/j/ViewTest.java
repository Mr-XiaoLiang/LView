package com.l.j;

import java.util.ArrayList;
import java.util.HashMap;

import com.l.j.option.LProgressButtonOption;
import com.l.j.view.LPieView;
import com.l.j.view.LProgressButton;
import com.l.j.view.LProgressButton.LProgressButtonOnClickListener;
import com.l.j.view.LRadarView;
import com.l.j.view.LSlideButtonView;
import com.l.j.view.LSlideButtonView.SlideButtonViewListener;
import com.l.j.view.LThermometerView;
import com.l.j.view.LXiuXiu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

public class ViewTest extends Activity {

	private LPieView pieView;
	private LRadarView radarView;
	private LSlideButtonView buttonView,buttonView2,buttonView3;
	private LThermometerView thermometerView;
	private LProgressButton progressButton;
	private LXiuXiu lXiuXiu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		switch (intent.getIntExtra("type", 0)) {
			case 0:
				setContentView(R.layout.pie);
				pieView = (LPieView) findViewById(R.id.pie);
				pieView.setGreenSize(394);
				pieView.setRedSize(765);
				break;
			case 2:
				setContentView(R.layout.radar);
				radarView = (LRadarView) findViewById(R.id.r);
				ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < 7; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put(LRadarView.VALUE_NAME, "小建" + i + "号");
					map.put(LRadarView.VALUE_PERCENT, i * 20);
					map.put(LRadarView.VALUE_VALUE, i * 20);
					list.add(map);
				}
				radarView.setDataArray(list);
				break;
			case 3:
				setContentView(R.layout.round);
				break;
			case 4:
				setContentView(R.layout.btn);
				buttonView = (LSlideButtonView) findViewById(R.id.b);
				buttonView.setOnSlideListener(new SlideButtonViewListener() {
					@Override
					public void SlideButtonOnClick(LSlideButtonView SlideButtonView, boolean isOpen) {
						Toast.makeText(ViewTest.this, isOpen + "", Toast.LENGTH_SHORT).show();
					}
				});
				buttonView2 = (LSlideButtonView) findViewById(R.id.btn);
				buttonView2.setOnSlideListener(new SlideButtonViewListener() {
					@Override
					public void SlideButtonOnClick(LSlideButtonView SlideButtonView, boolean isOpen) {
						Toast.makeText(ViewTest.this, isOpen + "", Toast.LENGTH_SHORT).show();
					}
				});
				buttonView3 = (LSlideButtonView) findViewById(R.id.btn2);
				buttonView3.setBtnType(false);
				buttonView3.setBtnColor(Color.WHITE);
				buttonView3.setOffColor(Color.GRAY);
				buttonView3.setTextColor(Color.GRAY);
				buttonView3.setOnSlideListener(new SlideButtonViewListener() {
					@Override
					public void SlideButtonOnClick(LSlideButtonView SlideButtonView, boolean isOpen) {
						Toast.makeText(ViewTest.this, isOpen + "", Toast.LENGTH_SHORT).show();
					}
				});
				break;
			case 5:
				setContentView(R.layout.thermometer);
				thermometerView = (LThermometerView) findViewById(R.id.t);
				thermometerView.setMax(100);
				break;
			case 6:
				setContentView(R.layout.progress_button);
				progressButton = (LProgressButton) findViewById(R.id.pb);
				LProgressButtonOption.Builder builder = new LProgressButtonOption.Builder();

				builder.setBtnStartText("开始")
						.setBtnEndText("完成")
						.setShowPercent(true)
						.setBtnErrorIcon(R.drawable.ic_launcher);

				progressButton.setOption(new LProgressButtonOption(builder));
				progressButton.setClickListener(new LProgressButtonOnClickListener() {
					@Override
					public void LPBOnClick(LProgressButton btn) {
						switch (btn.getType()) {
							case LProgressButton.TYPE_END:
								progressButton.reset();
								break;
							case LProgressButton.TYPE_ERROR:
								progressButton.onEnd();
								break;
							case LProgressButton.TYPE_LOADING:
								progressButton.onError();
								break;
							case LProgressButton.TYPE_PREPARE:
								progressButton.onLoad(100, 100);
								break;
							case LProgressButton.TYPE_START:
								progressButton.onPrepare();
								// progressButton.onError();
								break;
							default:
								break;
						}
					}
				});
				break;
			case 15:
				setContentView(R.layout.xiuxiu);
				lXiuXiu = (LXiuXiu) findViewById(R.id.xiu);
				break;
		}
	}

}
