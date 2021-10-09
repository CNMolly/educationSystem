package com.moli.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneLevelSubject {

    private String id;

    private String title;

    //一对多
    private List<TwoLevelSubject> children=new ArrayList<>();
}
