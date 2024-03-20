package com.xupt3g.personalmanagementview.view;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.libbase.BuildConfig;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.button.RippleView;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheetItemView;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.grouplist.XUICommonListItemView;
import com.xuexiang.xui.widget.grouplist.XUIGroupListView;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.personalmanagementview.R;
import com.xupt3g.personalmanagementview.model.retrofit.AccountInfoResponse;
import com.xupt3g.personalmanagementview.presenter.AccountInfoPresenter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.personalmanagementview.View.AccountInfoFragment
 *
 * @author: shallew
 * @data: 2024/2/6 0:06
 * @about: TODO 账号信息管理页面
 */
@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class AccountInfoFragment extends Fragment implements AccountInfoModifyImpl {
    private View mView;
    private ImageView backButton;
    private XUIGroupListView mGroupListView;
    private XUICommonListItemView item1;
    private XUICommonListItemView item2;
    private XUICommonListItemView item3;
    private XUICommonListItemView item4;
    private RippleView confirmButton;
    /**
     * 临时的用户信息类。如果修改成功，将这个AccountInfo替换PersonalManagementFragment的Response
     * 修改不成功将会换回PersonalManagementFragment的Response
     */
    private AccountInfoResponse temAccountInfo;
    /**
     * 原来的用户信息类 PersonalManagementFragment的Response
     */
    private AccountInfoResponse originalAccountInfo;
    /**
     * Presenter层实例
     */
    private AccountInfoPresenter presenter;
    /**
     * 只修改用户文字信息的TAG
     */
    private final int MODIFY_ONLY_INFO = 1234;
    /**
     * 只修改用户头像信息的TAG
     */
    private final int MODIFY_ONLY_AVATAR = MODIFY_ONLY_INFO + 1;
    /**
     * 全部信息都修改
     */
    private final int MODIFY_ALL = MODIFY_ONLY_AVATAR + 1;
    /**
     * 记录标志的变量
     */
    private int modifyTarget = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.personal_frag_personal_account_info, container, false);

        mGroupListView = mView.findViewById(R.id.account_info_groupListView);
        confirmButton = mView.findViewById(R.id.account_info_ripple_confirm);

        backButton = mView.findViewById(R.id.account_info_back);
        backButton.setOnClickListener(view -> {
            requireActivity().onBackPressed();
        });

        presenter = new AccountInfoPresenter(this);
        presenter.setResponseLiveData(PersonalManagementFragment.presenter.getResponseLiveData());

        confirmButton.setVisibility(View.GONE);

        confirmButton.setOnClickListener(view -> {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(requireContext());
            builder.title("提示");
            builder.content("修改之后无法自动恢复，是否确认当前修改？");
            builder.positiveText("确认");
            builder.negativeText("取消");
            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (modifyTarget == MODIFY_ONLY_INFO) {
                        //只修改了其他文字信息
                        presenter.accountInfoModify(temAccountInfo.getUserInfo(), null);
                    } else if (modifyTarget == MODIFY_ONLY_AVATAR) {
                        //只修改了头像
                        MultipartBody.Part part = userAvatarBodyInit();
                        presenter.accountInfoModify(null, part);
                    } else if (modifyTarget == MODIFY_ALL) {
                        MultipartBody.Part part = userAvatarBodyInit();
                        presenter.accountInfoModify(temAccountInfo.getUserInfo(), part);
                    }
                    ToastUtils.toast("确认修改");
                    confirmButton.setVisibility(View.GONE);
                }
            });
            builder.onNegative((dialog, which) -> {
            });
            builder.show();
        });

        PersonalManagementActivity.setFragmentChangeTransition(getContext(), this);

        //获取原用户信息
        originalAccountInfo = PersonalManagementFragment.presenter.getResponseLiveData().getValue();


        if (originalAccountInfo != null) {
            //初始化临时Response
            temAccountInfo = new AccountInfoResponse(originalAccountInfo);
            initGroupListView();
        }


        return mView;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initGroupListView() {

        //设置头像
        item1 = mGroupListView.createItemView(
                null,
                "设置头像", null,
                XUICommonListItemView.HORIZONTAL,
                XUICommonListItemView.ACCESSORY_TYPE_NONE);
        Glide.with(requireContext()).load(originalAccountInfo.getUserInfo().getAvatar())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        item1.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });


        XUIGroupListView.newSection(mView.getContext())
                .addItemView(item1, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BottomSheet.BottomListSheetBuilder builder = new BottomSheet.BottomListSheetBuilder(getContext());
                        builder.addItem("用相机拍摄", "camera");
                        builder.addItem("从相册中选", "album");
                        builder.setOnSheetItemClickListener(new BottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(BottomSheet dialog, View itemView, int position, String tag) {
                                        dialog.dismiss();
                                        ToastUtils.toast(tag);
                                        if ("camera".equals(tag)) {
                                            openCamera();
                                        } else if ("album".equals(tag)) {
                                            openAlbum();
                                        }
                                    }
                                })
                                .build()
                                .show();

                    }
                })
                .addTo(mGroupListView);


        item2 = mGroupListView.createItemView(
                null,
                "昵称",
                originalAccountInfo.getUserInfo().getNickname(),//填入用户昵称
                XUICommonListItemView.HORIZONTAL,
                XUICommonListItemView.ACCESSORY_TYPE_NONE);

        String sex = "";
        if (originalAccountInfo.getUserInfo().getSex() == 0) {
            sex = "男";
        } else {
            sex = "女";
        }
        item3 = mGroupListView.createItemView(
                null,
                "性别",
                sex,
                XUICommonListItemView.HORIZONTAL,
                XUICommonListItemView.ACCESSORY_TYPE_NONE);

        item4 = mGroupListView.createItemView(
                null,
                "自我介绍",
                originalAccountInfo.getUserInfo().getInfo(),//用户的介绍
                XUICommonListItemView.VERTICAL,
                XUICommonListItemView.ACCESSORY_TYPE_NONE);


        int size = DensityUtils.dp2px(mView.getContext(), 20);
        XUIGroupListView.newSection(getContext())
                .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(item2, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //弹窗设置新的用户昵称
                        new MaterialDialog.Builder(requireContext())
                                .iconRes(R.drawable.personal_icon_nickname)
                                .title("昵称")
                                .content("请输入你新的昵称")
                                .inputType(InputType.TYPE_CLASS_TEXT)
                                .input(
                                        null,
                                        "",
                                        false,
                                        ((dialog, input) -> ToastUtils.toast(input.toString())))
                                .inputRange(0, 10)
                                .positiveText("确认")
                                .negativeText("取消")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();//关闭
                                        if (dialog.getInputEditText() != null) {
                                            String input = dialog.getInputEditText().getText().toString();
                                            ToastUtils.toast(input);
                                            //更新显示
                                            item2.setDetailText(input);
                                            //保存到临时用户信息中
                                            temAccountInfo.getUserInfo().setNickname(input);
                                            confirmButton.setVisibility(View.VISIBLE);
                                            if (modifyTarget == MODIFY_ONLY_AVATAR) {
                                                //如果前面只修改了头像
                                                modifyTarget = MODIFY_ALL;
                                            } else if (modifyTarget == 0) {
                                                //如果没有标记位
                                                modifyTarget = MODIFY_ONLY_INFO;
                                            }
                                        }
                                    }
                                })
                                .cancelable(false)
                                .show();
                    }
                })
                .addItemView(item3, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //弹窗修改用户性别
                        BottomSheet.BottomGridSheetBuilder builder = new BottomSheet.BottomGridSheetBuilder(getActivity());
                        builder.setIsShowButton(true)
                                .setButtonText("关闭")
                                .addItem(R.drawable.personal_icon_man, "男性", 0, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                                .addItem(R.drawable.personal_icon_woman, "女性", 1, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                                //性别 0代表男性 1代表女性
                                .setOnSheetItemClickListener(new BottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(BottomSheet dialog, BottomSheetItemView itemView) {
                                        dialog.dismiss();

                                        int tag = (int) itemView.getTag();
                                        if (tag == 0) {
                                            ToastUtils.toast("你好！先生");
                                            item3.setDetailText("男");
                                        } else if (tag == 1) {
                                            ToastUtils.toast("你好！女士");
                                            item3.setDetailText("女");
                                        }
                                        temAccountInfo.getUserInfo().setSex(tag);
                                        confirmButton.setVisibility(View.VISIBLE);
                                        if (modifyTarget == MODIFY_ONLY_AVATAR) {
                                            //如果前面只修改了头像
                                            modifyTarget = MODIFY_ALL;
                                        } else if (modifyTarget == 0) {
                                            //如果没有标记位
                                            modifyTarget = MODIFY_ONLY_INFO;
                                        }
                                    }
                                }).build().show();
                    }
                })
                .addItemView(item4, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //弹窗设置用户新的自我介绍
                        new MaterialDialog.Builder(requireContext())
                                .iconRes(R.drawable.personal_icon_introduce)
                                .title("介绍")
                                .content("添加你的自我介绍：")
                                .inputType(InputType.TYPE_CLASS_TEXT)
                                .input(
                                        null,
                                        "",
                                        false,
                                        ((dialog, input) -> ToastUtils.toast(input.toString())))
                                .inputRange(0, 200)
                                .positiveText("确认")
                                .negativeText("取消")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();//关闭
                                        if (dialog.getInputEditText() != null) {
                                            String input = dialog.getInputEditText().getText().toString();
                                            ToastUtils.toast(input);
                                            item4.setDetailText(input);//更新显示
                                            //保存临时用户信息
                                            temAccountInfo.getUserInfo().setInfo(input);
                                            confirmButton.setVisibility(View.VISIBLE);
                                            if (modifyTarget == MODIFY_ONLY_AVATAR) {
                                                //如果前面只修改了头像
                                                modifyTarget = MODIFY_ALL;
                                            } else if (modifyTarget == 0) {
                                                //如果没有标记位
                                                modifyTarget = MODIFY_ONLY_INFO;
                                            }
                                        }
                                    }
                                })
                                .cancelable(false)
                                .show();
                    }
                })
                .addTo(mGroupListView);
    }


    File outputImage;
    int CAMERA_REQUEST_CODE = 100000;
    int ALBUM_REQUEST_CODE = 100001;
    int CROP_REQUEST_CODE = 100002;
    Uri contentUri;

    public void openCamera() {
        if (!checkPermissionsIsAllowed(cameraPermissions)) {
            return;
        }//检查并申请权限

        outputImage = new File(requireActivity().getExternalCacheDir(), "photo");
        //会在这个应用关联目录下创建一个名为photo子目录
        try {
            if (outputImage.exists()) {
                //如果该目录存在，就会删除该目录下的东西。
                //不保存历史拍摄结果
                outputImage.delete();
            }
            //如果该目录是空的，就会创建一个文件用于保存拍摄的图片
            outputImage.createNewFile();
        } catch (IOException e) {
            Log.d("TAG", e.toString());
        }
        //第二步：将文件转换成uri对象
        if (Build.VERSION.SDK_INT >= 24) {
            //如果版本大于7.0，就调用FileProvider的getUriForFile()方法将图片路径（File）转换成Uri对象
            if (!BuildConfig.isModule) {
                //集成
                contentUri = FileProvider.getUriForFile(requireContext(), "com.xupt3g.hearttrip.app.provider", outputImage);
            } else {
                //组件化
                contentUri = FileProvider.getUriForFile(requireContext(), "com.xupt3g.personalmanagementview.provider", outputImage);
            }
        } else {
            //如果版本低于7.0，就调用Uri的fromFile()方法将图片路径（File）转换为Uri的实例
            contentUri = Uri.fromFile(outputImage);
        }
        //第三步：启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //指定图片的输出地址为imageUri，拍摄的图片会被指定保存在这个路径下。
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        //第一个参数表示启动的intent，第二个参数表示整形请求码
        //拍完照有结果返回到onActivityResult()中
    }

    public void openAlbum() {
        if (!checkPermissionsIsAllowed(albumPermissions)) {
            return;
        }//检查并申请权限

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (CAMERA_REQUEST_CODE == requestCode) {  //调用相机后返回
            if (resultCode == RESULT_OK) {
                ToastUtils.toast("RESULT_OK");

                cropPhoto(contentUri);
            }
        } else if (ALBUM_REQUEST_CODE == requestCode) {
            if (resultCode == RESULT_OK) {//调用相册后返回
                Uri uri = data.getData();
                cropPhoto(uri);
            }
        } else if (CROP_REQUEST_CODE == requestCode) {
            if (data == null) {//调用剪裁后返回
                return;
            }
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                //在这里获得了剪裁后的Bitmap对象，可以用于上传
                Bitmap image = bundle.getParcelable("data");
                BitmapDrawable drawable = new BitmapDrawable(image);
                //设置到ImageView上
                item1.setImageDrawable(drawable);
                if (modifyTarget == 0) {
                    //如果没有标记位
                    modifyTarget = MODIFY_ONLY_AVATAR;
                } else if (modifyTarget == MODIFY_ONLY_INFO) {
                    modifyTarget = MODIFY_ALL;
                }
                confirmButton.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        ToastUtils.toast("裁剪 ");
        Uri contentUri = Uri.fromFile(new File(getPhotoPath()));
        Intent intent = new Intent("com.android.camera.action.CROP");
        //Android 7.0需要临时添加读取Url的权限， 添加此属性是为了解决：调用裁剪框时候提示：图片无法加载或者加载图片失败或者无法加载此图片
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//发送裁剪信号，去掉也能进行裁剪
        intent.putExtra("scale", true);// 设置缩放
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //上述两个属性控制裁剪框的缩放比例。
        //当用户用手拉伸裁剪框时候，裁剪框会按照上述比例缩放。
        intent.putExtra("outputX", 300);//属性控制裁剪完毕，保存的图片的大小格式。
        intent.putExtra("outputY", 300);//你按照1:1的比例来裁剪的，如果最后成像是800*400，那么按照2:1的样式保存，
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出裁剪文件的格式
        intent.putExtra("return-data", true);//是否返回裁剪后图片的Bitmap
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);//设置输出路径
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    @NonNull
    private String getPhotoPath() {
        File file = new File(requireActivity().getPackageName() + "_userAvatar.jpg");
        return file.getPath();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //授权成功
                ToastUtils.toast("授权成功！");
            } else {
                ToastUtils.toast("授权失败！");
            }
        }
    }

    private String[] cameraPermissions = {Permission.CAMERA};
    private String[] albumPermissions = {Permission.MANAGE_EXTERNAL_STORAGE};
    private final int PERMISSION_REQUEST_CODE = 1212;

    /**
     * TODO 检查权限是否被打开
     */
    public boolean checkPermissionsIsAllowed(String[] permissions) {
        final boolean[] success = {false};
        //判断权限是否被授予了
        boolean granted = XXPermissions.isGranted(requireContext(), permissions);
        if (granted) {
            //如果被授予了，返回true
            return true;
        } else {
            //如果没被授予 动态申请权限
            XXPermissions.with(this)
                    .permission(permissions)
                    .request(new OnPermissionCallback() {
                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            //部分未授权成功
                            if (!allGranted) {
                                ToastUtils.toast("部分权限未获取成功");
                                return;
                            } else {
                                ToastUtils.toast("成功获取权限！");
                                success[0] = true;
                            }
                        }

                        @Override
                        public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                            if (doNotAskAgain) {
                                ToastUtils.toast("被永久拒绝授权，请手动进行授权！");
                                XXPermissions.startPermissionActivity(requireContext(), permissions);
                                if (XXPermissions.isGranted(requireContext(), permissions)) {
                                    success[0] = true;
                                }
                            } else {
                                ToastUtils.toast("权限获取失败！");
                            }
                        }
                    });
        }
        return success[0];
    }

    /**
     * TODO 数据修改成功回调
     */
    @Override
    public void modifySuccessful() {
        ToastUtils.toast("信息保存成功！");

        //通知UI更新
    }

    /**
     * TODO 数据修改失败回调
     */
    @Override
    public void modifyFailed() {
        ToastUtils.toast("信息保存失败！");

        initGroupListView();//将界面数据还原
    }

    /**
     * 生成用来传递给服务器的头像
     *
     * @return （MultipartBody.Part）
     */
    private MultipartBody.Part userAvatarBodyInit() {
        File file = new File(getPhotoPath());
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData("userAvatar", file.getName(), imageBody);
    }

}
