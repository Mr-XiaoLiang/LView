package com.l.j.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * �״�ͼ
 * @author LiuJ
 * @time 2015 10 14
 */
public class LRadarView extends View {
	/**
	 * ��������
	 */
	private Paint bgPaint;
	/**
	 * ǰ������
	 */
	private Paint graphPaint;
	/**
	 * ���ֻ���
	 */
	private Paint textPaint;
	/**
	 * ���ֻ���
	 */
	private Paint smallTextPaint;
	/**
	 * ����ɫ
	 * ���⵽����
	 */
	private int[] bgColors = { Color.parseColor("#609bd2"), Color.parseColor("#77abdb"),
			Color.parseColor("#8bbae5"), Color.parseColor("#b9d7f3"), Color.parseColor("#d6e8fa") };
	/**
	 * ������͸����
	 * �����浽����
	 */
	private int[] bgAlphas = {102,128,179,204,230};
	/**
	 * ǰ����ɫ
	 */
	private int graphColor = Color.parseColor("#6c6fea");
	/**
	 * ���ݼ�
	 * һ��map��һ������
	 * map�����˵�Ļ�����Ϣ
	 * ����
	 * ��ʾ��ֵ
	 * �ٷֱ�
	 */
	private ArrayList<HashMap<String, Object>> dataArray;
	/**
	 * ����
	 * �������ݵ�������
	 */
	public static final String VALUE_NAME = "VALUE_NAME";
	/**
	 * �ٷֱ�
	 * �������ݵ�������
	 */
	public static final String VALUE_PERCENT = "VALUE_PERCENT";
	/**
	 * ��ʾֵ
	 * �������ݵ�������
	 */
	public static final String VALUE_VALUE = "VALUE_VALUE";
	/**
	 * ��������
	 */
	private int pointNum = 0;
	/**
	 * ��Ԫ�Ƕ�
	 */
	private float angle = 0f;
	/**
	 * �뾶
	 */
	private float radius = 0;
	/**
	 * ���
	 */
	private float width = 0;
	/**
	 * �߶�
	 */
	private float height = 0;
	/**
	 * ���ֳߴ�
	 */
	private float bigTaxtSize = 0;
	/**
	 * С�ֳߴ�
	 */
	private float smallTaxtSize = 0;
	/**
	 * ����������
	 */
	private int index = 0;
	/**
	 * �ص�����
	 */
	private RadarViewListener lis;
	
	public LRadarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		bgPaint = new Paint();
		bgPaint.setAntiAlias(true);
		bgPaint.setStyle(Paint.Style.FILL);
		
		graphPaint = new Paint();
		graphPaint.setAntiAlias(true);
		graphPaint.setStyle(Paint.Style.FILL);
		graphPaint.setColor(graphColor);
		graphPaint.setAlpha(128);
		
		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setTextAlign(Align.CENTER);
		
		smallTextPaint = new Paint();
		smallTextPaint.setAntiAlias(true);
		smallTextPaint.setColor(graphColor);
		smallTextPaint.setTextAlign(Align.CENTER);
		
