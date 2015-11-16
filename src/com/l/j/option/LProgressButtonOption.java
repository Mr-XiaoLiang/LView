package com.l.j.option;

import android.graphics.Color;

/**
 * 
 * @author LiuJ ���ǽ�������ť�Ĳ���������
 * 
 */
public class LProgressButtonOption {
	// ��ťԲ�ǵĳߴ�
	private final int btnRadius;
	// ��ť��ʼ����ɫ
	private final int btnStartBgColor;
	// ��ť��ʼ����ͼƬ
	private final int btnStartBgImage;
	// ��ť��ʼ����
	private final String btnStartText;
	// ��ť���ǰ������ɫ
	private final int btnStartTextColor;
	// ��ť��ʼ��ͼƬ
	private final int btnStartIcon;
	// ��ť��������ɫ
	private final int btnEndBgColor;
	// ��ť��������ͼƬ
	private final int btnEndBgImage;
	// ��ť��������
	private final String btnEndText;
	// ��ť����������ɫ
	private final int btnEndTextColor;
	// ��ť������ͼƬ
	private final int btnEndIcon;
	// ��ť�쳣����ɫ
	private final int btnErrorBgColor;
	// ��ť�쳣����ͼƬ
	private final int btnErrorBgImage;
	// ��ť��������
	private final String btnErrorText;
	// ��ť����������ɫ
	private final int btnErrorTextColor;
	// ��ť������ͼƬ
	private final int btnErrorIcon;
	// ��������ɫ
	private final int btnProgressColor;
	// ���������(�밴ť�߶ȵı���)
	private final float btnProgressWidth;
	// ������������ɫ
	private final int btnProgressBgColor;
	// �Ƿ���ʾ�ٷֱ�
	private final boolean isShowPercent;
	// ���������ı����Ƿ�͸��
	private final boolean isProgressCenterLucency;
	// ���������ı�������ɫ
	private final int btnProgressCenterColor;
	// ��������ֵ����ɫ
	private final int btnProgressPercentColor;
	// ���������ı�����ͼƬ
	private final int btnProgressCenterImage;
	//��ťͼ��ĳߴ�
	private final float btnIconSize;
	//ͼ�갴ť�ı�������
	private final int btnIconSizeType;
	//��ťͼ���Բ�Ǵ�С
	private final int btnIconRadius;
	public LProgressButtonOption(Builder builder) {
		super();
		this.btnEndBgColor = builder.btnEndBgColor;
		this.btnEndBgImage = builder.btnEndBgImage;
		this.btnEndIcon = builder.btnEndIcon;
		this.btnEndText = builder.btnEndText;
		this.btnEndTextColor = builder.btnEndTextColor;
		this.btnErrorBgColor = builder.btnErrorBgColor;
		this.btnErrorBgImage = builder.btnErrorBgImage;
		this.btnErrorIcon = builder.btnErrorIcon;
		this.btnErrorText = builder.btnErrorText;
		this.btnErrorTextColor = builder.btnErrorTextColor;
		this.btnProgressBgColor = builder.btnProgressBgColor;
		this.btnProgressCenterColor = builder.btnProgressCenterColor;
		this.btnProgressColor = builder.btnProgressColor;
		this.btnProgressPercentColor = builder.btnProgressPercentColor;
		this.btnProgressWidth = builder.btnProgressWidth;
		this.btnRadius = builder.btnRadius;
		this.btnStartBgColor = builder.btnStartBgColor;
		this.btnStartBgImage = builder.btnStartBgImage;
		this.btnStartIcon = builder.btnStartIcon;
		this.btnStartText = builder.btnStartText;
		this.btnStartTextColor = builder.btnStartTextColor;
		this.isProgressCenterLucency = builder.isProgressCenterLucency;
		this.isShowPercent = builder.isShowPercent;
		this.btnProgressCenterImage = builder.btnProgressCenterImage;
		this.btnIconSize = builder.btnIconSize;
		this.btnIconSizeType = builder.btnIconSizeType;
		this.btnIconRadius = builder.btnIconRadius;
		
	}

	

	public int getBtnRadius() {
		return btnRadius;
	}



	public int getBtnStartBgColor() {
		return btnStartBgColor;
	}



	public int getBtnStartBgImage() {
		return btnStartBgImage;
	}



	public String getBtnStartText() {
		return btnStartText;
	}



	public int getBtnStartTextColor() {
		return btnStartTextColor;
	}



	public int getBtnStartIcon() {
		return btnStartIcon;
	}



	public int getBtnEndBgColor() {
		return btnEndBgColor;
	}



