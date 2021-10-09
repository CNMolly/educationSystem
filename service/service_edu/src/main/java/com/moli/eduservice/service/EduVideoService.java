package com.moli.eduservice.service;

import com.moli.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author moli
 * @since 2020-11-14
 */
public interface EduVideoService extends IService<EduVideo> {

    //根据课程ID删除小节
    void removeVideoByCourseId(String courseId);
}
