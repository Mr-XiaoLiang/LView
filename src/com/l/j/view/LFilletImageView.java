package com.l.j.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class LFilletImageView extends ImageView {
	/**
	 * ͼƬ��
	 */
	private Paint imagePaint;
	/**
	 * ��ť�Ļ���
	 */
	private Paint btnPaint;
	/**
	 * ���ֱ�
	 */
	private Paint textPaint;
	/**
	 * д������
	 */
	private String text = "";
	/**
	 * ���
	 */
	private int width;
	/**
	 * �߶�
	 */
	private int height;
	/**
	 * Բ�ǰ뾶
	 */
	private float radius = 30;
	/**
	 * �Ƿ���
	 */
	private boolean isDown = false;

	public LFilletImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		imagePaint = new Paint();
		imagePaint.setAntiAlias(true);
		imagePaint.setFilterBitmap(true);
		imagePaint.setDither(true);
		btnPaint = new Paint();
		btnPaint.setAntiAlias(true);
		btnPaint.setColor(Color.WHITE);
		btnPaint.setAlpha(100);
		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setColor(Color.WHITE);
	}

	public LFilletImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LFilletImageView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		width = getWidth();
		height = getHeight();
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
		this.measure(0, 0);
		if (drawable.getClass() == NinePatchDrawable.class)
			return;
		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
		RectF rectf = new RectF(0, 0, width, height);
		Bitmap bitmap = createBitmap(b.copy(Bitmap.Config.ARGB_8888, true));
		canvas.drawBitmap(bitmap, 0, 0, null);
		drawText(canvas);
		if (isDown) {
			canvas.drawRoundRect(rectf, radius, radius, btnPaint);
		}
	}

	/**
	 * ����һ�����ϵ�bitmap
	 * 
	 * @param bitmap
	 * @return
	 */
	private Bitmap createBitmap(Bitmap bitmap) {
		Bitmap outPut = null;
		Bitmap bm = bitmap;
		try {
			// ��ֱ����
			if (bm.getHeight() < height) {
				float f = 1.0f * bm.getHeight() / height;
				bm = Bitmap.createScaledBitmap(bm, (int) (bm.getWidth() / f),
						height, true);
			}
			// ˮƽ����
			if (bm.getWidth() < width) {
				float f = 1.0f * bm.getWidth() / width;
				bm = Bitmap.createScaledBitmap(bm, width,
						(int) (bm.getHeight() / f), true);
			}
			outPut = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
					Config.ARGB_8888);
			Canvas canvas = new Canvas(outPut);
			Rect rect = new Rect(width/2-bm.getWidth()/2, height/2-bm.getHeight()/2, width/2+bm.getWidth()/2, height/2+bm.getHeight()/2);
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

	/**
	 * ������
	 * 
	 * @param canvas
	 */
	private void drawText(Canvas canvas) {
		float textSize = height / 3;// ���������ø߶ȵ�����֮һ
		// ������ڵ����ֳ��ȳ����˿�ȵİ˷�֮��ʮ,��ô�������ڵĳ��ȵ��ڰٷ�֮��ʮ,�ó������С
		if ((textSize * text.length()) > (width * 0.8)) {
			textSize = width * 0.8f / text.length();
		}
		textPaint.setTextSize(textSize);
		// ��������ĸ߶�ƫ����
		FontMetrics fm = textPaint.getFontMetrics();
		float textY = height / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
		canvas.drawText(text, width / 2, textY, textPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isDown = true;
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			isDown = false;
			invalidate();
			break;
		default:
			break;
		}
		return true;
	}
}
