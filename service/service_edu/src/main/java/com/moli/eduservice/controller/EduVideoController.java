package com.moli.eduservice.controller;


import com.moli.commonutils.R;
import com.moli.eduservice.client.VodClient;
import com.moli.eduservice.entity.EduChapter;
import com.moli.eduservice.entity.EduVideo;
import com.moli.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author moli
 * @since 2020-11-14
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }
    //删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){

        //根据小节id删除视频id 删除之前进行判断 为空则无需删除
        EduVideo edu = videoService.getById(id);
        String videoSourceId = edu.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeAlyVideo(videoSourceId);
        }

        //最后删除小节
        videoService.removeById(id);
        return R.ok();
    }
    //根据小节id查询
    @GetMapping("getVideoInfo/{id}")
    public R getChapterInfo(@PathVariable String id) {
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("video",eduVideo);
    }
    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

}

