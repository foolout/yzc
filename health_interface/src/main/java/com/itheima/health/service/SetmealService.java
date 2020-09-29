package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    //增加方法
    Integer add(Setmeal setmeal, Integer[] checkgroupIds);

    //分页查询
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    //通过id查询套餐信息
    Setmeal findById(int id);

    //通过id查询选中的检查组ids
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    //修改
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    //删除
    void deleteById(Integer id) throws HealthException;

    //查出数据库中的所有图片
    List<String> findImgs();

    //查询所有套餐
    List<Setmeal> findAll();

    // 查询套餐详情
    Setmeal findDetailById(int id);

}

