package com.moli.eduservice.controller;


import com.moli.commonutils.R;
import com.moli.eduservice.entity.subject.OneLevelSubject;
import com.moli.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author moli
 * @since 2020-11-09
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }


    //课程分类列表
    @GetMapping("getSubject")
    public R getSubject(){

        List<OneLevelSubject> list=subjectService.getAllOneTwoLevelSubject();
        return R.ok().data("list",list);
    }
}


