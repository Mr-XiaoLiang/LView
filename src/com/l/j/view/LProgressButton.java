package com.l.j.view;

import com.l.j.option.LProgressButtonOption;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LProgressButton extends View {
	/**
	 * ����
	 */
	private LProgressButtonOption option;
	/**
	 * ��ť���
	 */
	private int width;
	/**
	 * ��ť�߶�
	 */
	private int height;
	/**
	 * ��ʼʱ�ı�������
	 */
	private Paint startBgPaint;
	/**
	 * ��ʼʱ��ǰ������
	 */
	private Paint startPaint;
	/**
	 * ��������������
	 */
	private Paint progressBgPaint;
	/**
	 * ���������Ŀ�Ļ���
	 */
	private Paint progressCenterPaint;
	/**
	 * ������ǰ������
	 */
	private Paint progressPaint;
	/**
	 * ������ǰ�����ֻ���
	 */
	private Paint progressTextPaint;
	/**
	 * ������������
	 */
	private Paint endBgPaint;
	/**
	 * ����ǰ������
	 */
	private Paint endPaint;
	/**
	 * �쳣��������
	 */
	private Paint errorBgPaint;
	/**
	 * �쳣ǰ������
	 */
	private Paint errorPaint;
	/**
	 * ��ǰ״̬
	 */
	private int type = TYPE_NONE;
	/**
	 * ��һ��״̬
	 */
	private int oldType = type;
	/**
	 * δ��ʼ��
	 */
	public final static int TYPE_NONE = -1;
	/**
	 * ��ʼ״̬
	 */
	public final static int TYPE_START = 0;
	/**
	 * ׼��������
	 */
	public final static int TYPE_PREPARE = 4;
	/**
	 * ������
	 */
	public final static int TYPE_LOADING = 1;
	/**
	 * ����
	 */
	public final static int TYPE_END = 2;
	/**
	 * �쳣
	 */
	public final static int TYPE_ERROR = 3;
	/**
	 * �ܽ���
	 */
	private float allNum = 0;
	/**
	 * �Ѽ���
	 */
	private float proNum = 0;
	/**
	 * ��ʾ�����н���
	 */
	private float proIndex = 0;
	/**
	 * ����ص��¼�
	 */
	private LProgressButtonOnClickListener clickListener;
	/**
	 * ������֡��
	 */
	private int index = 0;
	/**
	 * ���֡��
	 */
	private int maxIndex = 30;
	/**
	 * ����״̬���±�
	 */
	private float prepareIndex = 0;
	/**
	 * �뾶
	 */
	private int radius = -1;
	/**
	 * ����״̬
	 */
	private boolean drawType = true;
	/**
	 * ȷ�������Ƿ����
	 */
	private boolean isEnd = false;

	public LProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public LProgressButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LProgressButton(Context context) {
		super(context);
	}

	public LProgressButton(Context context, LProgressButtonOption option) {
		super(context);
		this.option = option;
		init();
	}

	/**
	 * ������ʼ��
	 */
	private void init() {
		if (option == null) {
			throw new RuntimeException("LProgressButtonOption is null");
		}
		startBgPaint = new Paint();
		startBgPaint.setAntiAlias(true);
		startBgPaint.setColor(option.getBtnStartBgColor());
		startPaint = new Paint();
		startPaint.setAntiAlias(true);
		startPaint.setColor(option.getBtnStartTextColor());
		startPaint.setTextAlign(Align.CENTER);
		progressBgPaint = new Paint();
		progressBgPaint.setAntiAlias(true);
		progressBgPaint.setColor(option.getBtnProgressBgColor());
		progressBgPaint.setStyle(Paint.Style.STROKE);
		progressCenterPaint = new Paint();
		progressCenterPaint.setAntiAlias(true);
		progressCenterPaint.setColor(option.getBtnProgressCenterColor());
		progressCenterPaint.setStyle(Paint.Style.FILL);
		progressPaint = new Paint();
		progressPaint.setAntiAlias(true);
		progressPaint.setColor(option.getBtnProgressColor());
		progressPaint.setStyle(Paint.Style.STROKE);
		progressTextPaint = new Paint();
		progressTextPaint.setAntiAlias(true);
		progressTextPaint.setColor(option.getBtnProgressPercentColor());
		progressTextPaint.setTextAlign(Align.CENTER);
		endBgPaint = new Paint();
		endBgPaint.setAntiAlias(true);
		endBgPaint.setColor(option.getBtnEndBgColor());
		endPaint = new Paint();
		endPaint.setAntiAlias(true);
		endPaint.setColor(option.getBtnEndTextColor());
		endPaint.setTextAlign(Align.CENTER);
		errorBgPaint = new Paint();
		errorBgPaint.setAntiAlias(true);
		errorBgPaint.setColor(option.getBtnErrorBgColor());
		errorPaint = new Paint();
		errorPaint.setAntiAlias(true);
		errorPaint.setColor(option.getBtnErrorTextColor());
		errorPaint.setTextAlign(Align.CENTER);
		type = TYPE_START;
		allNum = 0;
		proNum = 0;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		width = getWidth();
		height = getHeight();
		if (type == TYPE_NONE) {// û�г�ʼ���İ�ť����
			return;
		}
		drawType = true;
		
		drawType(oldType, canvas);
		if (drawType)
			drawType(type, canvas);
	}

	/**
	 * ���ڶ����νӵ��жϷ���
	 * 
	 * @param t
	 * @param canvas
	 */
	private void drawType(int t, Canvas canvas) {
		switch (t) {
		case TYPE_END:
			// ����������
			drawEndBackgroung(canvas);
			break;
		case TYPE_ERROR:
			// ���쳣����
			drawErrorBackgroung(canvas);
			break;
		case TYPE_LOADING:
			// �����ؽ���
			drawProgress(canvas);
			break;
		case TYPE_PREPARE:
			// ���ȴ�����
			drawProgressPrepare(canvas);
			break;
		case TYPE_START:
			// ����ʼ����
			drawStartBackgroung(canvas);
			break;
		}
	}

	/**
	 * ���ƿ�ʼ����
	 * 
	 * @param canvas
	 */
	private void drawStartBackgroung(Canvas canvas) {
		if (type != TYPE_START && oldType != TYPE_START) {
			return;// ������Լ�����ؾͷ���
		}
		drawType = false;
		if (type == TYPE_START && oldType != TYPE_START) {// ���뵱ǰ״̬
			createStart(canvas, TYPE_START);
			if (index > maxIndex) {
				index = maxIndex;
				oldType = type;
			}
			index++;
		} else if (type != TYPE_START && oldType == TYPE_START) {// �˳���ǰ״̬
			if (!isEnd) {
				index--;
				createStart(canvas, TYPE_START);
				if (index < 0) {
					isEnd = true;
					index = 0;
				}
			} else {
				drawType = true;
			}
		} else {
			createBtn(canvas, TYPE_START);
			return;
		}
		invalidate();
	}

	/**
	 * ���ƽ�������
	 * 
	 * @param canvas
	 */
	private void drawEndBackgroung(Canvas canvas) {
		if (type != TYPE_END && oldType != TYPE_END) {
			return;// ������Լ�����ؾͷ���
		}
		drawType = false;
		if (type == TYPE_END && oldType != TYPE_END) {// ���뵱ǰ״̬
			createStart(canvas, TYPE_END);
			if (index > maxIndex) {
				index = maxIndex;
				oldType = type;
			}
			index++;
		} else if (type != TYPE_END && oldType == TYPE_END) {// �˳���ǰ״̬
			if (!isEnd) {
				index--;
				createStart(canvas, TYPE_END);
				if (index < 0) {
					isEnd = true;
					index = 0;
				}
			} else {
				drawType = true;
			}
		} else {
			createBtn(canvas, TYPE_END);
			return;
		}
		invalidate();
	}

	/**
	 * �����쳣����
	 * 
	 * @param canvas
	 */
	private void drawErrorBackgroung(Canvas canvas) {
		if (type != TYPE_ERROR && oldType != TYPE_ERROR) {
			return;// ������Լ�����ؾͷ���
		}
		drawType = false;
		if (type == TYPE_ERROR && oldType != TYPE_ERROR) {// ���뵱ǰ״̬
			createStart(canvas, TYPE_ERROR);
			if (index > maxIndex) {
				index = maxIndex;
				oldType = type;
			}
			index++;
		} else if (type != TYPE_ERROR && oldType == TYPE_ERROR) {// �˳���ǰ״̬
			if (!isEnd) {
				index--;
				createStart(canvas, TYPE_ERROR);
				if (index < 0) {
					isEnd = true;
					index = 0;
				}
			} else {
				drawType = true;
			}
		} else {
			createBtn(canvas, TYPE_ERROR);
			return;
		}
		invalidate();
	}

	/**
	 * ���ƽ��Ƚ���
	 * 
	 * @param canvas
	 */
	private void drawProgress(Canvas canvas) {
		if (type != TYPE_LOADING & oldType != TYPE_LOADING) {
			return;// ������Լ�����ؾͷ���
		}
		drawType = false;
		// ������ɫ
		if (!option.isProgressCenterLucency()) {
			canvas.drawCircle(width / 2, height / 2, radius, progressCenterPaint);
		}
		// ������ͼ
		if (!option.isProgressCenterLucency() && option.getBtnProgressCenterImage() > 0) {
			canvas.drawBitmap(
					createBitmap(BitmapFactory.decodeResource(getResources(), option.getBtnProgressCenterImage()),
							radius * 2, radius * 2, radius),
					width / 2 - radius, height / 2 - radius, null);
		}
		progressPaint.setStrokeWidth(option.getBtnProgressWidth() * radius);
		progressBgPaint.setStrokeWidth(option.getBtnProgressWidth() * radius);
		canvas.drawCircle(width / 2, height / 2, radius, progressBgPaint);
		RectF rectF = new RectF(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);
		if (type == TYPE_LOADING && oldType != TYPE_LOADING && oldType != TYPE_PREPARE) {
			int r = 0;
			if (width > height) {
				r = (int) (height * 0.4);
			} else {
				r = (int) (width * 0.4);
			}
			if (radius < r) {
				radius += (r * 0.1);
			} else {
				radius = r;
				oldType = type;
			}
			// invalidate();
		} else if (type == TYPE_LOADING && (oldType == TYPE_LOADING || oldType == TYPE_PREPARE)) {
			oldType = type;
			isEnd = false;
			if (allNum <= 0 || proNum <= 0) {
				return;
			} else {
//				if(Math.abs(proNum - proIndex) < allNum / 100){
//					proIndex = proNum;
//				}
				canvas.drawArc(rectF, -90, 360 * proIndex / allNum, false, progressPaint);
				String text = ((int)(proIndex / allNum * 100))+"%";
				// ����
				if (text != null && text.length() > 0&&option.isShowPercent()) {
					int textSize;
					textSize = (int) (radius * 2 * 0.3);
					if (textSize * text.length() > (radius * 2)) {
						textSize = (int) ((radius * 2) / text.length() * 0.8);
					}
					progressTextPaint.setTextSize(textSize);
					FontMetrics fm = progressTextPaint.getFontMetrics();
					float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
					canvas.drawText(text, width / 2, height / 2 + textY, progressTextPaint);
				}
				if (proIndex < proNum) {
					proIndex += allNum * 0.01;
					if (Math.abs(proNum - proIndex) < allNum * 0.01) {
						proIndex = proNum;
					}
				} else if (proIndex > proNum) {
					proIndex -= proNum * 0.01;
					if (Math.abs(proNum - proIndex) < allNum * 0.01) {
						proIndex = proNum;
					}
				} else {
					return;
				}
			}
		} else if (oldType == TYPE_LOADING) {
			canvas.drawArc(rectF, -90, 360 * proIndex / allNum, false, progressPaint);
			if(!isEnd){
				int r = 0;
				if (width > height) {
					r = (int) (height * 0.4);
				} else {
					r = (int) (width * 0.4);
				}
				if (radius > 0) {
					radius -= (r * 0.1);
				} else {
					radius = 0;
					index = 0;
					isEnd = true;
				}
			}else{
				drawType = true;
			}
			// invalidate();
		}
		invalidate();
	}

	/**
	 * �����ȵȴ�����
	 * 
	 * @param canvas
	 */
	private void drawProgressPrepare(Canvas canvas) {
		if (type != TYPE_PREPARE && oldType != TYPE_PREPARE) {
			return;// ������Լ�����ؾͷ���
		}
		drawType = false;
		// ������ɫ
		if (!option.isProgressCenterLucency()) {
			canvas.drawCircle(width / 2, height / 2, radius, progressCenterPaint);
		}
		// ������ͼ
		if (!option.isProgressCenterLucency() && option.getBtnProgressCenterImage() > 0) {
			canvas.drawBitmap(
					createBitmap(BitmapFactory.decodeResource(getResources(), option.getBtnProgressCenterImage()),
							radius * 2, radius * 2, radius),
					width / 2 - radius, height / 2 - radius, null);
		}
		progressPaint.setStrokeWidth(option.getBtnProgressWidth() * radius);
		progressBgPaint.setStrokeWidth(option.getBtnProgressWidth() * radius);
		canvas.drawCircle(width / 2, height / 2, radius, progressBgPaint);
		RectF rectF = new RectF(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);
		if (type == TYPE_PREPARE && oldType != TYPE_PREPARE) {
			int r = 0;
			if (width > height) {
				r = (int) (height * 0.4);
			} else {
				r = (int) (width * 0.4);
			}
			if (radius < r) {
				radius += (r * 0.1);
			} else {
				radius = r;
				oldType = type;
			}
			// invalidate();
		} else if (type == TYPE_PREPARE && oldType == TYPE_PREPARE) {
			if (index < maxIndex) {
				canvas.drawArc(rectF, -90, 360 * index * 0.01f, false, progressPaint);
				index++;
				// invalidate();
			} else {// prepareIndex
				canvas.drawArc(rectF, prepareIndex - 90, 360 * index * 0.01f, false, progressPaint);
				prepareIndex += 5;
				if (prepareIndex > 360) {
					prepareIndex = 0;
				}
				// invalidate();
			}
			isEnd = false;
		} else if (type == TYPE_LOADING && oldType == TYPE_PREPARE) {
			if (!isEnd && index > 0) {
				canvas.drawArc(rectF, prepareIndex - 90, 360 * index * 0.01f, false, progressPaint);
				if (prepareIndex > 285) {
					prepareIndex += 360 * 0.01f;
					index--;
				} else {
					prepareIndex += 5;
					if (prepareIndex > 360) {
						prepareIndex = 0;
					}
				}
				// invalidate();
			}
			if (index <= 0) {
				isEnd = true;
				drawType = true;
				index = 0;
			}
		} else if (oldType == TYPE_PREPARE) {
			canvas.drawArc(rectF, prepareIndex - 90, 360 * index * 0.01f, false, progressPaint);
			prepareIndex += 5;
			if (prepareIndex > 360) {
				prepareIndex = 0;
			}
			int r = 0;
			if (width > height) {
				r = (int) (height * 0.4);
			} else {
				r = (int) (width * 0.4);
			}
			if (radius > 0) {
				radius -= (r * 0.1);
			} else {
				radius = 0;
				// oldType = type;
				index = 0;
			}
			// invalidate();
		}
		invalidate();
	}
	
	/**
	 * ��ť�����״̬
	 * 
	 * @param canvas
	 * @param type
	 */
	private void createBtn(Canvas canvas, int type) {
		Paint paint;
		Paint bgpaint;
		String text;
		int img;
		int icon;
		switch (type) {
		case TYPE_END:
			paint = endPaint;
			bgpaint = endBgPaint;
			text = option.getBtnEndText();
			img = option.getBtnEndBgImage();
			icon = option.getBtnEndIcon();
			break;
		case TYPE_ERROR:
			paint = errorPaint;
			bgpaint = errorBgPaint;
			text = option.getBtnErrorText();
			img = option.getBtnErrorBgImage();
			icon = option.getBtnErrorIcon();
			break;
		case TYPE_START:
			paint = startPaint;
			bgpaint = startBgPaint;
			text = option.getBtnStartText();
			img = option.getBtnStartBgImage();
			icon = option.getBtnStartIcon();
			break;
		default:
			return;
		}
		isEnd = false;
		this.type = oldType;
		switch (option.getBtnRadius()) {
		case LProgressButtonOption.Builder.btnRadius_HORIZONTAL:
			radius = height / 2;
			break;
		case LProgressButtonOption.Builder.btnRadius_SQUARE:
			radius = 0;
			break;
		case LProgressButtonOption.Builder.btnRadius_VERTICAL:
			radius = width / 2;
			break;
		default:
			if (option.getBtnRadius() > 0) {
				radius = option.getBtnRadius();
			} else {
				radius = 0;
			}
			break;
		}
		// ������ɫ
		RectF rect = new RectF(0, 0, width, height);
		canvas.drawRoundRect(rect, radius, radius, bgpaint);
		// ������ͼ
		if (img > 0) {
			canvas.drawBitmap(createBitmap(BitmapFactory.decodeResource(getResources(), img), height, width, radius),
					width, height, null);
		}
		//����ǰ��ͼƬ
		if(icon>0){
			int w = 0,h = 0;
			float l = 0;
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icon);
			if(bitmap.getWidth()!=0&&bitmap.getHeight()!=0){
				l = bitmap.getWidth()/bitmap.getHeight();
				switch (option.getBtnIconSizeType()) {
				case LProgressButtonOption.Builder.btnIconSizeType_AUTO:
					if(width/height>l){
						h = (int) (height*option.getBtnIconSize());
						w = (int) (h*l);
					}else{
						w = (int) (width*option.getBtnIconSize());
						h = (int) (w/l);
					}
					break;
				case LProgressButtonOption.Builder.btnIconSizeType_HEIGHT:
					h = (int) (height*option.getBtnIconSize());
					w = (int) (h*l);
					break;
				case LProgressButtonOption.Builder.btnIconSizeType_WIDTH:
					w = (int) (width*option.getBtnIconSize());
					h = (int) (w/l);
					break;
				}
				canvas.drawBitmap(createBitmap(bitmap,
						h, w, option.getBtnIconRadius()), width / 2 - w/2, height / 2 - h/2,
						null);
			}
			bitmap.recycle();
			bitmap = null; 
			System.gc();
		}
		// ����
		if (text != null && text.length() > 0) {
			int textSize;
			if (width > height) {
				textSize = (int) (height * 0.3);
				if (textSize * text.length() > width) {
					textSize = (int) (width / text.length() * 0.8);
				}
			} else {
				textSize = (int) (width * 0.3);
				if (textSize * text.length() > height) {
					textSize = (int) (height / text.length() * 0.8);
				}
			}
			startPaint.setTextSize(textSize);
			FontMetrics fm = startPaint.getFontMetrics();
			float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
			canvas.drawText(text, width / 2, height / 2 + textY, paint);
		}
	}

	/**
	 * ��ʼ�Ķ���
	 * 
	 * @param canvas
	 */
	private void createStart(Canvas canvas, int type) {
		Paint paint;
		Paint bgpaint;
		String text;
		int img;
		int icon;
		switch (type) {
		case TYPE_END:
			paint = endPaint;
			bgpaint = endBgPaint;
			text = option.getBtnEndText();
			img = option.getBtnEndBgImage();
			icon = option.getBtnEndIcon();
			break;
		case TYPE_ERROR:
			paint = errorPaint;
			bgpaint = errorBgPaint;
			text = option.getBtnErrorText();
			img = option.getBtnErrorBgImage();
			icon = option.getBtnErrorIcon();
			break;
		case TYPE_START:
			paint = startPaint;
			bgpaint = startBgPaint;
			text = option.getBtnStartText();
			img = option.getBtnStartBgImage();
			icon = option.getBtnStartIcon();
			break;
		default:
			return;
		}
		if (index < (maxIndex / 2)) {// �ӵ���Բ
			// ������ɫ
			canvas.drawCircle(width / 2, height / 2, radius, bgpaint);
			// ������ͼ
			if (img > 0) {
				canvas.drawBitmap(
						createBitmap(BitmapFactory.decodeResource(getResources(), img), radius * 2, radius * 2, radius),
						width / 2 - radius, height / 2 - radius, null);
			}
			int r = 0;
			if (width > height) {
				r = (int) (height * 0.5);
			} else {
				r = (int) (width * 0.5);
			}
			radius = r / maxIndex * 2 * index;
		} else {// ��Բ��ɰ�ť
			float wr = 0;
			float hr = 0;
			if (width > height) {
				radius = (int) (height * 0.5);
			} else {
				radius = (int) (width * 0.5);
			}
			if (option.getBtnRadius() > radius) {
				radius = option.getBtnRadius();
			}
			wr = (width / 2 - radius) / maxIndex * 2 * (index - maxIndex / 2);
			hr = (height / 2 - radius) / maxIndex * 2 * (index - maxIndex / 2);
			if (wr > (width / 2 - radius)) {
				wr = (width / 2 - radius);
			}
			if (hr > (height / 2 - radius)) {
				hr = (height / 2 - radius);
			}
			// ������ɫ
			RectF rect = new RectF(width / 2 - wr - radius, height / 2 - hr - radius, width / 2 + wr + radius,
					height / 2 + hr + radius);
			canvas.drawRoundRect(rect, radius, radius, bgpaint);
			// ������ͼ
			if (img > 0) {
				canvas.drawBitmap(createBitmap(BitmapFactory.decodeResource(getResources(), img),
						(int) hr * 2 + radius * 2, (int) wr * 2 + radius * 2, radius), width / 2 - wr, height / 2 - hr,
						null);
			}
			//����ǰ��ͼƬ
			if(icon>0){
				int w = 0,h = 0;
				float l = 0;
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icon);
				if(bitmap.getWidth()!=0&&bitmap.getHeight()!=0){
					l = bitmap.getWidth()/bitmap.getHeight();
					switch (option.getBtnIconSizeType()) {
					case LProgressButtonOption.Builder.btnIconSizeType_AUTO:
						if(wr/hr>l){
							h = (int) ((hr * 2 + radius * 2)*option.getBtnIconSize());
							w = (int) (h*l);
						}else{
							w = (int) ((wr * 2 + radius * 2)*option.getBtnIconSize());
							h = (int) (w/l);
						}
						break;
					case LProgressButtonOption.Builder.btnIconSizeType_HEIGHT:
						h = (int) ((hr * 2 + radius * 2)*option.getBtnIconSize());
						w = (int) (h*l);
						break;
					case LProgressButtonOption.Builder.btnIconSizeType_WIDTH:
						w = (int) ((wr * 2 + radius * 2)*option.getBtnIconSize());
						h = (int) (w/l);
						break;
					}
					canvas.drawBitmap(createBitmap(bitmap,
							h, w, option.getBtnIconRadius()), width / 2 - w/2, height / 2 - h/2,
							null);
				}
				bitmap.recycle();
				bitmap = null; 
				System.gc();
			}
			// ����
			if (text != null && text.length() > 0) {
				int textSize;
				if (wr > hr) {
					textSize = (int) ((hr * 2 + radius * 2) * 0.3);
					if (textSize * text.length() > (wr * 2 + radius * 2)) {
						textSize = (int) ((wr * 2 + radius * 2) / text.length() * 0.8);
					}
				} else {
					textSize = (int) ((wr * 2 + radius * 2) * 0.3);
					if (textSize * text.length() > (hr * 2 + radius * 2)) {
						textSize = (int) ((hr * 2 + radius * 2) / text.length() * 0.8);
					}
				}
				paint.setTextSize(textSize);
				FontMetrics fm = paint.getFontMetrics();
				float textY = -fm.descent + (fm.descent - fm.ascent) / 2;
				canvas.drawText(text, width / 2, height / 2 + textY, paint);
			}
		}
	}

	/**
	 * 
	 * ����һ�����ϵ�bitmap
	 * 
	 * @param bitmap
	 * @param height
	 * @param width
	 * @param radius
	 * @return
	 */
	private Bitmap createBitmap(Bitmap bitmap, int height, int width, float radius) {
		Paint imagePaint = new Paint();
		imagePaint.setAntiAlias(true);
		imagePaint.setFilterBitmap(true);
		imagePaint.setDither(true);
		Bitmap outPut = null;
		Bitmap bm = bitmap;
		try {
			// ��ֱ����
			if (bm.getHeight() < height) {
				float f = 1.0f * bm.getHeight() / height;
				bm = Bitmap.createScaledBitmap(bm, (int) (bm.getWidth() / f), height, true);
			}
			// ˮƽ����
			if (bm.getWidth() < width) {
				float f = 1.0f * bm.getWidth() / width;
				bm = Bitmap.createScaledBitmap(bm, width, (int) (bm.getHeight() / f), true);
			}
			outPut = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(outPut);
			Rect rect = new Rect(width / 2 - bm.getWidth() / 2, height / 2 - bm.getHeight() / 2,
					width / 2 + bm.getWidth() / 2, height / 2 + bm.getHeight() / 2);
			canvas.drawARGB(0, 0, 0, 0);
			RectF rectf = new RectF(0, 0, width, height);
			canvas.drawRoundRect(rectf, radius, radius, imagePaint);
			imagePaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bm, rect, rect, imagePaint);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return outPut;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (clickListener != null&&type==oldType) {
				clickListener.LPBOnClick(this);
			}
		}
		return true;
	}

	/**
	 * ���Ȱ�ť����¼�������
	 * 
	 * @author LiuJ
	 *
	 */
	public interface LProgressButtonOnClickListener {
		public void LPBOnClick(LProgressButton btn);
	}

	/**
	 * ��ȡ���в���
	 * 
	 * @return
	 */
	public LProgressButtonOption getOption() {
		return option;
	}

	/**
	 * ���ð�ť��ʽ����
	 * 
	 * @param option
	 */
	public void setOption(LProgressButtonOption option) {
		this.option = option;
		init();
	}

	/**
	 * ���¿�ʼ ����
	 */
	public void reset() {
		type = TYPE_START;
		invalidate();
	}

	/**
	 * ��ʼ����
	 * 
	 * @param allNum
	 * @param proNum
	 */
	public void onLoad(float allNum, float proNum) {
		oldType = type;
		type = TYPE_LOADING;
		this.allNum = allNum;
		this.proNum = proNum;
		invalidate();
	}

	/**
	 * �������ؽ���
	 * 
	 * @param proNum
	 */
	public void updateProgress(float proNum) {
		this.proNum = proNum;
		invalidate();
	}

	/**
	 * ׼��������
	 */
	public void onPrepare() {
		oldType = type;
		type = TYPE_PREPARE;
		invalidate();
	}

	/**
	 * ���ؽ���
	 */
	public void onEnd() {
		oldType = type;
		type = TYPE_END;
		invalidate();
	}

	/**
	 * ����
	 */
	public void onError() {
		oldType = type;
		type = TYPE_ERROR;
		invalidate();
	}

	/**
	 * ��ȡ��ǰ״̬
	 * 
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * ��ȡ��ť�ļ�����
	 * 
	 * @return
	 */
	public LProgressButtonOnClickListener getClickListener() {
		return clickListener;
	}

	/**
	 * ���ð�ť�ļ�����
	 * 
	 * @param clickListener
	 */
	public void setClickListener(LProgressButtonOnClickListener clickListener) {
		this.clickListener = clickListener;
	}
}
