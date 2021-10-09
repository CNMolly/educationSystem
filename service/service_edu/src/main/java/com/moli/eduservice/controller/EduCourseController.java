package com.moli.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moli.commonutils.R;
import com.moli.eduservice.entity.EduCourse;
import com.moli.eduservice.entity.vo.CourseInfoVo;
import com.moli.eduservice.entity.vo.CoursePublishVo;
import com.moli.eduservice.entity.vo.CourseQuery;
import com.moli.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author moli
 * @since 2020-11-14
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //课程列表 TODO
    @ApiOperation(value = "课程列表")
    @GetMapping("findCourse")
    public R getCourseList(){
        List<EduCourse> list=courseService.list(null);
        return R.ok().data("list",list);
    }

    //添加课程基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id=courseService.saveCourseInfo(courseInfoVo);


        return R.ok().data("courseId",id);
    }

    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo=courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo=courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程发布 修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Published");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    @GetMapping("pageCourse/{now}/{sum}")
    public R pageListCourse(@PathVariable long now,@PathVariable long sum) {

        //Page<EduCourse> pageCourse=new Page<>(now,sum);
        Page<EduCourse> pageCourse=new Page<>(now,sum);
        courseService.page(pageCourse,null);

        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> records = pageCourse.getRecords();
        return R.ok().data("total",total).data("row",records);
    }


    //条件查询+分页
    @PostMapping("pageConditionCourse/{now}/{sum}")
    public R pageConditionCourse(@PathVariable long now, @PathVariable long sum,
                                  @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> pageCourse=new Page<>(now,sum);
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();


        courseService.page(pageCourse,wrapper);

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        //判断是否要拼接条件
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        courseService.page(pageCourse,wrapper);

        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> records = pageCourse.getRecords();
        return R.ok().data("total",total).data("row",records);
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }

}

