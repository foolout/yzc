package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    //添加检查组
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 添加检查组
        checkGroupDao.add(checkGroup);
        // 获取检查组id
        Integer checkGroupId = checkGroup.getId();
        // 循环遍历选中检查项id，
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                //添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }
        }
    }

    //分页条件查询
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        //有查询条件的处理
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            //拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //查询分页
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(page.getTotal(),page.getResult());
    }

    //通过id获取检查组
    @Override
    public CheckGroup findById(int checkGroupId) {
        return checkGroupDao.findById(checkGroupId);
    }

    //通过检查组id查询选中的检查项id
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    //修改提交
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //先更新检查组
        checkGroupDao.update(checkGroup);
        //删除旧的
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        //建立新的
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(), checkitemId);
            }
        }
    }

    //删除检查组
    @Override
    @Transactional
    public void deleteById(int id) throws HealthException {
        //检查这个检查组是否被使用了
        int count = checkGroupDao.findSetmealCountByCheckGroupId(id);
        if (count > 0){
            //被使用
            throw new HealthException("访检查组已经被套餐使用了，不能删除");
        }
        //未被使用
        //先删除
        checkGroupDao.deleteCheckGroupCheckItem(id);
        checkGroupDao.deleteById(id);

    }

    //查询所有检查组
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
