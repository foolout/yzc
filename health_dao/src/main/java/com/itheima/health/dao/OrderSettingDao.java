package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {

    //通过日期来查询预约设置
    OrderSetting findByOrderDate(Date orderDate);

    //更新可预约数量
    void updateNumber(OrderSetting orderSetting);

    //添加预约设置
    void add(OrderSetting orderSetting);

    //通过月份查询预约设置信息
    List<Map<String, Integer>> getOrderSettingBetween(@Param("startDate") String startDate,@Param("endDate") String endDate);
}
