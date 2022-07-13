package com.cloud.tv.core.manager.integrated.node;

import com.cloud.tv.core.service.ISysConfigService;
import com.cloud.tv.core.utils.NodeUtil;
import com.cloud.tv.core.utils.ResponseUtil;
import com.cloud.tv.dto.BranchDto;
import com.cloud.tv.entity.SysConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/nspm/branch")
@RestController
public class TopoNspmBranchManagerAction {

    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private NodeUtil nodeUtil;

    @ApiOperation("列表")
    @PostMapping(value = "/findBranchTree")
    public Object findBranchTree(@RequestBody(required = false) BranchDto dto) {
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        
        String token = sysConfig.getNspmToken();
        if (token != null) {
            String url = "topology/branch/findBranchTree/";
            Object result = this.nodeUtil.postBody(null, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("获取分组")
    @PostMapping(value = "/getBranch")
    public Object getBranch(@RequestBody(required = false) BranchDto dto) {
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        
        String token = sysConfig.getNspmToken();
        if (token != null) {
            String url = "/topology/branch/getBranch/?id=" + dto.getId();
            Object result = this.nodeUtil.postBody(null, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("更新")
    @PostMapping(value = "/update")
    public Object update(@RequestBody(required = false) BranchDto dto) {
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        
        String token = sysConfig.getNspmToken();
        if (token != null) {
            String url = "/topology/branch/update?branchName=" + dto.getBranchName() + "&branchDesc=" + dto.getBranchDesc() + "&id" + dto.getId();
            Object result = this.nodeUtil.postBody(null, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("添加")
    @PostMapping(value = "/add")
    public Object add(@RequestBody(required = false) BranchDto dto) {
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        
        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology/branch/add?branchName=" + dto.getBranchName() + "&branchDesc=" + dto.getBranchDesc() + "&parentLevel" + dto.getParentLevel();
            Object result = this.nodeUtil.postBody(null, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("删除")
    @PostMapping(value = "/delete")
    public Object delete(@RequestBody(required = false) BranchDto dto) {
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        
        String token = sysConfig.getNspmToken();
        if (token != null) {
            String url = "/topology/branch/delete/?id=" + dto.getId();
            Object result = this.nodeUtil.postBody(null, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

}
