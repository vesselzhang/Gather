package com.vessel.gather.module.me.di;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.vessel.gather.app.data.entity.NotePadResponse;
import com.vessel.gather.module.me.adapter.MemoAdapter;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @author vesselzhang
 * @date 2017/11/25
 */

@ActivityScope
public class MemoPresenter extends BasePresenter<MemoContract.Model, MemoContract.View> implements MemoAdapter.OnDeleteClickListener {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    MemoAdapter mAdapter;
    @Inject
    List<NotePadResponse.NotesBean> mList;

    @Inject
    public MemoPresenter(MemoContract.Model model, MemoContract.View rootView) {
        super(model, rootView);
    }

    public void queryNotepad() {
        mModel.queryNotepad().subscribe(
                new ErrorHandleSubscriber<NotePadResponse>(mErrorHandler) {
                    @Override
                    public void onNext(NotePadResponse notePadResponse) {
                        if (notePadResponse.getNotes() == null) {
                            return;
                        }
                        mList.clear();
                        mList.addAll(notePadResponse.getNotes());
                        mAdapter.notifyDataSetChanged();

                        mRootView.hasResult(mList.size() > 0);
                    }
                }
        );
    }

    public void saveNotepad(String title, String time, String content) {
        mModel.saveNotepad(title, time, content).subscribe(
                new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        mRootView.saveSuccess();
                        mRootView.showMessage("保存成功");
                        queryNotepad();
                    }
                }
        );
    }

    private void deleteNotepad(String notepadId) {
        mModel.deleteNotepad(notepadId).subscribe(
                new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        mRootView.showMessage("删除成功");
                        queryNotepad();
                    }
                }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(NotePadResponse.NotesBean notesBean) {
        deleteNotepad(notesBean.getNotepadId());
    }
}