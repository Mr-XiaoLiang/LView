package com.l.j.view;

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
 * ��¼���滬������
 * 
 * @author yanluxi
 *
 */
public class LChooseBgView extends View {
	/**
	 * �Ƿ�����߱�ѡ��
	 */
	private boolean isLeft = true;
	/**
	 * ���
	 */
	private int width;
	/**
	 * �߶�
	 */
	private int height;
	/**
	 * ��ɫ����
	 */
	private Paint darkPaint;
	/**
	 * ǳɫ����
	 */
	private Paint lightPaint;
	/**
	 * �����ƶ��ı��
	 */
	private int index = 0;
	/**
	 * �����θ߶�
	 */
	private int triHeight;
	/**
	 * �����ο��
	 */
	private int triWidth;
	/**
	 * ��ɫ�ߵĿ��
	 */
	private int lineWidth = 1;
	/**
	 * ��ߵ�����
	 */
	private String leftName = "";
	/**
	 * �ұߵ�����
	 */
	private String rightName = "";
	/**
	 * �����С
	 */
	private float textSize = 0;
	/**
	 * �����С��ߵı���
	 */
	private float textRatio = 0.3F;
	/**
	 * �ƶ�����
	 */
	private int stepSize = 30;
	/**
	 * ��ɫ���ֻ���
	 */
	private Paint darkTextPaint;
	/**
	 * ǳɫ���ֻ���
	 */
	private Paint lightTextPaint;
	/**
	 * ����ص�
	 */
	private ChooseBgViewListener listener;
	/**
	 * ��������
	 */
	private Paint bgPaint;
	/**
	 * �Ƿ���������
	 */
	private boolean style = false;

	public LChooseBgView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		darkPaint = new Paint();
		darkPaint.setAntiAlias(true);
		darkPaint.setStyle(Paint.Style.FILL);
		// darkPaint.setColor(Color.parseColor(""));//����Զ����ɫ,�������д���,ע����������
		darkPaint.setColor(Color.GRAY);
		darkPaint.setStrokeWidth(lineWidth);// �����ǻ�ɫ�ߵĿ��
		lightPaint = new Paint();
		lightPaint.setAntiAlias(true);
		lightPaint.setStyle(Paint.Style.FILL);
		lightPaint.setColor(Color.WHITE);

		bgPaint = new Paint();
		bgPaint.setAntiAlias(true);
		bgPaint.setColor(Color.parseColor("#e7e7e7"));// ������ɫ

		darkTextPaint = new Paint();
		darkTextPaint.setAntiAlias(true);
		darkTextPaint.setTextAlign(Align.CENTER);
		darkTextPaint.setColor(Color.GRAY);

