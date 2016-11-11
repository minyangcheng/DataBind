package com.min.databind.bean;

import com.min.databind.bind.CheckMode;
import com.min.databind.bind.ConvertMode;
import com.min.databind.bind.annotation.FieldCheck;
import com.min.databind.bind.annotation.FieldConvert;

/**
 * Created by minyangcheng on 2016/11/11.
 */
public class Person {

    @FieldCheck(order = 0, msg = "age can not be empty")
    public String name;

    @FieldConvert(ConvertMode.ITEM)
    @FieldCheck(order = 1, msg = "age can not be empty")
    public byte age;

    @FieldCheck(order = 1, msg = "age can not be empty" , checkFlows = {CheckMode.EMPTY,CheckMode.PHONE})
    public String phone;

    @FieldCheck(order = 2, msg = "address can not be empty")
    public String address;

    @FieldCheck(order = 3, msg = "nickName can not be empty")
    public String nickName;

    @FieldConvert(ConvertMode.SEX)
    @FieldCheck(order = 4, msg = "nickName can not be empty")
    public int sex;  //1男 2女

}
