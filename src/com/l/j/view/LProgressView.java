package com.l.j.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class LProgressView extends View {
	/**
	 * ��������ɫ
	 */
	private int proColor;
	/**
	 * ������ɫ
	 */
	private int textColor;
	/**
	 * ������ɫ
	 */
	private int bgColor;
	/**
	 * ����������
	 */
	private Paint proPaint;
	/**
	 * �˵㻭��
	 */
	private Paint epPaint;
	/**
	 * ��������
	 */
	private Paint bgPaint;
	/**
	 * ���ֻ���
	 */
	private Paint textPaint;
	/**
	 * ����������
	 */
	private Paint proBgPaint;
	/**
	 * �ܽ���
	 */
	private int allInt = 0;
	/**
	 * �Ѿ����˵Ľ���
	 */
	private int havingInt = 0;
	/**
	 * �������Ƿ��б���
	 */
	private boolean proHaveBg = false;
	/**
	 * ��ʾ�ַ�
	 */
	private String showText;
	/**
	 *  ���
	 */
	private float width;
	/**
	 * �߶�
	 */
	private float height;
	/**
	 * �����С
	 */
	private float textSize;
	/**
	 * ֱ��
	 */
	private float diameter;
	/**
	 * ��̬����
	 */
	private float index = 0;
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getWidth();
		height = getHeight();
		if(width>height){
			diameter = height/7;
		}else{
			diameter = width/7;
		}
		proPaint.setStrokeWidth(diameter);
		proBgPaint.setStrokeWidth(diameter);
		float pro = getAngle();
		if(proHaveBg){
			//������������
			canvas.drawCircle(width/2, height/2, diameter*3f, proBgPaint);
		}
		/**
		 * ��������
		 */
		canvas.drawArc(getRectF(), -90, pro, false, proPaint);
		float[] loc = getLocation(pro);
		canvas.drawCircle(loc[0], loc[1], diameter/2, epPaint);
		canvas.drawCircle(loc[2], loc[3], diameter/2, epPaint);
		/**
		 * ������
		 */
		canvas.drawCircle(width/2, height/2, diameter*2.5f, bgPaint);
		textSize = diameter*5/(showText.length()+1);
		textPaint.setTextSize(textSize);
		FontMetrics fm = textPaint.getFontMetrics();
		float textY = (height/2)-fm.descent + (fm.descent - fm.ascent) / 2;
		canvas.drawText(showText, width/2, textY, textPaint);
		if((int)index<havingInt){
			index+=allInt*0.01;
			if(Math.abs(havingInt-index)<allInt*0.01){
				index = havingInt;
			}
			invalidate();
		}else if((int)index>havingInt){
			index-=allInt*0.01;
			if(Math.abs(havingInt-index)<allInt*0.01){
				index = havingInt;
			}
			invalidate();
		}
	}


	/**
	 * ��ʼ��
	 */
	private void init(){
		proColor = Color.parseColor("#46bb7f");
		textColor = Color.BLACK;
		bgColor = Color.WHITE;
		showText = "";
		proPaint = new Paint();
		proPaint.setColor(proColor);
		proPaint.setAntiAlias(true);
		proPaint.setStyle(Paint.Style.STROKE);
		proBgPaint = new Paint();
		proBgPaint.setColor(proColor);
		proBgPaint.setAntiAlias(true);
		proBgPaint.setStyle(Paint.Style.STROKE);
		proBgPaint.setAlpha(128);
		epPaint = new Paint();
		epPaint.setColor(proColor);
		epPaint.setAntiAlias(true);
		epPaint.setStyle(Paint.Style.FILL);
		bgPaint = new Paint();
		bgPaint.setColor(bgColor);
		bgPaint.setAntiAlias(true);
		bgPaint.setStyle(Paint.Style.FILL);
		textPaint = new Paint();
		textPaint.setColor(textColor);
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setTextAlign(Align.CENTER);
	}

	public LProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public LProgressView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public LProgressView(Context context) {
		this(context,null);
	}
	
	/**
	 * ��ȡ�Ƕ�
	 * 
	 * @param x
	 * @return
	 */
	private float getAngle() {
		if(havingInt == 0 || allInt == 0 || index == 0){
			showText = "0%";
			return 0f;
		}
		double a = 1d*index/allInt;
		showText = ((int)(a*100))+"%";
		
		return (float) (a*360);
	}
	/**
	 * Բ��λ��
	 * 
	 * @return
	 */
	private RectF getRectF() {
		if (width > height) {
			return new RectF((width - height) / 2 + (diameter * 0.5f),
					(diameter * 0.5f), width - (width - height) / 2
							- (diameter * 0.5f), height - (diameter * 0.5f));
		} else {
			return new RectF((diameter * 0.5f), (height - width) / 2
					+ (diameter * 0.5f), width - (diameter * 0.5f), height
					- (height - width) / 2 - (diameter * 0.5f));
		}
	}
	/**
	 * �������ȡ
	 * @return
	 */
	private float[] getLocation(float a) {
		float x1 = (float) (width / 2);
		float y1 = (float) (height / 2 - (diameter * 3));
		float x2 = (float) (width / 2 + (Math.cos((a-90)/180 * Math.PI) * diameter * 3));
		float y2 = (float) (height / 2 + (Math.sin((a-90)/180 * Math.PI) * diameter * 3));
		return new float[] { x1, y1, x2, y2 };
	}

	/**
	 * ��ȡ��������ɫ
	 * @return
	 */
	public int getProColor() {
		return proColor;
	}

	/**
	 * ���ý�������ɫ
	 * @param proColor
	 */
	public void setProColor(int proColor) {
		this.proColor = proColor;
		invalidate();
	}

	/**
	 * ��ȡ������ɫ
	 * @return
	 */
	public int getTextColor() {
		return textColor;
	}

	/**
	 * ����������ɫ
	 * @param textColor
	 */
	public void setTextColor(int textColor) {
		this.textColor = textColor;
		invalidate();
	}

	/**
	 * ��ȡ������ɫ
	 * @return
	 */
	public int getBgColor() {
		return bgColor;
	}

	/**
	 * ���ñ�����ɫ
	 * @param bgColor
	 */
	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
		invalidate();
	}

	/**
	 * ��ȡ����
	 * @return
	 */
	public int getAllInt() {
		return allInt;
	}

	/**
	 * ��������
	 * @param allInt
	 */
	public void setAllInt(int allInt) {
		this.allInt = allInt;
		invalidate();
	}

	/**
	 * ��ȡ�Ѿ���ɽ���
	 * @return
	 */
	public int getHavingInt() {
		return havingInt;
	}

	/**
	 * ��������ɽ���
	 * @param havingInt
	 */
	public void setHavingInt(int havingInt) {
		this.havingInt = havingInt;
		invalidate();
	}

	/**
	 * ��ȡ�Ƿ���ʾ����������
	 * @return
	 */
	public boolean isProHaveBg() {
		return proHaveBg;
	}

	/**
	 * �����Ƿ���ʾ����������
	 * @param proHaveBg
	 */
	public void setProHaveBg(boolean proHaveBg) {
		this.proHaveBg = proHaveBg;
		invalidate();
	}
	
}