		lightTextPaint = new Paint();
		lightTextPaint.setAntiAlias(true);
		lightTextPaint.setTextAlign(Align.CENTER);
		lightTextPaint.setColor(Color.BLUE);// ������ɫ
	}

	public LChooseBgView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LChooseBgView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getWidth();
		height = getHeight();
		triHeight = height / 6;// ���Ǽ��������θ߶�,�����θ߶��ǿ�ȵ�һ��
		triWidth = triHeight * 2;
		/**
		 * ����ǻ���,index��λ�ö�λ���ߵ����,Ҳ����˵ ����������λ�Ʒ�Χ��0~width/2
		 * �������յ��λ�Ʒ�Χ��width/2~width index ��λ�Ʒ�Χ����0~width/2
		 */
		if (style) {
			if (index == 0) {
				index = width / 4;// ��ʱ�������� index=widtn/4*3 ���������Լ�ѡ��

			}
		}
		/**
		 * ������
		 */
		canvas.drawRect(0, 0, width, height, bgPaint);

		/**
		 * ���ǻ�����Ļ���.ע�ͺ�Ͳ�����
		 */
		canvas.drawLine(0, height, width, height, darkPaint);
		/**
		 * ���ǻ�����Ļ���.ע�ͺ�Ͳ�����
		 */
		canvas.drawLine(0, 1, width, 1, darkPaint);
		/**
		 * ��������
		 */
		drawTriangle(canvas);
		drawText(canvas);
		/**
		 * ����λ��,���������λ��Ҫ��ͻ�
		 */
		if (style) {
			if (isLeft) {
				if (index < (width / 4) / stepSize * stepSize) {
					index += stepSize;
					invalidate();
				} else if (index > (width / 4) / stepSize * stepSize) {
					index -= stepSize;
					invalidate();
				}
			} else {
				if (index < (width / 4 * 3) / stepSize * stepSize) {
					index += stepSize;
					invalidate();
				} else if (index > (width / 4 * 3) / stepSize * stepSize) {
					index -= stepSize;
					invalidate();
				}
			}
		} else {
			if (isLeft) {
				if (index < 0) {
					index += stepSize;
					invalidate();
				} else if (index > 0) {
					index -= stepSize;
					invalidate();
				}
			} else {
				if (index < (width / 2) / stepSize * stepSize) {
					index += stepSize;
					invalidate();
				} else if (index > (width / 2) / stepSize * stepSize) {
					index -= stepSize;
					invalidate();
				}
			}
		}
	}

	/**
	 * ��������
	 */
	private void drawText(Canvas canvas) {
		textSize = height * textRatio;
		darkTextPaint.setTextSize(textSize);
		lightTextPaint.setTextSize(textSize);
		// ���ָ߶Ƚ���
		FontMetrics fm = lightTextPaint.getFontMetrics();
		float textY = height / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
		if (isLeft) {
			canvas.drawText(leftName, width / 4, textY, lightTextPaint);
			canvas.drawText(rightName, width / 4 * 3, textY, darkTextPaint);
		} else {
			canvas.drawText(leftName, width / 4, textY, darkTextPaint);
			canvas.drawText(rightName, width / 4 * 3, textY, lightTextPaint);
		}
		// ���ָ���
		canvas.drawLine(width / 2, 0, width / 2, height, darkPaint);
	}

	/**
	 * �����εĻ��Ʒ���
	 * 
	 * @param canvas
	 */
	private void drawTriangle(Canvas canvas) {
		if(style){
			/**
			 * �Ȼ��������ε����
			 */
			 Path path = new Path();
			 path.moveTo(index, height-triHeight-lineWidth);
			 path.lineTo(index-triWidth/2-lineWidth, height);
			 path.lineTo(index+triWidth/2+lineWidth, height);
			 path.close();
			 canvas.drawPath(path, darkPaint);
			/**
			 * �ٻ��������ε����
			 */
			 path = new Path();
			 path.moveTo(index, height-triHeight);
			 path.lineTo(index-triWidth/2, height);
			 path.lineTo(index+triWidth/2, height);
			 path.close();
			 canvas.drawPath(path, lightPaint);
		}else{
			/**
			 * ����һ����
			 */
			canvas.drawLine(index, height - lineWidth, index + width / 2, height
					- lineWidth, lightTextPaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (event.getX() > width / 2) {
				isLeft = false;
			} else {
				isLeft = true;
			}
			if (listener != null) {
				listener.onChooseViewClick(isLeft);
			}
			invalidate();
		}
		return true;
	}

	public boolean isLeft() {
		return isLeft;
	}

	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
		invalidate();
	}

	public String getLeftName() {
		return leftName;
	}

	public void setLeftName(String leftName) {
		this.leftName = leftName;
	}

	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public interface ChooseBgViewListener {
		public void onChooseViewClick(boolean isLeft);
	}

	public void setChooseBgViewListener(ChooseBgViewListener listener) {
		this.listener = listener;
	}

	public boolean getStyle() {
		return style;
	}

	public void setStyle(boolean style) {
		this.style = style;
	}
	
}
