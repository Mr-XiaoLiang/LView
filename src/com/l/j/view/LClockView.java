package com.l.j.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.mytest.R;

/**
 * ������ʽ��ʱ��ѡ����
 * 
 * @author Liuj
 *
 */
public class LClockView extends View {

	/*-------------------------����������ʼ-------------------------------*/
	/**
	 * Сʱ
	 */
	public static final int TYPE_HOURS = 0;
	/**
	 * ����(��ͬ��)
	 */
	public static final int TYPE_MINUTES = 1;
	/*-------------------------������������-------------------------------*/

	/*-------------------------����������ʼ-------------------------------*/
	/**
	 * ���
	 */
	private float width;
	/**
	 * �߶�
	 */
	private float height;
	/**
	 * �뾶
	 */
	private float radius;
	/**
	 * ʱ������
	 */
	private int type;
	/**
	 * Сʱ����
	 */
	private int[] hoursItems;
	/**
	 * ��������
	 */
	private int[] minutesItems;
	/**
	 * �߿���ɫ
	 */
	private int borderColor;
	/**
	 * ����ɫ
	 */
	private int backgroundColor;
	/**
	 * ������ɫ
	 */
	private int dialColor;
	/**
	 * �߿���
	 */
	private float borderWidth;
	/**
	 * �����С
	 */
	private float textSize;
	/**
	 * ������ɫ
	 */
	private int textColor;
	/**
	 * ����ͼƬ
	 */
	private int dialImage;
	/**
	 * ָ����ɫ
	 */
	private int pointerColor;
	/**
	 * ������������ɫ
	 */
	private int textColorForUn;
	/**
	 * �̶���ɫ
	 */
	private int scaleColor;
	/**
	 * ָ��Ƕ�
	 */
	private double pointerAngle = 0f;
	/**
	 * ��ѡ��
	 */
	private int selected = 0;
	/**
	 * ��ѡ����ɫ
	 */
	private int selectedColor;
	/**
	 * ��ѡ��
	 */
	private int realSelected = 0;
	/**
	 * ָ��Ƕ�
	 */
	private double realPointerAngle = 0f;
	/**
	 * ��ѡ�л���
	 */
	private Paint selectedPaint;
	/**
	 * ���ֻ���
	 */
	private Paint textPaint;
	/**
	 * ��Ȧ���ֻ���
	 */
	private Paint smallTextPaint;
	/**
	 * ���ֻ���-- ��ɫ
	 */
	private Paint textGaryPaint;
	/**
	 * ��Ȧ���ֻ���-- ��ɫ
	 */
	private Paint smallTextGaryPaint;
	/**
	 * �߿򻭱�
	 */
	private Paint borderPaint;
	/**
	 * ָ�뻭��
	 */
	private Paint pointerPaint;
	/**
	 * ��������
	 */
	private Paint backgroundPaint;
	/**
	 * ���̻���
	 */
	private Paint dialPaint;
	/**
	 * ��̶Ȼ���
	 */
	private Paint scaleBigPaint;
	/**
	 * С�̶Ȼ���
	 */
	private Paint scaleSmallPaint;

	/**
	 * ѡ�����ֻ���
	 */
	private Paint selectedTextPaint;
	/**
	 * ѡ�����ֻ���
	 */
	private Paint selectedSmallTextPaint;
	
