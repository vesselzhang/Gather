package com.vessel.gather.module.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.vessel.gather.R;
import com.vessel.gather.app.base.MySupportFragment;
import com.vessel.gather.app.data.api.service.CommonService;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.app.utils.HttpResultFunc;
import com.vessel.gather.app.utils.HttpResultVoidFunc;
import com.vessel.gather.app.utils.progress.ProgressSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * @author vesselzhang
 * @date 2017/11/29
 */

public class WorkerApplyFragment extends MySupportFragment {

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.et_worker_apply_name)
    EditText etName;
    @BindView(R.id.et_worker_apply_sex)
    Spinner spSex;
//    @BindView(R.id.et_worker_apply_id_card)
//    EditText etIdCard;
//    @BindView(R.id.et_worker_apply_id_card_address)
//    EditText etIdCardAddress;
    @BindView(R.id.et_worker_apply_service_city)
    EditText etServiceCity;
    @BindView(R.id.et_worker_apply_type)
    Spinner spType;


    private AppComponent appComponent;
    private List<TypeListResponse.TypesBean> typesBeanList = new ArrayList<>();
    private List<String> types = new ArrayList<>();
    private List<String> sex = new ArrayList<>();

    public static WorkerApplyFragment newInstance() {
        Bundle args = new Bundle();

        WorkerApplyFragment fragment = new WorkerApplyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_worker_apply, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitleTV.setText("师傅入驻");

        sex.clear();
        sex.add("男");
        sex.add("女");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, sex);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spSex.setAdapter(adapter);

        appComponent.repositoryManager().obtainRetrofitService(CommonService.class)
                .queryTypeList(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<>())
                .subscribe(new ErrorHandleSubscriber<TypeListResponse>(appComponent.rxErrorHandler()) {
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

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, types);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spType.setAdapter(adapter);
                    }
                });
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
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "姓名必填");
                    return;
                }
//                if (TextUtils.isEmpty(etIdCard.getText().toString())) {
//                    ArmsUtils.makeText(getActivity(), "身份证号必填");
//                    return;
//                }
//                if (TextUtils.isEmpty(etIdCardAddress.getText().toString())) {
//                    ArmsUtils.makeText(getActivity(), "身份证证件地址必填");
//                    return;
//                }
                if (TextUtils.isEmpty(etServiceCity.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "服务城市必填");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("type", 0);
                map.put("name", etName.getText().toString());
                map.put("sex", spSex.getSelectedItem());
                map.put("idNumber", 0);
                map.put("address", 0);
                map.put("city", etServiceCity.getText().toString());
                map.put("typeId", typesBeanList.get(spType.getSelectedItemPosition()).getTypeId());
                appComponent.repositoryManager().obtainRetrofitService(CommonService.class)
                        .authorityApply(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new HttpResultVoidFunc())
                        .compose(RxLifecycleUtils.bindToLifecycle(this))
                        .subscribe(new ProgressSubscriber<Boolean>(getActivity(), appComponent.rxErrorHandler()) {
                            @Override
                            public void onNext(Boolean aBoolean) {
                                super.onNext(aBoolean);
                                ArmsUtils.makeText(getActivity(), "已提交申请");
                                pop();
                            }
                        });
                break;
        }
    }
}