	public int getBtnEndBgImage() {
		return btnEndBgImage;
	}



	public String getBtnEndText() {
		return btnEndText;
	}



	public int getBtnEndTextColor() {
		return btnEndTextColor;
	}



	public int getBtnEndIcon() {
		return btnEndIcon;
	}



	public int getBtnErrorBgColor() {
		return btnErrorBgColor;
	}



	public int getBtnErrorBgImage() {
		return btnErrorBgImage;
	}



	public String getBtnErrorText() {
		return btnErrorText;
	}



	public int getBtnErrorTextColor() {
		return btnErrorTextColor;
	}



	public int getBtnErrorIcon() {
		return btnErrorIcon;
	}



	public int getBtnProgressColor() {
		return btnProgressColor;
	}



	public float getBtnProgressWidth() {
		return btnProgressWidth;
	}



	public int getBtnProgressBgColor() {
		return btnProgressBgColor;
	}



	public boolean isShowPercent() {
		return isShowPercent;
	}



	public boolean isProgressCenterLucency() {
		return isProgressCenterLucency;
	}



	public int getBtnProgressCenterColor() {
		return btnProgressCenterColor;
	}



	public int getBtnProgressPercentColor() {
		return btnProgressPercentColor;
	}



	public int getBtnProgressCenterImage() {
		return btnProgressCenterImage;
	}

	

	public float getBtnIconSize() {
		return btnIconSize;
	}


	public int getBtnIconSizeType() {
		return btnIconSizeType;
	}

	public int getBtnIconRadius() {
		return btnIconRadius;
	}



	public static class Builder {
		/**
		 * ��ťԲ�ǰ뾶�ı�ѡ�� ���°�Բ�İ�ť��ʽ
		 */
		public static final int btnRadius_VERTICAL = -3;
		/**
		 * ��ťԲ�ǰ뾶�ı�ѡ�� ���Ұ�Բ����ʽ
		 */
		public static final int btnRadius_HORIZONTAL = -2;
		/**
		 * ��ťԲ�ǰ뾶�ı�ѡ�� ����
		 */
		public static final int btnRadius_SQUARE = -1;
		/**
		 * ��ť��ʼ״̬��Ĭ����ɫ
		 */
		public static final int btnColor_DEFAULT = Color.rgb(0, 153, 204);
		/**
		 * ��ťĬ�ϵĳɹ���ɫ
		 */
		public static final int btnColor_SUCCESS = Color.rgb(153, 204, 0);
		/**
		 * ��ťĬ�ϵ��쳣��ɫ
		 */
		public static final int btnColor_ERROR = Color.rgb(255, 68, 68);
		/**
		 * ��ťĬ�ϵ�ͼƬ
		 */
		public static final int btnImage_NONE = -1;
		/**
		 * ��ťͼ��ĳߴ�仯����
		 * ����ڰ�ť���
		 */
		public static final int btnIconSizeType_WIDTH = 0;
		/**
		 * ��ťͼ��ĳߴ�仯����
		 * ����ڰ�ť�߶�
		 */
		public static final int btnIconSizeType_HEIGHT = 1;
		/**
		 * ��ťͼ��ĳߴ�仯����
		 * �Զ�,�Զ�ѡ���С���Ǹ�
		 */
		public static final int btnIconSizeType_AUTO = -1;
		
