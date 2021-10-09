package com.moli.eduservice.service;

import com.moli.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moli.eduservice.entity.subject.OneLevelSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author moli
 * @since 2020-11-09
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneLevelSubject> getAllOneTwoLevelSubject();
}
