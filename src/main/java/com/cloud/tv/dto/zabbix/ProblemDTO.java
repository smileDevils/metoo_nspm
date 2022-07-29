package com.cloud.tv.dto.zabbix;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("描述")
@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDTO extends ParamsDTO  {

    private String ip;
    private String hostids;
    private Object filter;
    private Integer limit;
}
