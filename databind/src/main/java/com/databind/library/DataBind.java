package com.databind.library;

import android.view.View;
import android.view.ViewGroup;

import com.databind.library.annotation.FieldCheck;
import com.databind.library.annotation.FieldConvert;
import com.databind.library.util.L;
import com.databind.library.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by minyangcheng on 2016/11/11.
 */
public class DataBind {

    private static final String TAG=DataBind.class.getSimpleName();

    /**
     * 向控件上赋值
     * @param obj
     * @param vp
     */
    public static void fillData(Object obj , ViewGroup vp){
        if(obj==null || vp==null){
            return;
        }
        try {
            Class clazz=obj.getClass();
            List<Field> fields=BindUtils.getRightFields(clazz);
            Class fieldType;
            String fieldName;
            Object fieldValue;
            View targetView;
            FieldConvert fieldFieldConvert;
            for(Field f : fields){
                f.setAccessible(true);
                fieldType=f.getType();
                fieldName=f.getName();
                fieldFieldConvert =f.getAnnotation(FieldConvert.class);
                fieldValue=f.get(obj);
                targetView=BindUtils.findViewByTag(vp,fieldName);
                if(targetView!=null){
                    if(fieldFieldConvert !=null){
                        fieldValue= fieldFieldConvert.value().toView(fieldValue);
                    }
                    BindUtils.fillDataToView(targetView,fieldValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从控件上获取数据
     * @param clazz
     * @param vp
     * @param <T>
     * @return
     */
    public static <T> T getData(Class<T> clazz , ViewGroup vp){
        if(clazz==null || vp==null){
            return null;
        }
        try {
            T t=clazz.newInstance();

            List<Field> fields=BindUtils.getRightFields(clazz);
            Class fieldType;
            String fieldName;
            View targetView;
            String viewValue;
            Object fieldValue;
            FieldConvert fieldFieldConvert;
            for(Field f : fields){
                f.setAccessible(true);
                fieldType=f.getType();
                fieldName=f.getName();
                fieldFieldConvert =f.getAnnotation(FieldConvert.class);
                targetView=BindUtils.findViewByTag(vp,fieldName);
                if(targetView!=null){
                    viewValue=BindUtils.getDataFromView(targetView);
                    if(fieldFieldConvert !=null){
                        viewValue= fieldFieldConvert.value().toValue(viewValue);
                    }
                    fieldValue=BindUtils.getValueObjByFieldType(viewValue, fieldType);
                    if(fieldValue!=null){
                        f.set(t, fieldValue);
                    }
                }
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验，返回空则检验通过
     * @param clazz
     * @param vp
     * @return
     */
    public static String check(Class clazz , ViewGroup vp){
        if(clazz==null || vp==null){
            return null;
        }
        try {
            Map<Integer,Field> fieldTreeMap=BindUtils.getFieldMapOrderByAnnotation(clazz);
            Collection<Field> fields=fieldTreeMap.values();

            String fieldName;
            View targetView;
            FieldCheck fieldCheck;
            String viewValue;
            CheckMode[] checkModes;
            int msgIndex=-1;
            String alertMsg=null;
            for(Field f : fields){
                f.setAccessible(true);
                fieldName=f.getName();
                fieldCheck=f.getAnnotation(FieldCheck.class);
                checkModes=fieldCheck.checkFlows();
                if(fieldCheck !=null){
                    targetView=BindUtils.findViewByTag(vp,fieldName);
                    viewValue=BindUtils.getDataFromView(targetView);
                    L.d(TAG,"msg length=%s , checkMode length=%s , %s",fieldCheck.msg().length,checkModes.length,fieldName);
                    msgIndex=CheckFlowUtil.check(viewValue,checkModes);
                    if(msgIndex>=0){
                        if(msgIndex>=fieldCheck.msg().length){
                            msgIndex=0;
                        }
                        alertMsg=fieldCheck.msg()[msgIndex];
                        if(StringUtils.isEmpty(alertMsg)){
                            alertMsg="输入信息不合法";
                        }
                        return alertMsg;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
