package com.cloud.tv.core.manager.view.action;

import com.cloud.tv.core.service.ISysConfigService;
import com.cloud.tv.core.utils.ResponseUtil;
import com.cloud.tv.entity.SysConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api("系统配置")
@RestController
@RequestMapping("/web/config")
public class SysConfigViewController {

    @Autowired
    private ISysConfigService configService;

    @ApiOperation("系统配置")
    @RequestMapping("/detail")
    public Object detail(){
        Map data = new HashMap();
        SysConfig configs = this.configService.findSysConfigList();
        data.put("domain", configs.getDomain());
        data.put("title", configs.getTitle());

        return ResponseUtil.ok(data);
    }
}
