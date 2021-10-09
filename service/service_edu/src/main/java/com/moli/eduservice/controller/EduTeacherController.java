package com.moli.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moli.commonutils.R;
import com.moli.eduservice.entity.EduTeacher;
import com.moli.eduservice.entity.vo.TeacherQuery;
import com.moli.eduservice.service.EduTeacherService;
import com.moli.servicebase.exceptionhandler.selfException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author moli
 * @since 2020-10-27
 */
@Api("讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;
//访问地址：http://localhost:8001/eduservice/teacher/findAll
    @ApiOperation(value = "讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("item",list);
    }

    //逻辑删除
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R delTeacher(@ApiParam(name = "id",value="讲师ID",required = true) @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
           return R.ok();
        }else {
           return R.error();
        }}


    @GetMapping("pageTeacher/{now}/{sum}")
    public R pageListTeacher(@PathVariable long now,@PathVariable long sum) {

        Page<EduTeacher> pageTeacher=new Page<>(now,sum);

        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("row",records);
        }


    //条件查询+分页
    @PostMapping("pageConditionTeacher/{now}/{sum}")
    public R pageConditionTeacher(@PathVariable long now, @PathVariable long sum,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageTeacher=new Page<>(now,sum);
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();


        teacherService.page(pageTeacher,wrapper);

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        //判断是否要拼接条件
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        teacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("row",records);
}

    //添加讲师接口的方法
    @PostMapping("addTcr")
    public R addTcr(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //根据id查询讲师
    @GetMapping("getTcr/{id}")
    public R getTcr(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("Teacher",eduTeacher);

    }

    //修改
    @PostMapping("updateTcr")
    public R updateTcr(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

