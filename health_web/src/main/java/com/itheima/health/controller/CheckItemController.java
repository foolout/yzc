package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    //新增
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        //调用服务添加
        checkItemService.add(checkItem);
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    //查询 所有检查项
    @GetMapping("/findAll")
    public Result findAll(){
        // 调用服务查询所有的检查项
        List<CheckItem> list = checkItemService.findAll();
        // 封装返回的结果
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    //分页条件查询
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        // 调用服务分页查询
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        // 返回结果
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    //删除
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        //调用业务服务
        //因为有全局异常处理这里不用在try..catch
        checkItemService.deleteById(id);

        //响应结果
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    //通过id查询
    @GetMapping("findById")
    public Result findById(int id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        // 调用服务更新
        checkItemService.update(checkItem);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

}
