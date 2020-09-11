package com.hjc.reader.ui.wan.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hjc.reader.R;
import com.hjc.reader.bean.response.WanBannerBean;
import com.hjc.reader.databinding.ItemBannerBinding;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class MyBannerAdapter extends BannerAdapter<WanBannerBean.DataBean, MyBannerAdapter.BannerViewHolder> {

    public MyBannerAdapter(List<WanBannerBean.DataBean> data) {
        super(data);
    }


    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ItemBannerBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_banner, parent, false);
        return new BannerViewHolder(viewDataBinding);
    }

    @Override
    public void onBindView(BannerViewHolder holder, WanBannerBean.DataBean data, int position, int size) {
        if (data == null){
            return;
        }
        if (holder.mBinding != null){
            holder.mBinding.setBannerBean(data);

            String num = (position + 1) + "/" + size;
            holder.mBinding.tvNum.setText(num);
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ItemBannerBinding mBinding;

        BannerViewHolder(ItemBannerBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
