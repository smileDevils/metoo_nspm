package com.cloud.tv.entity;


import com.cloud.tv.core.domain.IdEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("系统阈值")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Threshold  extends IdEntity {

    private Integer cpu;
    private Integer memory;
    private Integer flow;

}
