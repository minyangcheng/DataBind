# DataBind

写这个数据绑定库的灵感源于公司的项目开发遇到的问题：app中有些页面有许多需要展示、输入的字段，如果每个字段都去手动复制、手动取值，这就意味着增加大量的冗余代码，也增加了开发的工作量。解决这个问题其实很简单，只需要数据字段和控价进行一一绑定即可。

## 实现思路

### 基本原理

1. 将对象上的字段名与控件的tag相互对应。
2. 用反射取到对象中的每个字段，然后通过字段名去寻找到对应的控件，即可进行赋值和取值。
3. 通过在字段上添加注解就可以实现判断字段是否为空的操作。

### 对外开放的功能

1. 将对象上的数据填充到控件上，在填充可以进行转化，比如：1对应男
2. 将控件上的数据填充到对象中，在填充钱可以进行自动转化，比如：男对应1
3. 对控件上的数据进行判空等检查操作。

## 使用代码

javabean对象：
```java
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
```

layout布局:
```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.min.databind.MainActivity"
    android:orientation="vertical"
    android:id="@+id/content">

    <Button
        android:id="@+id/fillData"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="fillData"/>

    <Button
        android:id="@+id/getData"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="getData"/>

    <Button
        android:id="@+id/check"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="check"/>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="name" />
        <EditText
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="right"
            android:tag="name"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="age" />
        <EditText
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="right"
            android:tag="age"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="address" />
        <EditText
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="right"
            android:tag="address"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <TextView
            android:tag="tt"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="nickName" />
        <EditText
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="right"
            android:tag="nickName"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <TextView
            android:tag="tt"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="sex" />
        <EditText
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="right"
            android:tag="sex"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <TextView
            android:tag="tt"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="phone" />
        <EditText
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="right"
            android:tag="phone"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <TextView
            android:tag="tt"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="remark" />
        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:gravity="right"
            android:tag="remark"/>
    </LinearLayout>

</LinearLayout>

```

activity中的操作方法：
```java
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
```

## 总结

实现此库一个核心的思想就是数据和控件相互绑定，然后我们在此基础上添加一些平时开发需要用到的一些功能，主要用到了注解、反射等知识。
