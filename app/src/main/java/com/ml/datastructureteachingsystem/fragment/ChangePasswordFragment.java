package com.ml.datastructureteachingsystem.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ml.datastructureteachingsystem.R;
import com.ml.datastructureteachingsystem.activity.MainActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static android.content.Context.MODE_PRIVATE;

public class ChangePasswordFragment extends Fragment {
    private EditText oldpassword;
    private EditText newpassword;
    private TextView sure;
    private ImageView close;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changepassword, container, false);
        oldpassword = view.findViewById(R.id.oldpassword);
        newpassword = view.findViewById(R.id.newpassword);
        sure = view.findViewById(R.id.change_yes);
        close = view.findViewById(R.id.close);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldpassword.getText().equals("") && newpassword.getText().equals("")) {
                    Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    BmobUser.updateCurrentUserPassword(oldpassword.getText().toString(), newpassword.getText().toString(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(getActivity(), "密码修改成功", Toast.LENGTH_SHORT).show();
                                BmobUser.logOut();
                                sharedPreferences = getActivity().getSharedPreferences("data", MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putString("account", "");
                                editor.putString("password", "");
                                editor.apply();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("closeType", 1);
                                startActivity(intent);
                            } else {

                            }
                        }
                    });
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}
