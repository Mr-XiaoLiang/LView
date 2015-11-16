package com.l.j.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ��������
 * 
 * @author LiuJ һ����������ͻ���ʱ��İ�ť ����ʱ,������ɫ���Ż����ľ������
 */
public class LSlideButtonView extends View {
	/**
	 * �ر�״̬�ı���ɫ
	 */
	private int offColor = Color.parseColor("#d7f6e5");
	/**
	 * ��״̬�ı���ɫ
	 */
	private int onColor = Color.parseColor("#6eda9e");
	/**
	 * ��ť��ɫ
	 */
	private int btnColor = Color.parseColor("#3fc279");
	/**
	 * ��Ӱ����
	 */
	private Paint shadowPaint;
	
	/**
	 * ��λ��ɫ��ɫֵ(����������ɫ�仯)
	 */
	private float unitRed;
	/**
	 * ��λ��ɫ��ɫֵ(����������ɫ�仯)
	 */
	private float unitBlue;
	/**
	 * ��λ��ɫ��ɫֵ(����������ɫ�仯)
	 */
	private float unitGreen;
	/**
	 * ��ɫ��ɫֵ(����������ɫ�仯)
	 */
	private int red;
	/**
	 * ��ɫ��ɫֵ(����������ɫ�仯)
	 */
	private int blue;
	/**
	 * ��ɫ��ɫֵ(����������ɫ�仯)
	 */
	private int green;
	/**
	 * ��ť��״̬
	 */
	private boolean type = false;
	/**
	 * �Ƿ���ָ�ɿ�
	 */
	private boolean isTouchUp = true;
	/**
	 * ��ָ����ʱ�� �����ж���ָ�¼�����
	 */
	private long downTime = 0;
	/**
	 * ��ָ�ɿ�ʱ�� �����ж���ָ�¼�����
	 */
	private long upTime = 0;
	/**
	 * X����,�����ð�ťճ��
	 */
	private int X = 0;
	/**
	 * ���ֻ���
	 */
	private Paint textPaint;
	/**
	 * ��ť����
	 */
	private Paint btnPaint;
	/**
	 * ��������
	 */
	private Paint bgPaint;
	/**
	 * ������� �����ð�ť����
	 */
	private int width;
	/**
	 * �����߶�, �����ð�ť����
	 */
	private int height;
	/**
	 * ��ť�뾶
	 */
	private int radius;
	/**
	 * ��λ���
	 */
	private int index = 0;
	/**
	 * �ص�����
	 */
	private SlideButtonViewListener lis;
	/**
	 * �Ƕ�
	 */
	private float angle = 0;

	public LSlideButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		bgPaint = new Paint();
		bgPaint.setAntiAlias(true);
		bgPaint.setStyle(Paint.Style.FILL);

		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setColor(Color.WHITE);

		btnPaint = new Paint();
		btnPaint.setAntiAlias(true);
		btnPaint.setStyle(Paint.Style.FILL);
		btnPaint.setColor(btnColor);

		shadowPaint = new Paint();
		shadowPaint.setAntiAlias(true);
		shadowPaint.setStyle(Paint.Style.FILL);
		shadowPaint.setColor(Color.GRAY);
		shadowPaint.setAlpha(128);
		
