package com.xupt3g.personalmanagementview.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.personalmanagementview.R;

public class UserAvatarPreviewActivity extends AppCompatActivity {
    public static final String defAvatar = "avatarDefault";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_avatar_preview);
        ImageView avatar = findViewById(R.id.avatar_view);
        String userAvatar = getIntent().getStringExtra("UserAvatar");
        UiTool.setImmersionBar(this, false);
        if (!defAvatar.equals(userAvatar)) {
            Glide.with(this).load(userAvatar).into(avatar);
        } else {
            Glide.with(this).load(R.drawable.personal_default_avatar_2).into(avatar);
        }
        avatar.setOnClickListener(view -> {});
        findViewById(R.id.avatar_previewLayout).setOnClickListener(view -> {
            finish();
        });
    }
}