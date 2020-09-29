package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    //添加检查
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    //分页条件查询
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    //通过id获取检查组
    CheckGroup findById(int checkGroupId);

    //通过检查组id查询选中的检查项id
    List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);

    //修改检查组
    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    //删除检查组
    void deleteById(int id);

    //查询所有检查组
    List<CheckGroup> findAll();
}
