package com.l.j.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
/**
 * �¶ȼ���ͼ,������ʾ����
 * @author LiuJ
 *
 */
public class LThermometerView extends View {
	/**
	 * ��ǻ���
	 */
	private Paint shellPaint;
	/**
	 * Һ�廭��
	 */
	private Paint liquidPaint;
	/**
	 * �����ɫ
	 */
	private int shellColor = Color.parseColor("#70d29c");
	/**
	 * Һ����ɫ
	 */
	private int liquidColor = Color.parseColor("#70d29c");
	/**
	 * ����
	 */
	private float step;
	/**
	 * ���
	 */
	private int width;
	/**
	 * �߶�
	 */
	private int height;
	/**
	 * ��ǿ��
	 */
	private float shellWidth;
	/**
	 * �±�
	 */
	private int index = 0;
	/**
	 * ��ǰ�����
	 */
	private int max = 0;

	public LThermometerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		shellPaint = new Paint();
		shellPaint.setAntiAlias(true);
		shellPaint.setStyle(Paint.Style.STROKE);
		shellPaint.setColor(shellColor);
		liquidPaint = new Paint();
		liquidPaint.setAntiAlias(true);
		liquidPaint.setStyle(Paint.Style.FILL);
		liquidPaint.setColor(liquidColor);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getWidth();
		height = getHeight();
		shellWidth = (float) (width * 0.15);
		shellPaint.setStrokeWidth(shellWidth);
		step = (height-width)/100;
		/**
		 * ����Һ��߶�
		 */
		float liquidHeight = shellWidth / 2
				+ (100 - index) * step;
		if(liquidHeight<shellWidth / 2){
			liquidHeight=shellWidth / 2;
		}
		if(liquidHeight>height-width/2){
			liquidHeight=height-width/2;
		}
		/**
		 * ��Һ��
		 */
		RectF rect = new RectF(width * 0.2f+shellWidth / 2, liquidHeight, width * 0.8f-shellWidth / 2, height - width / 2);
		canvas.drawRoundRect(rect, width*0.3f, width*0.3f, liquidPaint);
		canvas.drawCircle(width / 2, height-width / 2, (width-shellWidth)/2, liquidPaint);
		/**
		 * �����
		 */
		rect = new RectF(width * 0.2f+shellWidth / 2, shellWidth / 2, width * 0.8f-shellWidth / 2, height - width / 2);
		canvas.drawRoundRect(rect, width*0.3f, width*0.3f, shellPaint);
		canvas.drawCircle(width / 2, height-width / 2, (width-shellWidth)/2, shellPaint);
		if(index>max){
			index--;
			invalidate();
		}
		if(index<max){
			index++;
			invalidate();
		}
	}

	public LThermometerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LThermometerView(Context context) {
		this(context, null);
	}
	/**
	 * ����Һ�����λ��
	 * ÿһ�ݴ����¶ȼƸ߶ȵİٷ�֮һ
	 * �������ֵΪ100
	 * @param max 
	 */
	public void setMax(int max) {
		this.max = max;
		invalidate();
	}
}
