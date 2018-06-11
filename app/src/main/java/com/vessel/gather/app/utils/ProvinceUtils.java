package com.vessel.gather.app.utils;

import android.app.Application;

import com.addresspicker.huichao.addresspickerlibrary.InitAreaTask;
import com.addresspicker.huichao.addresspickerlibrary.address.Province;
import com.alibaba.android.arouter.exception.InitException;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;

/**
 * @author vesselzhang
 * @date 2018/6/11
 */
public class ProvinceUtils {
    private volatile static boolean hasInit = false;
    private volatile static ProvinceUtils instance = null;
    private volatile static ArrayList<Province> provinces = new ArrayList<>();

    public static void init(Application application) {
        if (!hasInit) {
            InitAreaTask initAreaTask = new InitAreaTask(application, provinces);
            initAreaTask.execute(0);
            initAreaTask.setOnLoadingAddressListener(new InitAreaTask.onLoadingAddressListener() {
                @Override
                public void onLoadFinished() {
                    hasInit = true;
                }

                @Override
                public void onLoading() {

                }
            });
        }
    }

    public static ProvinceUtils getInstance() {
        if (!hasInit) {
            throw new InitException("Province::Init::Invoke init(context) first!");
        } else {
            if (instance == null) {
                synchronized (ARouter.class) {
                    if (instance == null) {
                        instance = new ProvinceUtils();
                    }
                }
            }
            return instance;
        }
    }

    public static ArrayList<Province> getProvinces() {
        return provinces;
    }
}