		dataArray = new ArrayList<HashMap<String,Object>>();
		for(int i = 0;i<7;i++){
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put(VALUE_NAME, "name"+i);
			map.put(VALUE_PERCENT, i*20);
			map.put(VALUE_VALUE, i*20+"");
			dataArray.add(map);
		}
	}

	public LRadarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LRadarView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		/**
		 * ���������ߴ�����Ϊ��view����֮����ȡ�ߴ����������
		 * �����ڵ������������ʱ���ȡ�ĳߴ������Ч��
		 */
		width = getWidth();
		height = getHeight();
		//��֤Բ��������ʾ����
		if((height*2/3)>(width/2)){
			radius = width/2/2;
		}else{
			radius = height*2/3/2;
		}
		bigTaxtSize = radius/8;
		smallTaxtSize = radius/12;
		pointNum = dataArray.size();
		if(pointNum!=0){
			angle = 360/pointNum;
			drawBg(canvas);
			drawGraph(canvas);
			drawText(canvas);
			if(index<=100){
				index++;
				invalidate();
			}
		}
		super.onDraw(canvas);
	}
	/**
	 * �����״ﱳ��
	 * @param canvas
	 */
	private void drawBg(Canvas canvas) {
		float radiusWidth = radius/5;
		//5��
		for(int i = 0;i<5;i++){
			bgPaint.setColor(bgColors[i]);
			bgPaint.setAlpha(bgAlphas[i]);
			//����
			Path path = new Path();
			for(int j = 0;j<pointNum;j++){
				float[] p = getLocation(j*angle, radius-(i*radiusWidth));
				if(j==0){
					path.moveTo(p[0], p[1]);
				}else{
					path.lineTo(p[0], p[1]);
				}
			}
			path.close();
			canvas.drawPath(path, bgPaint);
		}
	}
	/**
	 * �����״�ǰ��
	 * @param canvas
	 */
	private void drawGraph(Canvas canvas) {
		Path path = new Path();
		for(int j = 0;j<pointNum;j++){
			float[] p;
			int per = (Integer)dataArray.get(j).get(VALUE_PERCENT);
			if(index>per){
				p = getLocation(j*angle, radius*per/100);
			}else{
				p = getLocation(j*angle, radius*index/100);
			}
			if(j==0){
				path.moveTo(p[0], p[1]);
			}else{
				path.lineTo(p[0], p[1]);
			}
		}
		path.close();
		canvas.drawPath(path, graphPaint);
	}
	/**
	 * �����״�����
	 * @param canvas
	 */
	private void drawText(Canvas canvas) {
		String name = "";
		String per = "";
		float[] p;
		/*
		 * ���������������С
		 */
		textPaint.setTextSize(bigTaxtSize);
		smallTextPaint.setTextSize(smallTaxtSize);
		/*
		 * �������ָ߶�ƫ����,�����߶Ⱦ���
		 */
		FontMetrics fm = textPaint.getFontMetrics();
		float bigTextY = -fm.descent + (fm.descent - fm.ascent) / 2;
		fm = smallTextPaint.getFontMetrics();
		float smallTextY = -fm.descent + (fm.descent - fm.ascent) / 2;
		/**
		 * ��ʼ����
		 */
		for(int j = 0;j<pointNum;j++){
			name = (String) dataArray.get(j).get(VALUE_NAME);
			per = "("+dataArray.get(j).get(VALUE_VALUE).toString()+")";
			p = getLocation(j*angle, radius);
			/*
			 * Ϊ�˱������ָ���ͼ��,���Ը���λ�ò�ͬ����ƫ����
			 */
			if(j*angle<90){
				canvas.drawText(name, p[0]+(name.length()*bigTaxtSize/2), p[1]+bigTextY+bigTaxtSize/2, textPaint);
				canvas.drawText(per, p[0]+(name.length()*bigTaxtSize/2), p[1]+smallTextY+bigTaxtSize*3/2, smallTextPaint);
			}else if(j*angle<180){
				canvas.drawText(name, p[0]+(name.length()*bigTaxtSize/2), p[1]-bigTextY-smallTaxtSize, textPaint);
				canvas.drawText(per, p[0]+(name.length()*bigTaxtSize/2), p[1]-smallTextY, smallTextPaint);
			}else if(j*angle<270){
				canvas.drawText(name, p[0]-(name.length()*bigTaxtSize/2), p[1]-bigTextY-smallTaxtSize, textPaint);
				canvas.drawText(per, p[0]-(name.length()*bigTaxtSize/2), p[1]-smallTextY, smallTextPaint);
			}else{
				canvas.drawText(name, p[0]-(name.length()*bigTaxtSize/2), p[1]+bigTextY+bigTaxtSize/2, textPaint);
				canvas.drawText(per, p[0]-(name.length()*bigTaxtSize/2), p[1]+smallTextY+bigTaxtSize*3/2, smallTextPaint);
			}
		}
	}
	/**
	 * ���ݽǶȼ��뾶����λ��
	 * @param angle �Ƕ�
	 * @param radiu �뾶
	 * @return
	 * ��ʽ:�������Ǻ����������
	 * X = ���/2 + Math.sin(�Ƕ�/180*Math.PI)*�뾶
	 * Y = �߶�/2 + Math.cos(�Ƕ�/180*Math.PI)*�뾶
	 */
	private float[] getLocation(float angle,float radiu){
		//�ѽǶȽ���Ϊ0����y��������
//		angle += 180;
		float x = (float) (width / 2 + (Math.sin(angle/180 * Math.PI) * radiu));
		float y = (float) (height / 2 + (Math.cos(angle/180 * Math.PI) * radiu));
		return new float[]{x,y};
	}
	/**
	 * ������ʾ����
	 * @param dataArray
	 * Ҫ��ٷֱ�Ϊ����,������׳�����ʱ�쳣(����ת���쳣)
	 */
	public void setDataArray(ArrayList<HashMap<String, Object>> dataArray) {
		this.dataArray = dataArray;
		index = 0;
		invalidate();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP){
			int x = (int) (event.getX()-width/2);
			int y = (int) (event.getY()-height/2);
			double z = Math.sqrt((x * x) + (y * y));
			int p = (int) (Math.asin(x / z)*(180/Math.PI));
			if (y < 0) {
				if (x < 0) {
					p = -p - 180;
				} else {
					p = 180 - p;
				}
			}
			if(p<0){
				p+=360;
			}
			int num = (int) (p/angle);
			if(num*angle+angle/2>p){
				if(lis!=null){
					lis.onRadarViewClick(num);
				}
			}else{
				if(lis!=null){
					lis.onRadarViewClick(num+1);
				}
			}
		}
		return true;
	}
	/**
	 * �������
	 * @author xiao
	 *
	 */
	public interface RadarViewListener{
		public void  onRadarViewClick(int index);
	}
	public void setRadarViewListener(RadarViewListener lis) {
		this.lis = lis;
	}
}