		// ��ťԲ�ǵĳߴ�
		private int btnRadius = btnRadius_HORIZONTAL;
		// ��ť��ʼ����ɫ
		private int btnStartBgColor = btnColor_DEFAULT;
		// ��ť��ʼ����ͼƬ
		private int btnStartBgImage = btnImage_NONE;
		// ��ť��ʼ����
		private String btnStartText = "";
		// ��ť���ǰ������ɫ
		private int btnStartTextColor = Color.WHITE;
		// ��ť��ʼ��ͼƬ
		private int btnStartIcon = btnImage_NONE;
		// ��ť��������ɫ
		private int btnEndBgColor = btnColor_SUCCESS;
		// ��ť��������ͼƬ
		private int btnEndBgImage = btnImage_NONE;
		// ��ť��������
		private String btnEndText = "";
		// ��ť����������ɫ
		private int btnEndTextColor = Color.WHITE;
		// ��ť������ͼƬ
		private int btnEndIcon = btnImage_NONE;
		// ��ť�쳣����ɫ
		private int btnErrorBgColor = btnColor_ERROR;
		// ��ť�쳣����ͼƬ
		private int btnErrorBgImage = btnImage_NONE;
		// ��ť��������
		private String btnErrorText = "";
		// ��ť����������ɫ
		private int btnErrorTextColor = Color.WHITE;
		// ��ť������ͼƬ
		private int btnErrorIcon = btnImage_NONE;
		// ��������ɫ
		private int btnProgressColor = btnColor_DEFAULT;
		// ���������(�밴ť�߶ȵı���)
		private float btnProgressWidth = 0.05f;
		// ������������ɫ
		private int btnProgressBgColor = Color.parseColor("#e7e7e7");
		// �Ƿ���ʾ�ٷֱ�
		private boolean isShowPercent = false;
		// ���������ı����Ƿ�͸��
		private boolean isProgressCenterLucency = true;
		// ���������ı�������ɫ
		private int btnProgressCenterColor = Color.WHITE;
		// ���������ı�����ͼƬ
		private int btnProgressCenterImage = btnImage_NONE;
		// ��������ֵ����ɫ
		private int btnProgressPercentColor = Color.GRAY;
		//��ťͼ��ĳߴ�
		private float btnIconSize = 0.4f;
		//ͼ�갴ť�ı�������
		private int btnIconSizeType = btnIconSizeType_AUTO;
		//��ťͼ���Բ�Ǵ�С
		private int btnIconRadius = 0;
		/**
		 * ��ťԲ�ǵĳߴ�
		 * 
		 * @param btnRadius
		 *            Բ�ǵ�px��
		 * @return
		 */
		public Builder setBtnRadius(int btnRadius) {
			this.btnRadius = btnRadius;
			return this;
		}

		/**
		 * ���ð�ť��ʼʱ�ı���ɫ
		 * 
		 * @param btnStartBgColor
		 * @return
		 */
		public Builder setBtnStartBgColor(int btnStartBgColor) {
			this.btnStartBgColor = btnStartBgColor;
			return this;
		}

		/**
		 * ���ð�ť��ʼʱ�ı���ͼƬ
		 * 
		 * @param btnStartBgImage
		 *            ͼƬ��id
		 * @return
		 */
		public Builder setBtnStartBgImage(int btnStartBgImage) {
			this.btnStartBgImage = btnStartBgImage;
			return this;
		}

		/**
		 * ���ð�ť��ʼʱ��������ʾ
		 * 
		 * @param btnStartText
		 * @return
		 */
		public Builder setBtnStartText(String btnStartText) {
			this.btnStartText = btnStartText;
			return this;
		}

		/**
		 * ���ð�ť��ʼʱ��ʾ���ֵ���ɫ
		 * 
		 * @param btnStartTextColor
		 * @return
		 */
		public Builder setBtnStartTextColor(int btnStartTextColor) {
			this.btnStartTextColor = btnStartTextColor;
			return this;
		}

		/**
		 * ���ð�ť��ʼʱ��ͼ����ʾ
		 * 
		 * @param btnStartIcon
		 * @return
		 */
		public Builder setBtnStartIcon(int btnStartIcon) {
			this.btnStartIcon = btnStartIcon;
			return this;
		}

		/**
		 * ���ð�ť����ʱ�ı�����ɫ
		 * 
		 * @param btnEndBgColor
		 * @return
		 */
		public Builder setBtnEndBgColor(int btnEndBgColor) {
			this.btnEndBgColor = btnEndBgColor;
			return this;
		}

		/**
		 * ���ð�ť����ʱ�ı���ͼƬ
		 * 
		 * @param btnEndBgImage
		 * @return
		 */
		public Builder setBtnEndBgImage(int btnEndBgImage) {
			this.btnEndBgImage = btnEndBgImage;
			return this;
		}

		/**
		 * ���ð�ť����ʱ��������ʾ
		 * 
		 * @param btnEndText
		 * @return
		 */
		public Builder setBtnEndText(String btnEndText) {
			this.btnEndText = btnEndText;
			return this;
		}

		/**
		 * ���ð�ť����ʱ��������ɫ
		 * 
		 * @param btnEndTextColor
		 * @return
		 */
		public Builder setBtnEndTextColor(int btnEndTextColor) {
			this.btnEndTextColor = btnEndTextColor;
			return this;
		}

		/**
		 * ���ð�ť����ʱ��ͼ��
		 * 
		 * @param btnEndIcon
		 * @return
		 */
		public Builder setBtnEndIcon(int btnEndIcon) {
			this.btnEndIcon = btnEndIcon;
			return this;
		}

