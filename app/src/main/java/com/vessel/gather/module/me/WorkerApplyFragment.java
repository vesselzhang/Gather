package com.vessel.gather.module.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author vesselzhang
 * @date 2017/11/29
 */

public class WorkerApplyFragment extends MySupportFragment {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.et_worker_apply_name)
    TextView tvName;
    @BindView(R.id.et_worker_apply_sex)
    TextView tvSex;
    @BindView(R.id.et_worker_apply_id_card)
    TextView tvIdCard;
    @BindView(R.id.et_worker_apply_id_card_address)
    TextView tvIdCardAddress;
    @BindView(R.id.et_worker_apply_service_city)
    TextView tvServiceCity;
    @BindView(R.id.et_worker_apply_type)
    TextView tvType;

    public static WorkerApplyFragment newInstance() {
        Bundle args = new Bundle();

        WorkerApplyFragment fragment = new WorkerApplyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_worker_apply, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("师傅入驻");
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.et_worker_apply_submit, R.id.iv_left})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                pop();
                break;
            case R.id.et_worker_apply_submit:
                if (TextUtils.isEmpty(tvName.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "姓名必填");
                    return;
                }
                if (TextUtils.isEmpty(tvIdCard.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "身份证号必填");
                    return;
                }
                if (TextUtils.isEmpty(tvIdCardAddress.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "身份证证件地址必填");
                    return;
                }
                if (TextUtils.isEmpty(tvServiceCity.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "服务城市必填");
                    return;
                }
                if (TextUtils.isEmpty(tvType.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "类型必填");
                    return;
                }
                break;
        }
    }
}
