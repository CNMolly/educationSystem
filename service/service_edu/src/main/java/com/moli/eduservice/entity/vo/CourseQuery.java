package com.moli.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {
    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value="课程状态 Published已发布 Draft未发布")
    private String status;
}