	/*-------------------------������������-------------------------------*/
	/*-------------------------�ص�����������ʼ-------------------------------*/
	public interface ClockViewListener {
		public void onClockChange(int t);
	}
	/**
	 * �ص�����
	 */
	private ClockViewListener lis;
	/*-------------------------�ص�����������ʼ-------------------------------*/
	/*-------------------------���췽����ʼ-------------------------------*/
	public LClockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		/**
		 * ������ʼ��
		 */
		type = TYPE_HOURS;
		hoursItems = new int[24];
		minutesItems = new int[60];
		borderColor = Color.GRAY;
		dialColor = Color.WHITE;
		backgroundColor = Color.TRANSPARENT;
		textColor = Color.BLACK;
		pointerColor = Color.RED;
		textColorForUn = Color.parseColor("#50000000");
		scaleColor = Color.BLACK;
		selectedColor = Color.parseColor("#99c9f2");
		dialImage = 0;
		for (int i = 0; i < hoursItems.length; i++) {
			hoursItems[i] = i;
		}
		for (int i = 0; i < minutesItems.length; i++) {
			minutesItems[i] = i;
		}
		/**
		 * ���������������Զ�����ʽ����
		 */
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.clockview, defStyleAttr, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.clockview_background_color:
				backgroundColor = a.getColor(attr, Color.TRANSPARENT);
				break;
			case R.styleable.clockview_background_image:
				dialImage = a.getInt(attr, 0);
				break;
			case R.styleable.clockview_border_color:
				borderColor = a.getColor(attr, Color.GRAY);
				break;
			case R.styleable.clockview_border_width:
				borderWidth = a.getDimension(attr, 0);
				break;
			case R.styleable.clockview_dial_color:
				dialColor = a.getColor(attr, Color.WHITE);
				break;
			case R.styleable.clockview_pointer_color:
				pointerColor = a.getColor(attr, Color.RED);
				break;
			case R.styleable.clockview_scale_color:
				scaleColor = a.getColor(attr, Color.BLACK);
				break;
			case R.styleable.clockview_text_color:
				textColor = a.getColor(attr, Color.BLACK);
				break;
			case R.styleable.clockview_text_color_un:
				textColorForUn = a.getColor(attr, Color.GRAY);
				break;
			case R.styleable.clockview_type:
				type = a.getInt(attr, TYPE_HOURS);
				break;

			}
		}
		a.recycle();
		init();
	}

	public LClockView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LClockView(Context context) {
		this(context, null);
	}

	/*-------------------------���췽������-------------------------------*/

	@Override
	protected void onDraw(Canvas canvas) {
		height = getHeight();
		width = getWidth();
		if (width > height) {
			radius = height / 2 - borderWidth;
		} else {
			radius = width / 2 - borderWidth;
		}

		if (textSize == 0) {
			textSize = radius / 8;
			textPaint.setTextSize(textSize);
			smallTextPaint.setTextSize(textSize / 2);
			textGaryPaint.setTextSize(textSize);
			smallTextGaryPaint.setTextSize(textSize / 2);
			selectedTextPaint.setTextSize(textSize);
			selectedSmallTextPaint.setTextSize(textSize / 2);
		}
		/**
		 * ����ͼ����
		 */
		canvas.drawRect(0, 0, width, height, backgroundPaint);
		/**
		 * ������ɫ
		 */
		canvas.drawCircle(width / 2, height / 2, radius, dialPaint);
		/**
		 * ������ͼ
		 */
		if (dialImage > 0) {
			Bitmap roundBitmap = getCroppedRoundBitmap();
			canvas.drawBitmap(roundBitmap, width / 2 - radius, height / 2
					- radius, null);
		}
		
		float[] scale;
		for (int i = 0; i < 60; i++) {
			/**
			 * ��С�̶�
			 */
			scale = getSmallScale(i);
			canvas.drawLine(scale[0], scale[1], scale[2], scale[3],
					scaleSmallPaint);
			if (i % 5 == 0) {
				/**
				 * ����̶�
				 */
				scale = getBigScale(i);
				canvas.drawLine(scale[0], scale[1], scale[2], scale[3],
						scaleBigPaint);
			}
		}
		/**
		 * ������
		 */
		FontMetrics fm = textPaint.getFontMetrics();
		float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
		if (type == TYPE_HOURS) {
			FontMetrics fm2 = smallTextPaint.getFontMetrics();
			float textY2 = -fm2.descent + (fm2.descent - fm2.ascent) / 2;
			for (int i = 0; i < hoursItems.length; i++) {
				scale = getTextLocation(i);
				if (i < 12) {
					if (selected == i) {
						canvas.drawCircle(scale[0], scale[1], textSize * 0.7f,
								selectedPaint);
						canvas.drawText(i + "", scale[0], scale[1] + textY,
								selectedTextPaint);
					}
					if (hoursItems[i] > 0) {
						canvas.drawText(i + "", scale[0], scale[1] + textY,
								textPaint);
					} else {
						canvas.drawText(i + "", scale[0], scale[1] + textY,
								textGaryPaint);
					}
				} else {
					if (selected == i) {
						canvas.drawCircle(scale[0], scale[1], textSize * 0.7f,
								selectedPaint);
						canvas.drawText(i + "", scale[0], scale[1] + textY2,
								selectedSmallTextPaint);
					}
					if (hoursItems[i] > 0) {
						canvas.drawText(i + "", scale[0], scale[1] + textY2,
								smallTextPaint);
					} else {
						canvas.drawText(i + "", scale[0], scale[1] + textY2,
								smallTextGaryPaint);
					}
				}
			}
		} else {
			for (int i = 0; i < minutesItems.length; i++) {
				scale = getTextLocation(i);
				if (selected == i) {
					canvas.drawCircle(scale[0], scale[1], textSize * 0.7f,
							selectedPaint);
					canvas.drawText(i + "", scale[0], scale[1] + textY,
							selectedTextPaint);
				}
				if (i % 5 != 0) {
					continue;
				}
				if (minutesItems[i] > 0) {
					canvas.drawText(i + "", scale[0], scale[1] + textY,
							textPaint);
				} else {
					canvas.drawText(i + "", scale[0], scale[1] + textY,
							textGaryPaint);
				}
			}
		}
		/**
		 * ��ָ��С���
		 */
		 canvas.drawCircle(width / 2, height / 2, radius/20,pointerPaint );
		/**
		 * ��ָ��
		 */
		canvas.drawPath(getPointerLocation(), pointerPaint);
		// scale = getPointerLocation();
		// canvas.drawLine(width / 2, height / 2, scale[0], scale[1],
		// pointerPaint);
		super.onDraw(canvas);
	}

	/*-------------------------�������÷�����ʼ-------------------------------*/
	/**
	 * ����ѡ���� ����Ҫ��ȷ��type���� ����һ��ѡ��������� type Ϊ hoursʱ,���ֵΪ23,��СֵΪ0 type Ϊ
	 * minutesʱ,���ֵΪ59,��СֵΪ0 ����Ƿ�ֵ������
	 */
	public void setItem(int[] items) {
		switch (type) {
		case TYPE_HOURS:
			// ���Сʱ����
			hoursItems = new int[24];
			for (int i = 0; i < hoursItems.length; i++) {
				hoursItems[i] = -1;
			}
			// ����Сʱ����
			for (int i = 0; i < items.length; i++) {
				if (items[i] < 24 && items[i] >= 0) {
					hoursItems[items[i]] = 1;
				}
			}
			for (int i = 0; i < hoursItems.length; i++) {
				if (hoursItems[i] > 0) {
					selected = i;
					realSelected = i;
					realPointerAngle = i * 30;
					pointerAngle = i * 30;
					break;
				}
			}
			break;
		case TYPE_MINUTES:
			// ��շ�������
			minutesItems = new int[60];
			for (int i = 0; i < minutesItems.length; i++) {
				minutesItems[i] = -1;
			}
			// ���÷�������
			for (int i = 0; i < items.length; i++) {
				if (items[i] < 60 && items[i] >= 0) {
					minutesItems[items[i]] = 1;
				}
			}
			for (int i = 0; i < minutesItems.length; i++) {
				if (minutesItems[i] > 0) {
					selected = i;
					realSelected = i;
					realPointerAngle = i * 6;
					pointerAngle = i * 6;
					break;
				}
			}
			break;
		}
		invalidate();
	}

	/**
	 * ����ѡ���� ����Ҫ��ȷ��type���� type Ϊ hoursʱ,���ֵΪ23,��СֵΪ0 type Ϊ
	 * minutesʱ,���ֵΪ59,��СֵΪ0 ����ѡ���������
	 * 
	 * @param min
	 * @param max
	 */
	public void setItem(int min, int max) {
		if (min < 0) {
			min = 0;
		}
		switch (type) {
		case TYPE_HOURS:
			if (max > 23) {
				max = 23;
			}
			// ���Сʱ����
			hoursItems = new int[24];
			for (int i = 0; i < hoursItems.length; i++) {
				hoursItems[i] = -1;
			}
			for (int i = min; i <= max; i++) {
				hoursItems[i] = 1;
			}
			for (int i = 0; i < hoursItems.length; i++) {
				if (hoursItems[i] > 0) {
					selected = i;
					realSelected = i;
					realPointerAngle = i * 30;
					pointerAngle = i * 30;
					break;
				}
			}
			break;
		case TYPE_MINUTES:
			if (max > 59) {
				max = 59;
			}
			// ��շ�������
			minutesItems = new int[60];
			for (int i = 0; i < minutesItems.length; i++) {
				minutesItems[i] = -1;
			}
			for (int i = min; i <= max; i++) {
				minutesItems[i] = 1;
			}
			for (int i = 0; i < minutesItems.length; i++) {
				if (minutesItems[i] > 0) {
					selected = i;
					realSelected = i;
					realPointerAngle = i * 6;
					pointerAngle = i * 6;
					break;
				}
			}
			break;
		}
		invalidate();
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * ��������(Сʱ��,������)
	 * 
	 * @param type
	 *            Сʱ TYPE_HOURS ����(��ͬ��) TYPE_MINUTES
	 */
	public void setType(int type) {
		this.type = type;
		invalidate();
	}

	/**
	 * ��ȡ�߿���ɫ
	 * 
	 * @return
	 */
	public int getBorderColor() {
		return borderColor;
	}

	/**
	 * ���ñ߿���ɫ
	 * 
	 * @param borderColor
	 */
	public void setBorderColor(int borderColor) {
		this.borderColor = borderColor;
		invalidate();
	}

	/**
	 * ��ȡ����ɫ
	 * 
	 * @return
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * ���ñ���ɫ
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
		invalidate();
	}

	/**
	 * ��ȡ������ɫ
	 * 
	 * @return
	 */
	public int getDialColor() {
		return dialColor;
	}

	/**
	 * ���ñ�����ɫ
	 * 
	 * @param dialColor
	 */
	public void setDialColor(int dialColor) {
		this.dialColor = dialColor;
		invalidate();
	}

	/**
	 * ��ȡ������߿��
	 * 
	 * @return
	 */
	public float getDialWidth() {
		return borderWidth;
	}

	/**
	 * ���ñ�����߿��
	 * 
	 * @param dialWidth
	 */
	public void setDialWidth(float dialWidth) {
		this.borderWidth = dialWidth;
		invalidate();
	}

	/**
	 * ��ȡ�����С
	 * 
	 * @return
	 */
	public float getTextSize() {
		return textSize;
	}

	/**
	 * ���������С,�˴��趨Ϊ��׼����,���ѵ�ʱ����Ȧ������Զ���С
	 * 
	 * @param textSize
	 */
	public void setTextSize(float textSize) {
		this.textSize = textSize;
		invalidate();
	}

	/**
	 * ��ȡ������ɫ
	 * 
	 * @return
	 */
	public int getTextColor() {
		return textColor;
	}

	/**
	 * ����������ɫ
	 * 
	 * @param textColor
	 *            �����ֲ����ñ���Ϊ��ɫ,������û�ɫ�ᵼ�����ֲ���
	 */
	public void setTextColor(int textColor) {
		this.textColor = textColor;
		invalidate();
	}

	/**
	 * ��ȡ������ɫ
	 * 
	 * @return
	 */
	public int getBackgroundImage() {
		return dialImage;
	}

	/**
	 * ���ñ�����ɫ
	 * 
	 * @param backgroundImage
	 */
	public void setBackgroundImage(int backgroundImage) {
		this.dialImage = backgroundImage;
		invalidate();
	}

	/**
	 * ��ȡ�����õ��������ɫ
	 * 
	 * @return
	 */
	public int getTextColorForUn() {
		return textColorForUn;
	}

	/**
	 * ���ò������������ɫ
	 * 
	 * @param textColorForUn
	 */
	public void setTextColorForUn(int textColorForUn) {
		this.textColorForUn = textColorForUn;
	}
	/**
	 * ��ȡ�ص�����
	 * @return
	 */
	public ClockViewListener getClockViewListener() {
		return lis;
	}
	/**
	 * ���ûص�����
	 * @param lis
	 */
	public void setClockViewListener(ClockViewListener lis) {
		this.lis = lis;
	}

	/*-------------------------�������÷�������-------------------------------*/
	/**
	 * ��ȡ�ü����Բ��ͼƬ
	 * 
	 * @param radius
	 *            �뾶
	 */
	private Bitmap getCroppedRoundBitmap() {
		Bitmap scaledSrcBmp;
		int diameter = (int) (radius * 2);
		Resources res = getResources();
		Bitmap bmp = BitmapFactory.decodeResource(res, dialImage);
		// Ϊ�˷�ֹ��߲���ȣ����Բ��ͼƬ���Σ���˽�ȡ�������д����м�λ������������ͼƬ
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {// �ߴ��ڿ�
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// ��ȡ������ͼƬ
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else if (bmpHeight < bmpWidth) {// ����ڸ�
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else {
			squareBitmap = bmp;
		}

		if (squareBitmap.getWidth() != diameter
				|| squareBitmap.getHeight() != diameter) {
			scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,
					diameter, true);

		} else {
			scaledSrcBmp = squareBitmap;
		}
		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
				scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		// bitmap����(recycle�����ڲ����ļ�XML������Ч��)
		// bmp.recycle();
		// squareBitmap.recycle();
		// scaledSrcBmp.recycle();
		bmp = null;
		squareBitmap = null;
		scaledSrcBmp = null;
		return output;
	}

	private void init() {
		/**
		 * ���ֻ���
		 */
		textPaint = new Paint();
		textPaint.setColor(textColor);
		textPaint.setTextSize(textSize);
		textPaint.setAntiAlias(true);
		textPaint.setTextAlign(Align.CENTER);
		/**
		 * С���ֻ���
		 */
		smallTextPaint = new Paint();
		smallTextPaint.setColor(textColor);
		smallTextPaint.setTextSize(textSize - 10);
		smallTextPaint.setAntiAlias(true);
		smallTextPaint.setTextAlign(Align.CENTER);
		/**
		 * ���ֻ���-- ��ɫ
		 */
		textGaryPaint = new Paint();
		textGaryPaint.setColor(textColorForUn);
		textGaryPaint.setTextSize(textSize);
		textGaryPaint.setAntiAlias(true);
		textGaryPaint.setTextAlign(Align.CENTER);
		/**
		 * ��Ȧ���ֻ���-- ��ɫ
		 */
		smallTextGaryPaint = new Paint();
		smallTextGaryPaint.setColor(textColorForUn);
		smallTextGaryPaint.setTextSize(textSize - 10);
		smallTextGaryPaint.setAntiAlias(true);
		smallTextGaryPaint.setTextAlign(Align.CENTER);
		/**
		 * �߿򻭱�
		 */
		borderPaint = new Paint();
		borderPaint.setColor(borderColor);
		borderPaint.setAntiAlias(true);
		borderPaint.setStrokeWidth(borderWidth);
		/**
		 * ָ�뻭��
		 */
		pointerPaint = new Paint();
		pointerPaint.setAntiAlias(true);
		pointerPaint.setColor(pointerColor);
		pointerPaint.setStyle(Paint.Style.FILL);
		/**
		 * ��������
		 */
		backgroundPaint = new Paint();
		backgroundPaint.setAntiAlias(true);
		backgroundPaint.setColor(backgroundColor);
		/**
		 * ���̻���
		 */
		dialPaint = new Paint();
		dialPaint.setAntiAlias(true);
		dialPaint.setColor(dialColor);
		/**
		 * ��̶Ȼ���
		 */
		scaleBigPaint = new Paint();
		scaleBigPaint.setAntiAlias(true);
		scaleBigPaint.setColor(scaleColor);
		scaleBigPaint.setStrokeWidth(10);
		/**
		 * С�̶Ȼ���
		 */
		scaleSmallPaint = new Paint();
		scaleSmallPaint.setAntiAlias(true);
		scaleSmallPaint.setColor(scaleColor);
		scaleSmallPaint.setStrokeWidth(5);
		/**
		 * ��ѡ�л���
		 */
		selectedPaint = new Paint();
		selectedPaint.setAntiAlias(true);
		selectedPaint.setColor(selectedColor);
		/**
		 * ѡ�е����ֵĻ���
		 */
		selectedTextPaint = new Paint();
		selectedTextPaint.setAntiAlias(true);
		selectedTextPaint.setTextSize(textSize);
		selectedTextPaint.setColor(Color.WHITE);
		selectedTextPaint.setTextAlign(Align.CENTER);
		/**
		 * ѡ�е�С���ֵĻ���
		 */
		selectedSmallTextPaint = new Paint();
		selectedSmallTextPaint.setAntiAlias(true);
		selectedSmallTextPaint.setTextSize(textSize);
		selectedSmallTextPaint.setColor(Color.WHITE);
		selectedSmallTextPaint.setTextAlign(Align.CENTER);
	}

	/**
	 * ��ȡ��̶ȵ�λ������
	 * 
	 * @param index
	 * @return
	 */
	private float[] getBigScale(int index) {
		double length = radius * 0.04f;
		float x1 = 0f;
		float x2 = 0f;
		float y1 = 0f;
		float y2 = 0f;
		x1 = (float) (Math.sin(2 * Math.PI / 360 * 30 * index) * radius + (width / 2));
		y1 = (float) (-Math.cos(2 * Math.PI / 360 * 30 * index) * radius + (height / 2));
		x2 = (float) (Math.sin(2 * Math.PI / 360 * 30 * index)
				* (radius - length) + (width / 2));
		y2 = (float) (-Math.cos(2 * Math.PI / 360 * 30 * index)
				* (radius - length) + (height / 2));
		return new float[] { x1, y1, x2, y2 };
	}

	/**
	 * ��ȡС�̶ȵ�λ������
	 * 
	 * @param index
	 * @return
	 */
	private float[] getSmallScale(int index) {
		double length = radius * 0.02f;
		float x1 = 0f;
		float x2 = 0f;
		float y1 = 0f;
		float y2 = 0f;
		x1 = (float) (Math.sin(2 * Math.PI / 360 * 6 * index) * radius + (width / 2));
		y1 = (float) (-Math.cos(2 * Math.PI / 360 * 6 * index) * radius + (height / 2));
		x2 = (float) (Math.sin(2 * Math.PI / 360 * 6 * index)
				* (radius - length) + (width / 2));
		y2 = (float) (-Math.cos(2 * Math.PI / 360 * 6 * index)
				* (radius - length) + (height / 2));
		return new float[] { x1, y1, x2, y2 };
	}

	/**
	 * ��ȡ���ֵ�λ������
	 * 
	 * @param index
	 * @return
	 */
	private float[] getTextLocation(int index) {
		double length = (radius * 0.1f) + (textSize / 3);
		float x = 0f;
		float y = 0f;
		if (type == TYPE_HOURS) {
			if (index < 12) {
				x = (float) (Math.sin(2 * Math.PI / 360 * 30 * index)
						* (radius - length) + (width / 2));
				y = (float) (-Math.cos(2 * Math.PI / 360 * 30 * index)
						* (radius - length) + (height / 2));
			} else {
				x = (float) (Math.sin(2 * Math.PI / 360 * 30 * index)
						* (radius - length - (textSize * 1.5)) + (width / 2));
				y = (float) (-Math.cos(2 * Math.PI / 360 * 30 * index)
						* (radius - length - (textSize * 1.5)) + (height / 2));
			}
		} else {
			x = (float) (Math.sin(2 * Math.PI / 360 * 6 * index)
					* (radius - length) + (width / 2));
			y = (float) (-Math.cos(2 * Math.PI / 360 * 6 * index)
					* (radius - length) + (height / 2));
		}
		return new float[] { x, y };
	}

	/**
	 * ��ȡָ��λ��
	 * 
	 * @return
	 */
	private Path getPointerLocation() {

		float x1 = (float) (Math.sin(2 * Math.PI / 360 * pointerAngle)
				* (radius * 0.6) + (width / 2));
		float y1 = (float) (-Math.cos(2 * Math.PI / 360 * pointerAngle)
				* (radius * 0.6) + (height / 2));
		float x2 = (float) (Math.sin(2 * Math.PI / 360 * (pointerAngle + 90))
				* (radius * 0.05) + (width / 2));
		float y2 = (float) (-Math.cos(2 * Math.PI / 360 * (pointerAngle + 90))
				* (radius * 0.05) + (height / 2));
		float x3 = (float) (Math.sin(2 * Math.PI / 360 * (pointerAngle - 90))
				* (radius * 0.05) + (width / 2));
		float y3 = (float) (-Math.cos(2 * Math.PI / 360 * (pointerAngle - 90))
				* (radius * 0.05) + (height / 2));
		float x4 = (float) (Math.sin(2 * Math.PI / 360 * (pointerAngle + 180))
				* (radius * 0.2) + (width / 2));
		float y4 = (float) (-Math.cos(2 * Math.PI / 360 * (pointerAngle + 180))
				* (radius * 0.2) + (height / 2));

		Path path = new Path();
		path.moveTo(x1, y1);
		path.lineTo(x2, y2);
		path.lineTo(x4, y4);
		path.lineTo(x3, y3);
		path.close();
		return path;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/**
		 * ��ȡ����
		 */
		float x = event.getX() - (width / 2);
		float y = -(event.getY() - (height / 2));
		double r = Math.sqrt((x * x) + (y * y));
		double pa = Math.asin(x / r) / (2 * Math.PI) * 360;
		/**
		 * ��������
		 */
		if (y < 0) {
			if (x < 0) {
				pa = -pa - 180;
			} else {
				pa = 180 - pa;
			}
		}
		if (pa < 0) {
			pa += 360;
		}
		onTouch(pa, r);
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			switch (type) {
			case TYPE_HOURS:
				if (hoursItems[selected] < 0) {
					selected = realSelected;
					pointerAngle = realPointerAngle;
				} else {
					realSelected = selected;
					realPointerAngle = pointerAngle;
				}
				break;
			case TYPE_MINUTES:
				if (minutesItems[selected] < 0) {
					selected = realSelected;
					pointerAngle = realPointerAngle;
				} else {
					realSelected = selected;
					realPointerAngle = pointerAngle;
				}
				break;
			}
			if(lis!=null){
				lis.onClockChange(selected);
			}
			break;
		}
		invalidate();
		return true;
	};

	private void onTouch(double pa, double r) {
		if (type == TYPE_HOURS) {
			int i = (int) (pa / 30);
			if (pa % 30 > 15) {
				i++;
				if (i > 11) {
					i = 0;
				}
			}
			if (r > radius * 0.65) {
				selected = i;
			} else {
				selected = i + 12;
			}
			pa = i * 30;
		} else {
			int i = (int) (pa / 6);
			if (pa % 6 > 3) {
				i++;
				if (i > 59) {
					i = 0;
				}
			}
			selected = i;
			pa = i * 6;
		}
		pointerAngle = pa;
	}
}
