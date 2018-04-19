package com.vessel.gather.module.me.adapter;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.vessel.gather.R;
import com.vessel.gather.app.data.entity.NotePadResponse;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

public class MemoAdapter extends DefaultAdapter<NotePadResponse.NotesBean> {

    private OnDeleteClickListener onDeleteClickListener;

    public MemoAdapter(List<NotePadResponse.NotesBean> infos) {
        super(infos);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public BaseHolder<NotePadResponse.NotesBean> getHolder(View v, int viewType) {
        return new MemoHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_me_memo;
    }

    class MemoHolder extends BaseHolder<NotePadResponse.NotesBean> {

        @BindView(R.id.item_memo_title) TextView mTitle;
        @BindView(R.id.item_memo_time) TextView mTime;
        @BindView(R.id.item_memo_content) TextView mContent;
        @BindView(R.id.item_memo_delete) TextView mDelete;

        public MemoHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(NotePadResponse.NotesBean data, int position) {
            Observable.just(data).subscribe(address -> {
                mTitle.setText(address.getTitle());
                mTime.setText(address.getTime());
                mContent.setText(address.getContent());
                mDelete.setOnClickListener(view -> {
                    if (onDeleteClickListener != null) {
                        onDeleteClickListener.onItemClick(data);
                    }
                });
            });
        }
    }

    public interface OnDeleteClickListener {
        void onItemClick(NotePadResponse.NotesBean notesBean);
    }
}
