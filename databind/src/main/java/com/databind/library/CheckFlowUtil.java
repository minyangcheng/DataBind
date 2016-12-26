package com.databind.library;

/**
 * Created by minyangcheng on 2016/11/11.
 */
public class CheckFlowUtil {

    /**
     * 返回正数则为检查不通过
     * @param str
     * @param checkModes
     * @return
     */
    public static int check(String str ,CheckMode[] checkModes){
        CheckMode checkMode=null;
        for(int i=0;i<checkModes.length;i++){
            checkMode=checkModes[i];
            if(!checkMode.check(str)){
                return i;
            }
        }
        return -1;
    }

}