		red = Color.red(offColor);
		blue = Color.blue(offColor);
		green = Color.green(offColor);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getWidth();
		height = getHeight();
		/**
		 * ����ߴ�,�ð�ť���κ�����²�����
		 */
		if (width > height * 2) {
			radius = height / 2;
		} else {
			radius = width / 4;
		}
		//���Ǽ���Ӱ���㷨����
//		radius*=0.9;
		if (X == 0) {
			X = width / 2 - radius;
		}
		index = radius / 10;
		calculateUnitColor();
		drawBg(canvas);
		drawButton(canvas);
		// ��û�����������
		if (isTouchUp) {
			if (type) {
				if (X < (width / 2 + radius)) {
					X += index;
					// ��ֹ�ܹ�ͷ
					if (X > (width / 2 + radius)) {
						X = (width / 2 + radius);
					}
					invalidate();
				}
			} else {
				if (X > (width / 2 - radius)) {
					X -= index;
					// ��ֹ�ܹ�ͷ
					if (X < (width / 2 - radius)) {
						X = (width / 2 - radius);
					}
					invalidate();
				}
			}
		}
	}

	private void getAngle(){
		angle = 720f/(radius*2);
		angle = (X-(width/2-radius))*angle;
		if(X == (width / 2 - radius)){
			angle = 0;
		}
		if(X == (width / 2 + radius)){
			angle = 720;
		}
	}
	
	/**
	 * ���㵱ǰ����ɫֵ
	 */
	private void drawBg(Canvas canvas) {
		int index = X - width / 2 + radius;
		bgPaint.setColor(Color.rgb(red - (int) (index * unitRed), green
				- (int) (index * unitGreen), blue - (int) (index * unitBlue)));
		// ��ťͻ��
		RectF rect = new RectF(width / 2 - radius * 2, height / 2 - radius*0.7f,
				width / 2 + radius * 2, height / 2 + radius*0.7f);
		canvas.drawRoundRect(rect, radius*0.7f, radius*0.7f, bgPaint);
		// ��ťȫ��
//		 RectF rect = new RectF(width / 2 - radius * 2, height / 2 - radius,
//		 width / 2 + radius * 2, height / 2+radius);
//		 canvas.drawRoundRect(rect, radius, radius, bgPaint);
	}

	private void drawButton(Canvas canvas) {
		//����Ӱ
//		canvas.drawCircle(X+radius*0.1f, height / 2+radius*0.1f, radius, shadowPaint);
		float t = 2 * radius * 0.5f / radius;
		getAngle();
		t = Math.abs(t * (X - radius * 2));
		canvas.drawCircle(X, height / 2, radius, btnPaint);
		String str;
		if (X > width / 2) {
			str = "ON";
		} else {
			str = "OFF";
		}
		textPaint.setTextSize(t);
		FontMetrics fm = textPaint.getFontMetrics();
		float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
		canvas.rotate(angle, X, height / 2);
		canvas.drawText(str, X, height / 2 + textY, textPaint);
		canvas.rotate(-angle, X, height / 2);
	}

	/**
	 * ��λ��ɫ���� ��֤��ɫ�����˳��
	 */
	private void calculateUnitColor() {
		float startRed = Color.red(offColor);
		float startBlue = Color.blue(offColor);
		float startGreen = Color.green(offColor);
		float endRed = Color.red(onColor);
		float endBlue = Color.blue(onColor);
		float endGreen = Color.green(onColor);
		unitRed = Math.abs(startRed - endRed) / radius / 2;
		unitBlue = Math.abs(startBlue - endBlue) / radius / 2;
		unitGreen = Math.abs(startGreen - endGreen) / radius / 2;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		X = (int) event.getX();
		if (X < (width / 2 - radius)) {
			X = (width / 2 - radius);
		}
		if (X > (width / 2 + radius)) {
			X = (width / 2 + radius);
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downTime = System.currentTimeMillis();
			isTouchUp = false;
			break;
		case MotionEvent.ACTION_UP:
			upTime = System.currentTimeMillis();
			isTouchUp = true;
			if (upTime - downTime <= 300) {
				if (type) {
					type = false;
				} else {
					type = true;
				}
			} else {
				if (X > width / 2) {
					type = true;
				} else {
					type = false;
				}
			}
			if (lis != null) {
				lis.SlideButtonOnClick(this, type);
			}
			break;
		default:
			break;
		}
		invalidate();
		return true;
	}

	public LSlideButtonView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LSlideButtonView(Context context) {
		this(context, null);
	}

	/**
	 * ������ť�ĵ���¼�
	 * 
	 * @author LiuJ
	 *
	 */
	public interface SlideButtonViewListener {
		public void SlideButtonOnClick(LSlideButtonView SlideButtonView,
				boolean isOpen);
	}

	/**
	 * ���ü�����
	 * 
	 * @param lis
	 */
	public void setOnSlideListener(SlideButtonViewListener lis) {
		this.lis = lis;
	}

	/**
	 * �����Ƿ��
	 * 
	 * @param type
	 */
	public void setOpen(boolean type) {
		this.type = type;
	}

	/**
	 * �Ƿ��Ѵ�
	 * 
	 * @return
	 */
	public boolean isOpen() {
		return type;
	}

}
