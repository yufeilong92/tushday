package com.xuechuan.xcedu.mvp.model;

import android.content.Context;

import com.xuechuan.xcedu.mvp.contract.HomeNetContract;
import com.xuechuan.xcedu.mvp.view.RequestResulteView;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.mvp.model
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018.11.27 下午 5:18
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomeNetModel implements HomeNetContract.Model {
    @Override
    public void requestNetAll(Context context, int page, final RequestResulteView resulteView) {
        HomeService service = HomeService.getInstance(context);
        service.requestHomeNetAll(page, new StringCallBackView() {
            @Override
            public void onSuccess(String success) {
                resulteView.success(success);
            }

            @Override
            public void onError(String msg) {
                resulteView.error(msg);
            }
        });


    }
}
