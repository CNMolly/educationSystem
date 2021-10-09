package com.moli.oss.service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.moli.oss.service.OssService;
import com.moli.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName=ConstantPropertiesUtils.BUCKET_NAME;


    try{
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
        InputStream inputStream = file.getInputStream();

        //获取文件名称
        String fileName = file.getOriginalFilename();
        //文件名前加随机唯一值确保不被覆盖
        String uuid= UUID.randomUUID().toString().replaceAll("-","");
        fileName=uuid+fileName;

        String dataPath=new DateTime().toString("yyyy/MM/dd");

        fileName=dataPath+"/"+fileName;

        ossClient.putObject(bucketName, fileName, inputStream);

// 关闭OSSClient。
        ossClient.shutdown();
        //把上传之后的文件路径返回
        String url="https://"+bucketName+"."+endpoint+"/"+fileName;
        return url;
    }catch(Exception e){
        e.printStackTrace();
        return null;
    }

    }
}
