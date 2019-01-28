package com.hjc.reader.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjc.reader.R;


/**
 * @Author: HJC
 * @Date: 2019/1/7 11:32
 * @Description: 自定义标题栏(代替Toolbar使用)
 */
public class TitleBar extends LinearLayout {
    private String mTitleText;
    private float mTitleSize;
    private int mTitleColor;

    private int mLeftImage;
    private int mRightImage;

    private boolean isShowLine;

    private TextView tvTitle;
    private ImageView ivLeftImg;
    private ImageView ivRightImg;
    private View titleLine;

    private Context mContext;

    private onViewClick mClickListener;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        initTypeArray(attrs);
        initView();
        initData();
        addListener();
    }


    private void initTypeArray(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        mLeftImage = ta.getResourceId(R.styleable.TitleBar_leftImage, 0);
        mRightImage = ta.getResourceId(R.styleable.TitleBar_rightImage, 0);

        mTitleText = ta.getString(R.styleable.TitleBar_titleText);
        mTitleSize = ta.getDimensionPixelSize(R.styleable.TitleBar_titleSize, sp2px(18));
        mTitleColor = ta.getColor(R.styleable.TitleBar_titleColor, Color.BLACK);

        isShowLine = ta.getBoolean(R.styleable.TitleBar_isShowLine, false);

        ta.recycle();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_title_bar, this);
        ivLeftImg = findViewById(R.id.iv_left_img);
        ivRightImg = findViewById(R.id.iv_right_img);

        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setSelected(true);

        titleLine = findViewById(R.id.title_line);
    }

    private void initData() {
        //左图标
        if (mLeftImage != 0) {
            ivLeftImg.setVisibility(View.VISIBLE);
            ivLeftImg.setImageResource(mLeftImage);
        } else {
            ivLeftImg.setVisibility(View.GONE);
        }

        //标题
        if (!TextUtils.isEmpty(mTitleText)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(mTitleText);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, px2sp(mTitleSize));
            tvTitle.setTextColor(mTitleColor);
        } else {
            tvTitle.setVisibility(View.GONE);
        }

        //右图标
        if (mRightImage != 0) {
            ivRightImg.setVisibility(View.VISIBLE);
            ivRightImg.setImageResource(mRightImage);
        } else {
            ivRightImg.setVisibility(View.GONE);
        }

        titleLine.setVisibility(isShowLine ? View.VISIBLE : View.GONE);
    }

    private void addListener() {
        ivLeftImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.leftClick(v);
                }
            }
        });

        ivRightImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.rightClick(v);
                }
            }
        });
    }

    //设置标题
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    //设置标题大小
    public void setTitleSize(int size) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    //设置标题颜色
    public void setTitleTextColor(int color) {
        tvTitle.setTextColor(color);
    }

    //设置左图标
    public void setLeftDrawable(int res) {
        if (res != 0) {
            ivLeftImg.setVisibility(View.VISIBLE);
            ivLeftImg.setImageResource(res);
        } else {
            ivLeftImg.setVisibility(View.GONE);
        }
    }

    //设置右图标
    public void setRightDrawable(int res) {
        if (res != 0) {
            ivRightImg.setVisibility(View.VISIBLE);
            ivRightImg.setImageResource(res);
        } else {
            ivRightImg.setVisibility(View.GONE);
        }
    }

    public interface onViewClick {
        void leftClick(View view);

        void rightClick(View view);
    }

    public void setOnViewClickListener(onViewClick click) {
        this.mClickListener = click;
    }

    private int sp2px(float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private int px2sp(float pxValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
