package com.moli.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.moli.commonutils.R;
import com.moli.servicebase.exceptionhandler.selfException;
import com.moli.vod.service.VodService;
import com.moli.vod.utils.ConstantVodUtils;
import com.moli.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file){

        String videoId=vodService.uploadAlyVideo(file);
        return R.ok().data("videoId",videoId);
    }

    //删除视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
        try{
            //初始化对象
            DefaultAcsClient client= InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request=new DeleteVideoRequest();
            //向Request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        }catch(Exception e){
            e.printStackTrace();
            throw new selfException(20001,"删除视频失败！");
        }
    }

    //删除多个视频
    @DeleteMapping("delete-more")
    public R deleteMore(@RequestParam("videoList") List<String> videoList){
        vodService.removeMoreAlyVideo(videoList);
        return R.ok();
    }
}
