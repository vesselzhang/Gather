package com.vessel.gather.module.home.di;

import android.widget.SpinnerAdapter;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.app.data.entity.VariableResponse;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public interface WorkerSkillContract {
    interface View extends IView {
        void setAdapter(SpinnerAdapter adapter);
        void showImage(String imageUri);
    }

    interface Model extends IModel {
        Observable<Boolean> saveSkill(Map<String, Object> map);
        Observable<Boolean> removeSkill(long skillId);
        Observable<TypeListResponse> queryTypeList(int parentType);
        Observable<VariableResponse> uploadFile(File file);
    }
}
