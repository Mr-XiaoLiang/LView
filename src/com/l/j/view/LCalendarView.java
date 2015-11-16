package com.l.j.view;

import java.util.Calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.mytest.R;

public class LCalendarView extends View {
	/**
	 * ���
	 */
	private int year;
	/**
	 * �·�
	 */
	private int month;
	/**
	 * ��������
	 */
	private int[] days;
	/**
	 * ����
	 */
	private int today = 0;
	/**
	 * ѡ������
	 */
	private int selectedDay = 0;
	/**
	 * �����С
	 */
	private float textSize;
	/**
	 * �������ĵ�
	 */
	private float textY;
	/**
	 * ѡ����ɫ
	 */
	private int selectColor;
	/**
	 * ������ɫ
	 */
	private int textColor;
	/**
	 * ������ɫ
	 */
	private int weekColor;
	/**
	 * ����ɫ
	 */
	private int backgroundColor;
	/**
	 * ������ɫ
	 */
	private int todayColor;;
	/**
	 * ���ֵĻ���
	 */
	private Paint textPaint;
	/**
	 * ��Ļ���
	 */
	private Paint pointPaint;
	/**
	 * ���컭��
	 */
	private Paint todayPaint;
	/**
	 * ������
	 */
	private Paint backgroundPaint;

	/**
	 * ����
	 */
	private String[] weeks = { "��", "һ", "��", "��", "��", "��", "��" };
	/**
	 * ��Ԫ��Ŀ��
	 */
	int width = 0;
	/**
	 * ��Ԫ��ĸ߶�
	 */
	int height = 0;
	/**
	 * ����ѡ�������
	 */
	private CalendarViewListener calendarViewListener;

	public interface CalendarViewListener {
		public void calendarSelected(int d);
	}

