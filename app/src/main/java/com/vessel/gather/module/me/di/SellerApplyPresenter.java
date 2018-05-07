package com.vessel.gather.module.me.di;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData;
import com.miguelbcr.ui.rx_paparazzo2.entities.Response;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.app.data.entity.VariableResponse;
import com.vessel.gather.app.utils.progress.ProgressSubscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static android.app.Activity.RESULT_OK;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class SellerApplyPresenter extends BasePresenter<SellerApplyContract.Model, SellerApplyContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    private List<TypeListResponse.TypesBean> typesBeanList = new ArrayList<>();
    private List<String> types = new ArrayList<>();

    private int currentPosition;
    private String avatarUri0, avatarUri1, avatarUri2;

    @Inject
    public SellerApplyPresenter(SellerApplyContract.Model model, SellerApplyContract.View rootView) {
        super(model, rootView);
    }

    public void queryTypeList() {
        mModel.queryTypeList(1).subscribe(
                new ErrorHandleSubscriber<TypeListResponse>(mErrorHandler) {
                    @Override
                    public void onNext(TypeListResponse typeListResponse) {
                        if (typeListResponse.getTypes().size() > 0) {
                            typesBeanList.clear();
                            types.clear();
                            typesBeanList.addAll(typeListResponse.getTypes());
                            for (TypeListResponse.TypesBean typesBean : typesBeanList) {
                                types.add(typesBean.getTypeName());
                            }
                        }
                        mRootView.setTypeAdapter(types);
                    }
                }
        );
    }

    public void authorityApply(String name, String sex, String city, String address, int position, String tongyiCode) {
        if (TextUtils.isEmpty(avatarUri0)) {
            mRootView.showMessage("请添加证件照正面");
            return;
        }
        if (TextUtils.isEmpty(avatarUri1)) {
            mRootView.showMessage("请添加证件照反面");
            return;
        }
        if (TextUtils.isEmpty(avatarUri2)) {
            mRootView.showMessage("请添加营业执照");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("type", 1);
        map.put("name", name);
        map.put("sex", sex);
        map.put("idNumber", 0);
        map.put("address", 0);
        map.put("city", city);
        map.put("shopAddr", address);
        map.put("typeId", typesBeanList.get(position).getTypeId());
        map.put("code", tongyiCode);
        map.put("idFrontPic", avatarUri0);
        map.put("idConPic", avatarUri1);
        map.put("idSeizePic", avatarUri2);
        mModel.authorityApply(map).subscribe(
                new ProgressSubscriber<Boolean>(mAppManager.getCurrentActivity(), mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        super.onNext(aBoolean);
                        mRootView.showMessage("已提交申请");
                        mRootView.killMyself();
                    }
                }
        );
    }

    private void uploadFile(File file) {
        mModel.uploadFile(file).subscribe(
                new ProgressSubscriber<VariableResponse>(mAppManager.getCurrentActivity(), mErrorHandler) {
                    @Override
                    public void onNext(VariableResponse variableResponse) {
                        super.onNext(variableResponse);
                        switch (currentPosition) {
                            case 0:
                                avatarUri0 = variableResponse.getUrl();
                                break;
                            case 1:
                                avatarUri1 = variableResponse.getUrl();
                                break;
                            case 2:
                                avatarUri2 = variableResponse.getUrl();
                                break;
                        }
                        mRootView.showImage(currentPosition, file);
                    }
                }
        );
    }

    public void pickAvatar(int position) {
        this.currentPosition = position;
        CharSequence[] items = {"相册", "相机"};
        new AlertDialog.Builder(mAppManager.getCurrentActivity())
                .setTitle("选择图片来源")
                .setItems(items, (dialog, which) -> {
                    if (which == 0) {
                        RxPaparazzo.single(mAppManager.getCurrentActivity())
                                .usingGallery()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new AvatarPick());
                    } else {
                        RxPaparazzo.single(mAppManager.getCurrentActivity())
                                .usingCamera()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new AvatarPick());
                    }
                })
                .create().show();
    }

    private class AvatarPick implements Observer<Response<Activity, FileData>> {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Response<Activity, FileData> activityFileDataResponse) {
            if (activityFileDataResponse.resultCode() != RESULT_OK) {
                mRootView.showMessage("您取消了获取图片");
                return;
            }

            uploadFile(activityFileDataResponse.data().getFile());
        }

        @Override
        public void onError(Throwable e) {
            Log.e("error", e.toString());
        }

        @Override
        public void onComplete() {

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mErrorHandler = null;
    }

}