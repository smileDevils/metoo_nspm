package com.cloud.tv.entity;

import com.cloud.tv.core.domain.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Access;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Invisible extends IdEntity {

    @ApiModelProperty("不可见字符名称")
    private String name;
    @ApiModelProperty("不可见字符使用状态")
    private Integer status;
}
