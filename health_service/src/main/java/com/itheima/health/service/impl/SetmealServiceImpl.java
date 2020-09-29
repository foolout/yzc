package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    //添加套餐
    @Override
    @Transactional
    public Integer add(Setmeal setmeal, Integer[] checkgroupIds) {
        // 添加套餐信息
        setmealDao.add(setmeal);
        // 获取套餐的id
        Integer setmealId = setmeal.getId();
        // 添加套餐与检查组的关系
        if(null != checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId, checkgroupId);
            }
        }
        return setmealId;
    }

    //分页查询
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //查询条件
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            //模糊查询
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<Setmeal> page = setmealDao.findCondition(queryPageBean.getQueryString());
        return new PageResult<Setmeal>(page.getTotal(),page.getResult());
    }

    //通过id查询套餐信息
    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }


    //通过id查询选中的检查组ids
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    //修改
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        // 先更新套餐
        setmealDao.update(setmeal);
        // 删除旧关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        // 添加新关系
        if(null != checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(), checkgroupId);
            }
        }
    }

    //删除
    @Override
    @Transactional
    public void deleteById(Integer id) throws HealthException {
        //是否存在订单
        int count = setmealDao.findOrderCountBySetmealId(id);
        if (count > 0){
            //已使用套餐
            throw new HealthException("已经有订单使用了这个套餐，不能删除！");
        }
        //先删除与检查组的关系
        setmealDao.deleteSetmealCheckGroup(id);
        //再删除套餐
        setmealDao.deleteById(id);
    }

    //查出数据库中的所有图片
    @Override
    public List<String> findImgs() {
        return setmealDao.findImgs();
    }

    // 查询套餐详情
    @Override
    public Setmeal findDetailById(int id) {
        return setmealDao.findDetailById(id);
    }

    //查询所有套餐
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

}
