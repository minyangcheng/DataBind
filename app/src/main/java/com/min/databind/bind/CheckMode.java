package com.min.databind.bind;

import com.min.databind.util.StringUtils;

/**
 * Created by minyangcheng on 2016/11/11.
 */
public enum CheckMode {

    EMPTY (){
        @Override
        public boolean check(String str) {
            return !StringUtils.isEmpty(str);
        }
    },
    PHONE{
        @Override
        public boolean check(String str) {
            return StringUtils.isPhone(str);
        }
    };

    CheckMode() {
    }

    public abstract boolean check(String str);

}
