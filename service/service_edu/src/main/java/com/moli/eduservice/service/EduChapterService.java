package com.moli.eduservice.service;

import com.moli.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moli.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author moli
 * @since 2020-11-14
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //删除章节方法
    boolean deleteChapter(String chapterId);

    //根据课程Id删除章节
    void removeChapterByCourseId(String courseId);
}