		/**
		 * ���ð�ť�쳣����ʱ�ı�����ɫ
		 * 
		 * @param btnErrorBgColor
		 * @return
		 */
		public Builder setBtnErrorBgColor(int btnErrorBgColor) {
			this.btnErrorBgColor = btnErrorBgColor;
			return this;
		}

		/**
		 * ���ð�ť�쳣����ʱ�ı���ͼƬ
		 * 
		 * @param btnErrorBgImage
		 * @return
		 */
		public Builder setBtnErrorBgImage(int btnErrorBgImage) {
			this.btnErrorBgImage = btnErrorBgImage;
			return this;
		}

		/**
		 * ���ð�ť�쳣ʱ������
		 * 
		 * @param btnErrorText
		 * @return
		 */
		public Builder setBtnErrorText(String btnErrorText) {
			this.btnErrorText = btnErrorText;
			return this;
		}

		/**
		 * ���ð�ť�쳣ʱ���������ɫ
		 * 
		 * @param btnErrorTextColor
		 * @return
		 */
		public Builder setBtnErrorTextColor(int btnErrorTextColor) {
			this.btnErrorTextColor = btnErrorTextColor;
			return this;
		}

		/**
		 * ���ð�ť�쳣ʱ��ͼ��
		 * 
		 * @param btnErrorIcon
		 * @return
		 */
		public Builder setBtnErrorIcon(int btnErrorIcon) {
			this.btnErrorIcon = btnErrorIcon;
			return this;
		}

		/**
		 * ���ð�ť��������ɫ
		 * 
		 * @param btnProgressColor
		 * @return
		 */
		public Builder setBtnProgressColor(int btnProgressColor) {
			this.btnProgressColor = btnProgressColor;
			return this;
		}

		/**
		 * ���ð�ť���������
		 * 
		 * @param btnProgressWidth
		 * @return
		 */
		public Builder setBtnProgressWidth(float btnProgressWidth) {
			this.btnProgressWidth = btnProgressWidth;
			return this;
		}

		/**
		 * ���ð�ť�������ı�����ɫ
		 * 
		 * @param btnProgressBgColor
		 * @return
		 */
		public Builder setBtnProgressBgColor(int btnProgressBgColor) {
			this.btnProgressBgColor = btnProgressBgColor;
			return this;
		}

		/**
		 * ���ð�ť�Ƿ���ʾ������ֵ��ʾ
		 * 
		 * @param isShowPercent
		 * @return
		 */
		public Builder setShowPercent(boolean isShowPercent) {
			this.isShowPercent = isShowPercent;
			return this;
		}

		/**
		 * ���ð�ť���������Ĳ����Ƿ�͸��
		 * 
		 * @param isProgressCenterLucency
		 * @return
		 */
		public Builder setProgressCenterLucency(boolean isProgressCenterLucency) {
			this.isProgressCenterLucency = isProgressCenterLucency;
			return this;
		}

		/**
		 * ���ð�ť���������Ĳ�����ɫ
		 * 
		 * @param btnProgressCenterColor
		 * @return
		 */
		public Builder setBtnProgressCenterColor(int btnProgressCenterColor) {
			this.btnProgressCenterColor = btnProgressCenterColor;
			return this;
		}
		/**
		 * ���ý��������ĵ�ͼƬ
		 * @param btnProgressCenterImage
		 * @return
		 */
		public Builder setBtnProgressCenterImage(int btnProgressCenterImage) {
			this.btnProgressCenterImage = btnProgressCenterImage;
			return this;
		}

		/**
		 * ���ð�ť��������ֵ����ɫ
		 * 
		 * @param btnProgressPercentColor
		 * @return
		 */
		public Builder setBtnProgressPercentColor(int btnProgressPercentColor) {
			this.btnProgressPercentColor = btnProgressPercentColor;
			return this;
		}
		/**
		 * ���ð�ťͼ��Ĵ�С�ߴ�
		 * ������,����ڰ�ť�Ĵ�С
		 * @param btnIconSize
		 * @return
		 */
		public Builder setBtnIconSize(float btnIconSize) {
			this.btnIconSize = btnIconSize;
			return this;
		}
		/**
		 * ��ťͼ���С�Ա���Զ���
		 * @param btnIconSizeType
		 * @return
		 */
		public Builder setBtnIconSizeType(int btnIconSizeType) {
			this.btnIconSizeType = btnIconSizeType;
			return this;
		}
		/**
		 * ����ͼ���Բ�Ǵ�С
		 * @param btnIconRadius
		 * @return
		 */
		public Builder setBtnIconRadius(int btnIconRadius) {
			this.btnIconRadius = btnIconRadius;
			return this;
		}
		
	}
}
