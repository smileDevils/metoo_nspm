package com.cloud.tv.core.manager.integrated.expose;

import com.cloud.tv.core.http.HttpTools;
import com.cloud.tv.core.service.ISysConfigService;
import com.cloud.tv.core.utils.NodeUtil;
import com.cloud.tv.core.utils.ResponseUtil;
import com.cloud.tv.dto.ExposureDto;
import com.cloud.tv.dto.TopoNodeDto;
import com.cloud.tv.entity.SysConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/nspm/risk")
@RestController
public class TopoExposeManagerController {

    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private NodeUtil nodeUtil;
    @Autowired
    private HttpTools httpTools;

    @ApiOperation("防御优化-资产主机列表")
    @PostMapping(value = "/api/danger/attackSurface/hostExposureList")
    public Object statisticalInformation(@RequestBody ExposureDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        
        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/risk/api/danger/attackSurface/hostExposureList";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

        @PostMapping(value="/queryNodeHistory")
    public Object queryNodeHistory(@RequestBody TopoNodeDto dto){
       Object result = this.httpTools.get("/topology/node/queryNodeHistory.action", dto);
       return ResponseUtil.ok(result);
    }
}
