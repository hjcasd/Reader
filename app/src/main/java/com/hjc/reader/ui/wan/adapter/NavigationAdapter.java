package com.hjc.reader.ui.wan.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;

import java.util.List;

public class NavigationAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private OnSelectListener listener;
    private int selectedPosition = -1;

    public NavigationAdapter(@Nullable List<String> data) {
        super(R.layout.item_navigation_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tvChapter = helper.getView(R.id.tv_chapter);
        tvChapter.setText(item);

        int position = helper.getAdapterPosition();
        if (selectedPosition == position) {
            tvChapter.setSelected(true);
        } else {
            tvChapter.setSelected(false);
        }

        if (listener != null) {
            tvChapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelection(position);
                    listener.onSelected(position);
                }
            });
        }
    }

    public void setSelection(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    public interface OnSelectListener {
        void onSelected(int position);
    }

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }
}
