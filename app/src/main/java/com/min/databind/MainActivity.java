package com.min.databind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.databind.library.DataBind;
import com.databind.library.util.L;
import com.min.databind.bean.Person;
import com.min.databind.util.GsonUtil;
import com.min.databind.util.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity_TAG";

    @Bind(R.id.content)
    ViewGroup mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.fillData)
    void clickBtnFillData() {
        Person person=new Person();
        person.name="minyangcheng";
        person.age=26;
        person.address="九江";
        person.nickName="yangcheng";
        person.sex=1;
        person.phone="15257178923";;
        person.remark="这是一条备注";
        //向控件赋值
        DataBind.fillData(person, mVp);
    }

    @OnClick(R.id.getData)
    void clickBtngetData() {
        //从控件取值
        Person person=DataBind.getData(Person.class,mVp);
        if(person!=null){
            String personJsonStr=GsonUtil.toPrettyJson(person);
            ToastUtils.showShortToast(this,personJsonStr);
            L.d(TAG,"person=%s", personJsonStr);
        }
    }

    @OnClick(R.id.check)
    void clickBtncheck() {
        //控件上的输入项校验
        String str=DataBind.check(Person.class,mVp);
        if(TextUtils.isEmpty(str)){
            ToastUtils.showShortToast(this,"check is pass");
            L.d(TAG,"check is pass");
        }else{
            ToastUtils.showShortToast(this,str);
            L.d(TAG,"find a field is empty : msg=%s", str);
        }
    }

}
