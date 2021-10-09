package com.moli.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.moli.servicebase.exceptionhandler.selfException;
import com.moli.vod.service.VodService;
import com.moli.vod.utils.ConstantVodUtils;
import com.moli.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {


    @Override
    public String uploadAlyVideo(MultipartFile file) {
        try{
            //上传视频原始名称
            String fileName=file.getOriginalFilename();

            String title=fileName.substring(0,fileName.lastIndexOf("."));
            //上传文件输入流
            InputStream inputStream=file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId=null;
            if (response.isSuccess()) {
                videoId=response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId=response.getVideoId();
            }
            return videoId;
        }catch(Exception e){

            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void removeMoreAlyVideo(List videoList) {
        try{
            //初始化对象
            DefaultAcsClient client= InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request=new DeleteVideoRequest();
            //转换videolist
            String videoIds= StringUtils.join(videoList.toArray(),",");
            //向Request设置视频id
            request.setVideoIds(videoIds);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        }catch(Exception e){
            e.printStackTrace();
            throw new selfException(20001,"删除视频失败！");
        }
    }
}
