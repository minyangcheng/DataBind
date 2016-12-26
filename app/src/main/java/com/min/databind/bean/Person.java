package com.min.databind.bean;

import com.databind.library.CheckMode;
import com.databind.library.ConvertMode;
import com.databind.library.annotation.FieldCheck;
import com.databind.library.annotation.FieldConvert;

/**
 * Created by minyangcheng on 2016/11/11.
 */
public class Person {

    @FieldCheck(order = 0, msg = "name can not be empty",checkFlows=CheckMode.EMPTY)
    public String name;

    @FieldConvert(ConvertMode.ITEM)
    @FieldCheck(order = 1, msg = "age can not be empty",checkFlows=CheckMode.EMPTY)
    public byte age;

    @FieldCheck(order = 2,msg={"phone can not be empty","phone is no right"},checkFlows = {CheckMode.EMPTY,CheckMode.PHONE})
    public String phone;

    @FieldCheck(order = 3, msg = "address can not be empty",checkFlows=CheckMode.EMPTY)
    public String address;

    public String nickName;  //此字段不做校验和转换

    @FieldConvert(ConvertMode.SEX)  //转换
    @FieldCheck(order = 4, msg = "sex can not be empty",checkFlows=CheckMode.EMPTY)  //校验
    public int sex;  //1男 2女

    public String remark;

}
