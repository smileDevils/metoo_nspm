package com.cloud.tv.core.manager.integrated.monitor;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.tv.core.service.IGroupService;
import com.cloud.tv.core.service.ISysConfigService;
import com.cloud.tv.core.utils.NodeUtil;
import com.cloud.tv.core.utils.ResponseUtil;
import com.cloud.tv.dto.SysWarnMessageModelDto;
import com.cloud.tv.dto.SysXxlChDelLogDto;
import com.cloud.tv.entity.Group;
import com.cloud.tv.entity.SysConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api("系统相关信息接口")
@RestController
@RequestMapping({"/nspm/monitor/server"})
public class TopoSystemInfoController {

    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private NodeUtil nodeUtil;

    @PostMapping({"/sysWarnMessage"})
    @ApiOperation("磁盘告警日志")
    public Object listSysWarnMessageLog(@RequestBody SysWarnMessageModelDto dto) throws Exception {
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        if(token != null) {
            String url = "/topology-monitor/system/xxlCh/GET/sysWarnMessage";
            Object object = this.nodeUtil.postFormDataBody(dto, url, token);
            JSONObject result = JSONObject.parseObject(object.toString());
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.badArgument();
    }

    @PostMapping({"/xxlChDelLog"})
    @ApiOperation("查询clickHouse表删除记录")
    public Object listXxlChDelLog(@RequestBody SysXxlChDelLogDto dto) throws Exception {
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        if(token != null) {
            String url = "/topology-monitor/system/xxlCh/GET/xxlChDelLog";
            Object object = this.nodeUtil.postFormDataBody(dto, url, token);
            JSONObject result = JSONObject.parseObject(object.toString());
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.badArgument();
    }

}
