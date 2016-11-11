package com.min.databind.bind.annotation;

import com.min.databind.bind.ConvertMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by minyangcheng on 2016/11/11.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldConvert {

    ConvertMode value();

}
