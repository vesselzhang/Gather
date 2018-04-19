package com.vessel.gather.module.me.di;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.vessel.gather.app.data.entity.NotePadResponse;

import io.reactivex.Observable;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

public interface MemoContract {
    interface View extends IView {
        void hasResult(boolean hasResult);
        void saveSuccess();
    }

    interface Model extends IModel {
        Observable<NotePadResponse> queryNotepad();
        Observable<Boolean> saveNotepad(String title, String time, String content);
        Observable<Boolean> deleteNotepad(String notepadId);
    }
}
