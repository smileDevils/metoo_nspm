package com.cloud.tv.core.manager.integrated.node;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.tv.core.manager.admin.tools.ShiroUserHolder;
import com.cloud.tv.core.service.ISysConfigService;
import com.cloud.tv.core.service.IUserService;
import com.cloud.tv.core.utils.NodeUtil;
import com.cloud.tv.core.utils.ResponseUtil;
import com.cloud.tv.dto.SceneDto;
import com.cloud.tv.entity.SysConfig;
import com.cloud.tv.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//@RequiresPermissions("LK:SCENE:MANAGER")
@Api("场景设置")
@RequestMapping("/nspm/scene")
@RestController
public class TopoSceneSettingManagerController {

    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NodeUtil nodeUtil;
    private SceneDto dto;
    @Autowired
    private IUserService userService;

    @ApiOperation("场景列表")
    @PostMapping(value = "/push/api/disposal/scenes/pageList")
    public Object list(@RequestBody(required = false) SceneDto dto) {
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/push/api/disposal/scenes/pageList";
            Object result = this.nodeUtil.postBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("场景新建")
    @PostMapping(value = "/push/api/disposal/scenes/edit")
    public Object edit(@RequestBody(required = false) SceneDto dto) {
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/push/api/disposal/scenes/edit";
            User currentUser = ShiroUserHolder.currentUser();
            User user = this.userService.findByUserName(currentUser.getUsername());
            dto.setCreateUser(user.getUsername());
            Object result = this.nodeUtil.postBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("场景编辑")
    @PostMapping(value = "/push/api/disposal/scenes/getByUUId")
    public Object getByUUId(@RequestBody(required = false) SceneDto dto) {
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        
        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/push/api/disposal/scenes/getByUUId";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("场景删除")
    @PostMapping(value = "/push/api/disposal/scenes/delete")
    public Object delete(@RequestBody(required = false) SceneDto dto) {
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        
        String token = sysConfig.getNspmToken();
        if(token != null){
            String[] ids = dto.getIds().split(",");
            if(ids.length>0){
                String url = "/push/api/disposal/scenes/delete";
                for(String id : ids){
                    SceneDto obj = new SceneDto();
                    obj.setId(Integer.parseInt(id));
                    this.nodeUtil.postFormDataBody(obj, url, token);
                }
                return ResponseUtil.ok();
            }
            return ResponseUtil.ok();
        }
        return ResponseUtil.error();
    }

}
