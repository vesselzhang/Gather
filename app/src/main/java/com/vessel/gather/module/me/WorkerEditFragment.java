package com.vessel.gather.module.me;

import android.content.Intent;
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
import com.vessel.gather.app.data.entity.ArtisanInfoResponse;
import com.vessel.gather.app.data.entity.TypeListResponse;
import com.vessel.gather.app.utils.HttpResultFunc;
import com.vessel.gather.app.utils.HttpResultVoidFunc;
import com.vessel.gather.app.utils.progress.ProgressSubscriber;
import com.zaaach.citypicker.CityPickerActivity;

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

public class WorkerEditFragment extends MySupportFragment {
    private static final int REQUEST_CODE_PICK_CITY = 0;

    @BindView(R.id.tv_title)
    TextView mTitleTV;
    @BindView(R.id.et_worker_edit_name)
    EditText etName;
    @BindView(R.id.et_worker_edit_service_city)
    TextView etServiceCity;
    @BindView(R.id.et_worker_edit_address)
    EditText etAddress;
    @BindView(R.id.et_worker_edit_type)
    Spinner spType;


    private AppComponent appComponent;
    private ArtisanInfoResponse artisanInfo;
    private List<TypeListResponse.TypesBean> typesBeanList = new ArrayList<>();
    private List<String> types = new ArrayList<>();

    public static WorkerEditFragment newInstance(ArtisanInfoResponse artisanInfoResponse) {
        Bundle args = new Bundle();
        args.putSerializable("info", artisanInfoResponse);

        WorkerEditFragment fragment = new WorkerEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment_worker_edit, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        artisanInfo = (ArtisanInfoResponse) getArguments().getSerializable("info");
        mTitleTV.setText("师傅信息");

        appComponent.repositoryManager().obtainRetrofitService(CommonService.class)
                .queryTypeList(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<>())
                .subscribe(new ErrorHandleSubscriber<TypeListResponse>(appComponent.rxErrorHandler()) {
                    @Override
                    public void onNext(TypeListResponse typeListResponse) {
                        int position = -1;
                        if (typeListResponse.getTypes().size() > 0) {
                            typesBeanList.clear();
                            types.clear();
                            typesBeanList.addAll(typeListResponse.getTypes());
                            for (int i = 0; i < typesBeanList.size(); i++) {
                                types.add(typesBeanList.get(i).getTypeName());
                                if (artisanInfo != null && typesBeanList.get(i).getTypeId() == artisanInfo.getTypeId()) {
                                    position = i;
                                }
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, types);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spType.setAdapter(adapter);
                        if (position != -1) {
                            spType.setSelection(position);
                        }
                    }
                });
        etName.setText(artisanInfo.getRealName());
        etServiceCity.setText(artisanInfo.getCity());
        etAddress.setText(artisanInfo.getAddress());
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.et_worker_edit_submit, R.id.iv_left, R.id.et_worker_edit_service_city})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                pop();
                break;
            case R.id.et_worker_edit_service_city:
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class), REQUEST_CODE_PICK_CITY);
                break;
            case R.id.et_worker_edit_submit:
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "姓名必填");
                    return;
                }
                if (TextUtils.isEmpty(etServiceCity.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "服务城市必填");
                    return;
                }
                if (TextUtils.isEmpty(etAddress.getText().toString())) {
                    ArmsUtils.makeText(getActivity(), "常驻地址必填");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("realName", etName.getText().toString());
                map.put("address", etAddress.getText().toString());
                map.put("city", etServiceCity.getText().toString());
                map.put("typeId", typesBeanList.get(spType.getSelectedItemPosition()).getTypeId());
                appComponent.repositoryManager().obtainRetrofitService(CommonService.class)
                        .updateArtisan(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new HttpResultVoidFunc())
                        .compose(RxLifecycleUtils.bindToLifecycle(this))
                        .subscribe(new ProgressSubscriber<Boolean>(getActivity(), appComponent.rxErrorHandler()) {
                            @Override
                            public void onNext(Boolean aBoolean) {
                                super.onNext(aBoolean);
                                ArmsUtils.makeText(getActivity(), "修改成功");
                                pop();
                            }
                        });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                etServiceCity.setText(city);
            }
        }
    }
}
