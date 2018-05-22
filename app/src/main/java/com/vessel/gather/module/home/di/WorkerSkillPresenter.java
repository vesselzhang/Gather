package com.vessel.gather.module.home.di;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;

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
public class WorkerSkillPresenter extends BasePresenter<WorkerSkillContract.Model, WorkerSkillContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    private List<TypeListResponse.TypesBean> typesBeanList = new ArrayList<>();
    private List<String> types = new ArrayList<>();
    private String avatarUri;

    @Inject
    public WorkerSkillPresenter(WorkerSkillContract.Model model, WorkerSkillContract.View rootView) {
        super(model, rootView);
    }

    public void pickImage() {
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

    public void saveSkill(long skillId, String name, int typePosition, String describe, String price, String unit) {
        Map<String, Object> map = new HashMap<>();
        map.put("skillName", name);
        map.put("price", price);
        map.put("type", types.get(typePosition));
        if (!TextUtils.isEmpty(avatarUri)) {
            map.put("skillPic", avatarUri);
        }
        if (skillId != -1) {
            map.put("skillId", skillId);
        }
        map.put("remark", describe);
        map.put("unit", unit);
        mModel.saveSkill(map).subscribe(
                new ProgressSubscriber<Boolean>(mAppManager.getCurrentActivity(), mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        super.onNext(aBoolean);
                    }
                }
        );
    }

    public void deleteSkill(long skillId) {
        mModel.removeSkill(skillId).subscribe(
                new ProgressSubscriber<Boolean>(mAppManager.getCurrentActivity(), mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        super.onNext(aBoolean);
                        mRootView.showMessage("删除成功");
                        mRootView.killMyself();
                    }
                }
        );
    }

    public void queryTypeList() {
        mModel.queryTypeList(0).subscribe(
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

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(mAppManager.getCurrentActivity(), android.R.layout.simple_spinner_item, types);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        mRootView.setAdapter(adapter);
                    }
                }
        );
    }

    private void uploadFile(File file) {
        mModel.uploadFile(file).subscribe(
                new ErrorHandleSubscriber<VariableResponse>(mErrorHandler) {
                    @Override
                    public void onNext(VariableResponse variableResponse) {
                        avatarUri = variableResponse.getUrl();
                        if (!TextUtils.isEmpty(avatarUri)) {
                            mRootView.showImage(avatarUri);
                        }
                    }
                }
        );
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
        this.mErrorHandler = null;
    }
}