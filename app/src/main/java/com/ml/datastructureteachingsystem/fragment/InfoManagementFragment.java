package com.ml.datastructureteachingsystem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ml.datastructureteachingsystem.R;
import com.ml.datastructureteachingsystem.activity.MyPostActivity;
import com.ml.datastructureteachingsystem.activity.MyReplyActivity;
import com.ml.datastructureteachingsystem.activity.MySettingActivity;
import com.ml.datastructureteachingsystem.handler.AvatarLoader;
import com.ml.datastructureteachingsystem.module.User;

import cn.bmob.v3.BmobUser;

public class InfoManagementFragment extends Fragment {

    private LinearLayout mMyPost;
    private LinearLayout mMyReply;
    private LinearLayout mMySetting;
    private int mPressColor;
    private int mDefaultColor;
    private ImageView mAvatar;
    private TextView mNick;
    private TextView admin;

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
        mMyPost=v.findViewById(R.id.post);
        mMyReply=v.findViewById(R.id.reply);
        mMySetting=v.findViewById(R.id.setting);
        mAvatar=v.findViewById(R.id.headphoto);
        mNick=v.findViewById(R.id.nick);
        admin=v.findViewById(R.id.admin);


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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            User user=BmobUser.getCurrentUser(User.class);
            new AvatarLoader(mAvatar,user).load();
            mNick.setText(user.getUsername());
            if(mNick.getText().equals("admin")){
                admin.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setPressColor(LinearLayout linearLayout){
        linearLayout.setBackgroundColor(mPressColor);
    }

    private void setDefaultColor(LinearLayout linearLayout){
        linearLayout.setBackgroundColor(mDefaultColor);
    }
}