	public LCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		days = new int[42];
		year = 2015;
		month = 9;
		today = 0;
		textSize = 0;
		textY = 0;
		selectColor = Color.parseColor("#99c9f2");
		textColor = Color.BLACK;
		weekColor = Color.GRAY;
		backgroundColor = getResources().getColor(android.R.color.transparent);
		todayColor = Color.parseColor("#50000000");
		/**
		 * ���������������Զ�����ʽ����
		 */
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.calendarview, defStyleAttr, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.calendarview_background_color:
				backgroundColor = a.getColor(attr, Color.parseColor("#ebebeb"));
				break;
			case R.styleable.calendarview_month:
				month = a.getInt(attr, 0);
				break;
			case R.styleable.calendarview_selected:
				selectedDay = a.getInt(attr, 0);
				break;
			case R.styleable.calendarview_selected_color:
				selectColor = a.getColor(attr, Color.parseColor("#99c9f2"));
				break;
			case R.styleable.calendarview_today:
				today = a.getInt(attr, 0);
				break;
			case R.styleable.calendarview_text_color:
				textColor = a.getColor(attr, Color.BLACK);
				break;
			case R.styleable.calendarview_today_color:
				todayColor = a.getColor(attr, Color.parseColor("#50000000"));
				break;
			case R.styleable.calendarview_weeks_color:
				weekColor = a.getColor(attr, Color.GRAY);
				break;
			case R.styleable.calendarview_year:
				year = a.getColor(attr, 0);
				break;
			}
		}
		a.recycle();

		init();
	}

	public LCalendarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LCalendarView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

		width = getWidth() / 7;
		height = getHeight() / 7;
		if (textSize == 0) {
			textSize = height * 0.5f;
			textPaint.setTextSize(textSize);
		}

		FontMetrics fm = textPaint.getFontMetrics();
		textY = height / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
		textPaint.setColor(weekColor);
		for (int j = 0; j < 7; j++) {
			canvas.drawText(weeks[j], (width * j) + width / 2, textY, textPaint);
		}
		textPaint.setColor(textColor);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (days[i * 7 + j] == 0) {
					continue;
				}
				if (today != 0 && days[i * 7 + j] == today) {
					canvas.drawCircle((width * j) + width / 2,
							(height * (i + 1)) + height / 2, textSize * 1f,
							todayPaint);
				}
				if (selectedDay != 0 && days[i * 7 + j] == selectedDay) {
					canvas.drawCircle((width * j) + width / 2,
							(height * (i + 1)) + height / 2, textSize * 1f,
							pointPaint);
				}
				canvas.drawText(days[i * 7 + j] + "", (width * j) + width / 2,
						(height * (i + 1)) + textY, textPaint);
			}
		}

		super.onDraw(canvas);
	}

	private void init() {
		Calendar calendar = Calendar.getInstance();
		if (year == 0) {
			year = calendar.get(Calendar.YEAR);
		}
		if (month == 0) {
			month = calendar.get(Calendar.MONTH);
		}
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		int start = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int end = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int day = 1;
		for (int i = 0; i < days.length; i++) {
			if (i < start || day > end) {
				days[i] = 0;
			} else {
				days[i] = day;
				day++;
			}
		}
		textPaint = new Paint();
		textPaint.setTextSize(textSize);
		textPaint.setAntiAlias(true);
		textPaint.setTextAlign(Align.CENTER);
		pointPaint = new Paint();
		pointPaint.setColor(selectColor);
		pointPaint.setAntiAlias(true);
		todayPaint = new Paint();
		todayPaint.setColor(todayColor);
		todayPaint.setAntiAlias(true);
		backgroundPaint = new Paint();
		backgroundPaint.setColor(backgroundColor);
		backgroundPaint.setAntiAlias(true);
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = 0;
		int y = 0;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = (int) event.getX() / width;
			y = (int) event.getY() / height;
			/**
			 * �ж��ǲ��������ǵ���������
			 */
			if (y >= 1 && x >= 0 && y < 7 && x < 7
					&& days[((y - 1) * 7) + x] != 0) {
				selectedDay = days[((y - 1) * 7) + x];
			}
			break;
		}
		if (calendarViewListener != null) {
			calendarViewListener.calendarSelected(selectedDay);
		}
		invalidate();
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = (int) (textSize * 11) + getPaddingLeft()
					+ getPaddingRight();
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = (int) (textSize * 11) + getPaddingTop()
					+ getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	/**
	 * ��ȡ���
	 * 
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * �������
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
		init();
	}

	/**
	 * ��ȡ�·�
	 * 
	 * @return
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * �����·�
	 * 
	 * @param month
	 */
	public void setMonth(int month) {
		this.month = month;
		init();
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	public int getToday() {
		return today;
	}

	/**
	 * ���ý���
	 * 
	 * @param today
	 */
	public void setToday(int today) {
		this.today = today;
		init();
	}

	/**
	 * ��ȡѡ������
	 * 
	 * @return
	 */
	public int getSelectedDay() {
		return selectedDay;
	}

	/**
	 * ����ѡ������
	 * 
	 * @param selectedDay
	 */
	public void setSelectedDay(int selectedDay) {
		this.selectedDay = selectedDay;
		init();
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
	 * ���������С
	 * 
	 * @param textSize
	 */
	public void setTextSize(float textSize) {
		this.textSize = textSize;
		init();
	}

	/**
	 * ��ȡѡ�б���ɫ
	 * 
	 * @return
	 */
	public int getSelectColor() {
		return selectColor;
	}

	/**
	 * ����ѡ�б���ɫ
	 * 
	 * @param selectColor
	 */
	public void setSelectColor(int selectColor) {
		this.selectColor = selectColor;
		init();
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
	 */
	public void setTextColor(int textColor) {
		this.textColor = textColor;
		init();
	}

	/**
	 * ��ȡ������ɫ
	 * 
	 * @return
	 */
	public int getWeekColor() {
		return weekColor;
	}

	/**
	 * ����������ɫ
	 * 
	 * @param weekColor
	 */
	public void setWeekColor(int weekColor) {
		this.weekColor = weekColor;
		init();
	}

	/**
	 * \ ��ȡ����ɫ
	 * 
	 * @return
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * ���ñ�����ɫ
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
		init();
	}

	/**
	 * ��ȡ���챳��ɫ
	 * 
	 * @return
	 */
	public int getTodayColor() {
		return todayColor;
	}

	/**
	 * ���ý��챳��ɫ
	 * 
	 * @param todayColor
	 */
	public void setTodayColor(int todayColor) {
		this.todayColor = todayColor;
		init();
	}

	/**
	 * ��ȡ����ѡ�������
	 * 
	 * @return
	 */
	public CalendarViewListener getCalendarViewListener() {
		return calendarViewListener;
	}

	/**
	 * ��������ѡ�������
	 * 
	 * @param calendarViewListener
	 */
	public void setCalendarViewListener(
			CalendarViewListener calendarViewListener) {
		this.calendarViewListener = calendarViewListener;
	}

	/**
	 * ��������
	 * 
	 * @param year
	 * @param month
	 * @param today
	 * @param selected
	 */
	public void setData(int year, int month, int today, int selected) {
		this.year = year;
		this.month = month;
		this.today = today;
		this.selectedDay = selected;
		init();
	}
	/**
	 * ���ÿ�ѡ��
	 * �����ѡ��ķ�Χ
	 * @param min
	 * @param max
	 */
	public void setItems(int min, int max) {
		if(max<min){
			max = min;
		}
		int[] items = new int[days.length];
		for(int i = 0;i<items.length;i++){
			items[i] = 0;
		}
		for(int i = min;i<=max;i++){
			B:for(int j = 0;j<days.length;j++){
				if(days[j] == i){
					items[j] = i;
					break B;
				}
			}
		}
		days = items;
		invalidate();
	}
	/**
	 * ���ÿ�ѡ��
	 * @param items �����ѡ�������
	 */
	public void setItems(int[] item) {
		int[] items = new int[days.length];
		for(int i = 0;i<items.length;i++){
			items[i] = 0;
		}
		for(int i = 0;i<item.length;i++){
			B:for(int j = 0;j<days.length;j++){
				if(days[j] == item[i]){
					items[j] = item[i];
					break B;
				}
			}
		}
		days = items;
		invalidate();
	}
}
