package com.example.dingdong.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dingdong.R;
import com.example.dingdong.widget.LogImageView;
import com.example.dingdong.widget.LogTextView;

/**
 * Created by CCX on 2017/8/21.
 * 基本baseFragment
 */
public abstract class BaseFragment extends Fragment{

    private boolean isLoadView;//视图是否加载完成
    private boolean isFragmentVisible;// fragment 是否被显示
    protected boolean isLazyLoadEnabled; //是否开启懒加载

    private boolean isLoadData;//是否加载了数据;
    protected  View view =null;

    public void enableLazyLoad(){
        isLazyLoadEnabled = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isFragmentVisible =isVisibleToUser;//是否加载当前fragment
        if(isFragmentVisible &&isLoadView&&isLazyLoadEnabled&&!isLoadData){
             isLoadData=true;
             initData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(initLayout()>0){
            view=inflater.inflate(initLayout(), null);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        isLoadView =true;
        if(isLazyLoadEnabled){//开启了懒加载
            initEvent();
            if(isFragmentVisible &&!isLoadData){
                isLoadData=true;
                initData();
            }
        }else{
            initEvent();
            if(!isLoadData){
                isLoadData=true;
                initData();
            }

        }
    }

    protected abstract int initLayout();
    protected abstract void initView(View view);
    protected abstract void initEvent();
    protected abstract void initData();

    /**
     * 设置标题栏左边的字体
     *
     * @param leftText
     */
    public void setLeftText(String leftText, View.OnClickListener onClickListener) {
        view.findViewById(R.id.left_text_tv).setVisibility(View.VISIBLE);
        if (leftText != null) {
            ((TextView) view.findViewById(R.id.left_text_tv)).setText(leftText);
        }
        if (onClickListener != null) {
            view.findViewById(R.id.left_text_tv).setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置标题栏左边的字体
     *
     * @param resId
     * @param onClickListener
     */
    public void setLeftImageRes(int resId, View.OnClickListener onClickListener) {
        view.findViewById(R.id.left_iv).setVisibility(View.VISIBLE);
        ((ImageView) view.findViewById(R.id.left_iv)).setImageResource(resId);
        if (onClickListener != null) {
            ((ViewGroup) view.findViewById(R.id.left_iv).getParent()).setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置左边为默认的图片（向左的箭头，以及返回事件）
     *
     * @author telenewbie
     */
    public void setLeftDefault() {
        setLeftImageRes(R.drawable.title_back_select, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 设置标题栏左边的字体
     *
     * @param rightText
     */
    public TextView setRightText(String rightText, View.OnClickListener onClickListener) {
        TextView textView = (TextView) view.findViewById(R.id.right_text_tv);
        textView.setVisibility(View.VISIBLE);
        textView.setText(rightText);
        if (onClickListener != null) {
            textView.setOnClickListener(onClickListener);
        }
        return textView;
    }

    public LogImageView setRightIv(int rightIvRes, View.OnClickListener onClickListener){
        LogImageView rightIv = (LogImageView) view.findViewById(R.id.right_iv);
        rightIv.setVisibility(View.VISIBLE);
        rightIv.setBackgroundResource(rightIvRes);
        rightIv.setOnClickListener(onClickListener);
        return rightIv;
    }

    /**
     * 暂存功能
     *
     * @param onClickListener
     */
    public void setRightStaging(View.OnClickListener onClickListener) {
        view.findViewById(R.id.right_staging_tv).setVisibility(View.VISIBLE);
        if (onClickListener != null) {
            view.findViewById(R.id.right_staging_tv).setOnClickListener(onClickListener);
        }
    }

    public void setRightText(int rightTextRes, View.OnClickListener onClickListener) {
        setRightText(getResources().getString(rightTextRes), onClickListener);
    }

    /**
     * 设置右边的文字
     *
     * @param text
     */
    public void setRightText(String text) {
        ((TextView) view.findViewById(R.id.right_text_tv)).setText("");
        ((TextView) view.findViewById(R.id.right_text_tv)).setText(text);
    }
    public void setRightVisibility(boolean isGone) {
        if (isGone)
            view.findViewById(R.id.right_text_tv).setVisibility(View.GONE);
        else
            view.findViewById(R.id.right_text_tv).setVisibility(View.VISIBLE);
    }
    /**
     * 显示标题
     */
    public void setText(String value) {
        ((TextView) view.findViewById(R.id.title_tv)).setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        ((TextView) view.findViewById(R.id.title_tv)).setText(value);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoadView = false;
    }
}
