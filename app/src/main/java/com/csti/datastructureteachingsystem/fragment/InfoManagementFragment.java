package com.csti.datastructureteachingsystem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.activity.MyPostActivity;
import com.csti.datastructureteachingsystem.activity.MyReplyActivity;
import com.csti.datastructureteachingsystem.activity.MySettingActivity;

public class InfoManagementFragment extends Fragment {

    private LinearLayout mMyPost;
    private LinearLayout mMyReply;
    private LinearLayout mMySetting;
    private int mPressColor;
    private int mDefaultColor;

    public static InfoManagementFragment newInstance() {
        InfoManagementFragment fragment = new InfoManagementFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        mPressColor=getResources().getColor(R.color.press_color);
        mDefaultColor=getResources().getColor(R.color.white);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_info_management, container, false);
        mMyPost=v.findViewById(R.id.fab);
        mMyReply=v.findViewById(R.id.reply_button);
        mMySetting=v.findViewById(R.id.setting);

        mMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MyPostActivity.newIntent(getActivity()));
                setPressColor(mMyPost);
            }
        });
        mMyReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MyReplyActivity.newIntent(getActivity()));
                setPressColor(mMyReply);
            }
        });
        mMySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MySettingActivity.newIntent(getActivity()));
                setPressColor(mMySetting);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setDefaultColor(mMyPost);
        setDefaultColor(mMyReply);
        setDefaultColor(mMySetting);
    }

    private void setPressColor(LinearLayout linearLayout){
        linearLayout.setBackgroundColor(mPressColor);
    }

    private void setDefaultColor(LinearLayout linearLayout){
        linearLayout.setBackgroundColor(mDefaultColor);
    }
}
