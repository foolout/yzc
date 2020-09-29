package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    //批量导入
    void add(List<OrderSetting> orderSettingList)throws HealthException;

    //通过月份查询预约设置信息
    List<Map<String,Integer>> getOrderSettingByMonth(String month);

    //基于日历的预约设置
    void editNumberByDate(OrderSetting orderSetting)throws HealthException;
}
