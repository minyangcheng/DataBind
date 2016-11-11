package com.min.databind.bind;

/**
 * Created by minyangcheng on 2016/11/11.
 */
public class CheckFlowUtil {

    public static boolean check(String str ,CheckMode[] checkModes){
        for(CheckMode checkMode : checkModes){
            if(!checkMode.check(str)){
                return false;
            }
        }
        return true;
    }

}
