package com.databind.library;

/**
 * Created by minyangcheng on 2016/11/11.
 */
public enum ConvertMode {

    SEX{
        @Override
        public String toView(Object obj) {
            String str=null;
            int value=(Integer)obj;
            if(value==1){
                str="男";
            }else{
                str="女";
            }
            return str;
        }

        @Override
        public String toValue(String str) {
            String s=null;
            if(str.equals("男")){
                s="1";
            }else{
                s="2";
            }
            return s;
        }
    },
    ITEM{
        @Override
        public String toView(Object obj) {
            String str=null;
            str="我"+obj.toString()+"岁了";
            return str;
        }

        @Override
        public String toValue(String str) {
            str=str.replaceAll("\\D+","");
            return str;
        }
    };

    ConvertMode() {
    }

    public abstract String toView(Object obj);

    public abstract String toValue(String str);

}
