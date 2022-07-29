package com.cloud.tv.dto.zabbix;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@ApiModel("检索触发器")
@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor
public class TriggerDTO extends ParamsDTO {

    private List<Integer> triggerids;
    private List<Integer> groupids;
    private List<Integer> templateids;
    private List<Integer> hostids;
    private List<Integer> itemids;
    private List<Integer> applicationids;
    private List<String> functions;
    private String group;
    private String host;
    private String expandData;
    private Boolean expandDescription;
    private Boolean expandExpression;
    private String selectGroups;
    private String selectItems;
    private String selectHosts;
    private String selectFunctions;
    private String selectDependencies;
    private String selectDiscoveryRule;
    private String selectLastEvent;
    private String filter;
    private Integer limitSelects;

}
