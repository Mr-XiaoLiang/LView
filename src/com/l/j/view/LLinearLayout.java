package com.l.j.view;

import java.util.ArrayList;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 带波纹的LinearLayout
 * 
 * @author LiuJ
 *
 */
public class LLinearLayout extends LinearLayout {

	private Paint paint;
	private int color = getSolidColor();
	private ShowType showType = ShowType.BOTH;
	private int width = 0, height = 0;
	private int maxRadius = 0;
	private boolean isUp = true;
	private ArrayList<Point> points;
	private int step = 0;
	private int stepRatio = 5;
	private int downX,DownY;
	private boolean onlyOnClick = false;

	@SuppressLint("NewApi")
	public LLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		paint = new Paint();
		paint.setAlpha(20);
		paint.setAntiAlias(true);
		points = new ArrayList<LLinearLayout.Point>();
	}

	@SuppressLint("NewApi")
	public LLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public LLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LLinearLayout(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (Point p : points) {
//			setColor2(i);
//			canvas.drawCircle(width / 2, height / 2, i + (maxRadiu * imgRatio), paint);
		}
	}

//	private void setColor(int r) {
//		int alpha = (int) ((maxRadiu * (1 - imgRatio) - r) * 1.0 / maxRadiu * 255);
//		if (alpha > 255)
//			alpha = 255;
//		if (alpha < 0)
//			alpha = 0;
//		paint.setAlpha(alpha);
//	}
	
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		width = getWidth();
		height = getHeight();
		maxRadius = Math.max(height, width) / 2;
		if (step == 0)
			step = maxRadius / 100 * stepRatio;
	}

	private void onChange() {
		int downSize = 0;
		int downEndSize = 0;
		Iterator<Point> it = points.iterator();
		while (it.hasNext()) {
			Point point = it.next();
			if(point.getT()==ShowType.UP){
				if(point.isEnd()){
					it.remove();
				}else{
					point.next(step);
				}
			}else if(point.getT()==ShowType.DOWN){
				if(isUp){
					it.remove();
					continue;
				}
				downSize++;
				if(point.isEnd()){
					downEndSize++;
				}else{
					point.next(step);
				}
			}
		}
		if(!isUp&&downSize<2&&downEndSize>0){
			points.add(new Point(downX, DownY, 1, ShowType.DOWN));
		}
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!onlyOnClick||hasOnClickListeners()) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (showType == ShowType.BOTH || showType == ShowType.DOWN) {
					downX = (int) getX();
					DownY = (int) getY();
					points.add(new Point(downX, DownY, 1, ShowType.DOWN));
				}
				break;
			case MotionEvent.ACTION_UP:
				points.add(new Point((int) getX(), (int) getY(), 1, ShowType.UP));
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	public enum ShowType {
		DOWN, UP, BOTH
	}

	private class Point {
		// X坐标
		private int x;
		// Y坐标
		private int y;
		// 半径
		private int r;
		// 当前类型
		private ShowType t;
		// 最大限度
		private int max = 0;

		public boolean isEnd() {
			if (r >= max) {
				return true;
			}
			return false;
		}

		public Point(int x, int y, int r, ShowType t) {
			super();
			this.x = x;
			this.y = y;
			this.r = r;
			this.t = t;
			switch (t) {
			case DOWN:
				max = getMax(x, y);
				break;
			case UP:
				max = maxRadius;
				break;
			}
		}

		public void next(int step) {
			r += step;
		}

		public int getR() {
			return r;
		}

		public void setR(int r) {
			this.r = r;
		}

		public ShowType getT() {
			return t;
		}

		public void setT(ShowType t) {
			this.t = t;
		}
		
	}

	private int getMax(float x, float y) {
		double xLeftTop = Math.abs(x);
		double yLeftTop = Math.abs(y);
		double rLeftTop = Math.abs(Math.sqrt(xLeftTop * xLeftTop + yLeftTop * yLeftTop));
		double xRightTop = Math.abs(x - width);
		double yRightTop = Math.abs(y);
		double rRightTop = Math.abs(Math.sqrt(xRightTop * xRightTop + yRightTop * yRightTop));
		double xLeftBottom = Math.abs(x);
		double yLeftBottom = Math.abs(y - height);
		double rLeftBottom = Math.abs(Math.sqrt(xLeftBottom * xLeftBottom + yLeftBottom * yLeftBottom));
		double xRightBottom = Math.abs(x - width);
		double yRightBottom = Math.abs(y - height);
		double rRightBottom = Math.abs(Math.sqrt(xRightBottom * xRightBottom + yRightBottom * yRightBottom));
		double left = Math.max(rLeftTop, rLeftBottom);
		double right = Math.max(rRightTop, rRightBottom);
		return (int) Math.max(left, right);
	}

	public int getShowColor() {
		return color;
	}

	public void setShowColor(int color) {
		this.color = color;
	}
}
