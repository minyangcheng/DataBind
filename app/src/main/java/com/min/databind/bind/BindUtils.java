package com.min.databind.bind;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.min.databind.bind.annotation.FieldCheck;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by minyangcheng on 2016/11/11.
 */
public class BindUtils {

    public static List<Field> getRightFields(Class clazz){
        ArrayList<Field> fieldList=new ArrayList<>();

        Field[] fields=clazz.getFields();
        int modifier=0;
        for(Field field : fields){
            field.setAccessible(true);
            modifier=field.getModifiers();
            if(!Modifier.isStatic(modifier)){
                //静态修饰的变量不做处理
                fieldList.add(field);
            }
        }
        return fieldList;
    }

    public static Map<Integer,Field> getFieldMapOrderByAnnotation(Class clazz){
        Map<Integer,Field> fieldTreeMap=new TreeMap<>();
        List<Field> fields=BindUtils.getRightFields(clazz);
        FieldCheck fieldCheck;
        for(Field f : fields){
            f.setAccessible(true);
            fieldCheck =f.getAnnotation(FieldCheck.class);
            if(fieldCheck !=null){
                fieldTreeMap.put(fieldCheck.order(),f);
            }
        }
        return fieldTreeMap;
    }

    public static View findViewByTag(ViewGroup vp ,String tag){
        return vp.findViewWithTag(tag);
    }

    public static void fillDataToView(View view ,Object value){
        if(view==null || value ==null){
            return;
        }
        if(view instanceof EditText){
            EditText et= (EditText) view;
            et.setText(value.toString());
        }
    }

    public static String getDataFromView(View view){
        if(view==null){
            return null;
        }
        if(view instanceof EditText){
            EditText et= (EditText) view;
            return et.getText().toString();
        }
        return null;
    }

    public static Object getValueObjByFieldType(String viewValue , Class clazz){
        if(TextUtils.isEmpty(viewValue)||clazz==null){
            return null;
        }
        Object obj=null;
        try {
            if(clazz.isPrimitive()){
                String className=clazz.getCanonicalName();
                if(className.equals("int")||className.equals(Integer.class.getCanonicalName())){
                    obj=Integer.parseInt(viewValue);
                }else if(className.equals("long")||className.equals(Long.class.getCanonicalName())){
                    obj=Long.parseLong(viewValue);
                }else if(className.equals("byte")||className.equals(Byte.class.getCanonicalName())){
                    obj=Byte.parseByte(viewValue);
                }else if(className.equals("short")||className.equals(Short.class.getCanonicalName())){
                    obj=Short.parseShort(viewValue);
                }
            }else{
                obj=viewValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}
