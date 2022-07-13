package com.cloud.tv.core.manager.integrated.policy;

import com.cloud.tv.core.service.ISysConfigService;
import com.cloud.tv.core.utils.NodeUtil;
import com.cloud.tv.core.utils.ResponseUtil;
import com.cloud.tv.entity.SysConfig;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nspm/patrol")
public class TopoPatrolManagerController {

    @Autowired
    private NodeUtil nodeUtil;
    @Autowired
    private ISysConfigService sysConfigService;

    @GetMapping("/downloadFile")
    public Object downloadFile(String url){
        if(!StringUtil.isEmpty(url)){
            url = "patrol/" + url;
            SysConfig sysConfig = this.sysConfigService.findSysConfigList();
            String token = sysConfig.getNspmToken();
            return this.nodeUtil.downloadPatrol(null, url, token);
        }
       return ResponseUtil.badArgument();
    }

}
