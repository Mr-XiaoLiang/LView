package com.l.j.view;

import java.util.Calendar;

import com.l.j.R;

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

public class LCalendarView extends View {
	/**
	 * 年份
	 */
	private int year;
	/**
	 * 月份
	 */
	private int month;
	/**
	 * 日期数组
	 */
	private int[] days;
	/**
	 * 今天
	 */
	private int today = 0;
	/**
	 * 选中日期
	 */
	private int selectedDay = 0;
	/**
	 * 字体大小
	 */
	private float textSize;
	/**
	 * 字体中心点
	 */
	private float textY;
	/**
	 * 选中颜色
	 */
	private int selectColor;
	/**
	 * 数字颜色
	 */
	private int textColor;
	/**
	 * 星期颜色
	 */
	private int weekColor;
	/**
	 * 背景色
	 */
	private int backgroundColor;
	/**
	 * 今天颜色
	 */
	private int todayColor;;
	/**
	 * 文字的画笔
	 */
	private Paint textPaint;
	/**
	 * 点的画笔
	 */
	private Paint pointPaint;
	/**
	 * 今天画笔
	 */
	private Paint todayPaint;
	/**
	 * 背景笔
	 */
	private Paint backgroundPaint;

	/**
	 * 星期
	 */
	private String[] weeks = { "日", "一", "二", "三", "四", "五", "六" };
	/**
	 * 单元格的宽度
	 */
	int width = 0;
	/**
	 * 单元格的高度
	 */
	int height = 0;
	/**
	 * 日期选择监听器
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
		 * 获得我们所定义的自定义样式属性
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
				 * 判断是不是在我们的日历里面
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
	 * 获取年份
	 *
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 设置年份
	 *
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
		init();
	}

	/**
	 * 获取月份
	 *
	 * @return
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * 设置月份
	 *
	 * @param month
	 */
	public void setMonth(int month) {
		this.month = month;
		init();
	}

	/**
	 * 获取今天
	 *
	 * @return
	 */
	public int getToday() {
		return today;
	}

	/**
	 * 设置今天
	 *
	 * @param today
	 */
	public void setToday(int today) {
		this.today = today;
		init();
	}

	/**
	 * 获取选中日期
	 *
	 * @return
	 */
	public int getSelectedDay() {
		return selectedDay;
	}

	/**
	 * 设置选中日期
	 *
	 * @param selectedDay
	 */
	public void setSelectedDay(int selectedDay) {
		this.selectedDay = selectedDay;
		init();
	}

	/**
	 * 获取字体大小
	 *
	 * @return
	 */
	public float getTextSize() {
		return textSize;
	}

	/**
	 * 设置字体大小
	 *
	 * @param textSize
	 */
	public void setTextSize(float textSize) {
		this.textSize = textSize;
		init();
	}

	/**
	 * 获取选中背景色
	 *
	 * @return
	 */
	public int getSelectColor() {
		return selectColor;
	}

	/**
	 * 设置选中背景色
	 *
	 * @param selectColor
	 */
	public void setSelectColor(int selectColor) {
		this.selectColor = selectColor;
		init();
	}

	/**
	 * 获取字体颜色
	 *
	 * @return
	 */
	public int getTextColor() {
		return textColor;
	}

	/**
	 * 设置字体颜色
	 *
	 * @param textColor
	 */
	public void setTextColor(int textColor) {
		this.textColor = textColor;
		init();
	}

	/**
	 * 获取星期颜色
	 *
	 * @return
	 */
	public int getWeekColor() {
		return weekColor;
	}

	/**
	 * 设置星期颜色
	 *
	 * @param weekColor
	 */
	public void setWeekColor(int weekColor) {
		this.weekColor = weekColor;
		init();
	}

	/**
	 * \ 获取背景色
	 *
	 * @return
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * 设置背景颜色
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
		init();
	}

	/**
	 * 获取今天背景色
	 *
	 * @return
	 */
	public int getTodayColor() {
		return todayColor;
	}

	/**
	 * 设置今天背景色
	 *
	 * @param todayColor
	 */
	public void setTodayColor(int todayColor) {
		this.todayColor = todayColor;
		init();
	}

	/**
	 * 获取日历选择监听器
	 *
	 * @return
	 */
	public CalendarViewListener getCalendarViewListener() {
		return calendarViewListener;
	}

	/**
	 * 设置日历选择监听器
	 *
	 * @param calendarViewListener
	 */
	public void setCalendarViewListener(
			CalendarViewListener calendarViewListener) {
		this.calendarViewListener = calendarViewListener;
	}

	/**
	 * 更新日期
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
	 * 设置可选项
	 * 传入可选项的范围
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
	 * 设置可选项
	 * @param items 传入可选项的数组
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
