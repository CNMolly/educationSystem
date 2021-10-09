package com.moli.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moli.eduservice.entity.EduSubject;
import com.moli.eduservice.entity.excel.SubjectData;
import com.moli.eduservice.entity.subject.OneLevelSubject;
import com.moli.eduservice.entity.subject.TwoLevelSubject;
import com.moli.eduservice.listener.SubjectExcelListener;
import com.moli.eduservice.mapper.EduSubjectMapper;
import com.moli.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author moli
 * @since 2020-11-09
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //课程分类列表
    @Override
    public List<OneLevelSubject> getAllOneTwoLevelSubject() {

        //查询一级分类
        QueryWrapper<EduSubject> wrapperOneLevel=new QueryWrapper<>();
        wrapperOneLevel.eq("parent_id","0");
        List<EduSubject> oneLevelSubjectList = baseMapper.selectList(wrapperOneLevel);

        //查询二级分类
        QueryWrapper<EduSubject> wrapperTwoLevel=new QueryWrapper<>();
        wrapperTwoLevel.ne("parent_id","0");
        List<EduSubject> twoLevelSubjectList = baseMapper.selectList(wrapperTwoLevel);

        //list集合，存储最终封装数据
        List<OneLevelSubject> finalSubjectList=new ArrayList<>();

        //封装一级
        for (int i = 0; i < oneLevelSubjectList.size(); i++) {
            EduSubject eduSubject = oneLevelSubjectList.get(i);

            //遍历取值后嵌套封装 一级封进finalSubjectList 二级封进一级
            OneLevelSubject oneLevelSubject=new OneLevelSubject();

            BeanUtils.copyProperties(eduSubject,oneLevelSubject);
            finalSubjectList.add(oneLevelSubject);

            //封装二级分类
            List<TwoLevelSubject> twoFinalSubjectList=new ArrayList<>();

            for (int j = 0; j <twoLevelSubjectList.size(); j++) {
                //获取二级分类
                EduSubject twoSubject=twoLevelSubjectList.get(j);
                //判断parentid与id
                if(twoSubject.getParentId().equals(eduSubject.getId())){
                    TwoLevelSubject twoLevelSubject=new TwoLevelSubject();
                    BeanUtils.copyProperties(twoSubject,twoLevelSubject);
                    twoFinalSubjectList.add(twoLevelSubject);
                }
            }

            oneLevelSubject.setChildren(twoFinalSubjectList);

        }

        //封装二级

        return finalSubjectList;
    }
}

