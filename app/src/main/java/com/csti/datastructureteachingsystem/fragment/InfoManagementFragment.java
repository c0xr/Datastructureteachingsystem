package com.csti.datastructureteachingsystem.fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.csti.datastructureteachingsystem.R;
import com.csti.datastructureteachingsystem.activity.MyPostActivity;
import com.csti.datastructureteachingsystem.activity.MyReplyActivity;
import com.csti.datastructureteachingsystem.activity.MySettingActivity;
import com.csti.datastructureteachingsystem.module.Person;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

import static android.content.Context.MODE_PRIVATE;
import static com.csti.datastructureteachingsystem.helper.SystemHelper.print;

public class InfoManagementFragment extends Fragment {

    private LinearLayout mMyPost;
    private LinearLayout mMyReply;
    private LinearLayout mMySetting;
    private int mPressColor;
    private int mDefaultColor;
    private SharedPreferences sharedPreferences;
    private ImageView mHeadphoto;
    Person user = BmobUser.getCurrentUser(Person.class);
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
        mHeadphoto=v.findViewById(R.id.headphoto);

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
       // sharedPreferences = getActivity().getSharedPreferences("data", MODE_PRIVATE);
      //  String headPhoto = sharedPreferences.getString("headphoto", "");
       // print(headPhoto);
       // if (!(headPhoto.equals(""))) {
//        bmobQuery.findObjects(new FindListener<GameScore>() {
//            @Override
//            public void done(List<GameScore> object,BmobException e) {
//                if(e==null){
//                    for (GameScore gameScore : object) {
//                        BmobFile bmobfile = gameScore.getPic();
//                        if(file!= null){
//                            //调用bmobfile.download方法
//                        }
//                    }
//                }else{
//                    toast("查询失败："+e.getMessage());
//                }
//            }
//        });
        File saveFile = new File(Environment.getExternalStorageDirectory(), user.getMheadphoto().getFilename());
        user.getMheadphoto().download(saveFile, new DownloadFileListener() {
            @Override
            public void done(String s, BmobException e) {
                displayImage(s);
            }

            @Override
            public void onProgress(Integer integer, long l) {

            }
        });
           // displayImage(headPhoto);
       // }
    }
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mHeadphoto.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getContext(), "得到图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void setPressColor(LinearLayout linearLayout){
        linearLayout.setBackgroundColor(mPressColor);
    }

    private void setDefaultColor(LinearLayout linearLayout){
        linearLayout.setBackgroundColor(mDefaultColor);
    }
}
