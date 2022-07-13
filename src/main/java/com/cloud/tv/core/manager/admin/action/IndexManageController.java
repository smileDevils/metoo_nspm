package com.cloud.tv.core.manager.admin.action;

import com.cloud.tv.core.manager.admin.tools.ShiroUserHolder;
import com.cloud.tv.core.service.IIndexService;
import com.cloud.tv.core.service.ISysConfigService;
import com.cloud.tv.core.service.IssuedService;
import com.cloud.tv.core.utils.ResponseUtil;
import com.cloud.tv.entity.SysConfig;
import com.cloud.tv.entity.Task;
import com.cloud.tv.entity.User;
import com.cloud.tv.vo.MenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("系统首页")
@RequestMapping("/index")
@RestController
public class    IndexManageController {

    @Autowired
    private IIndexService indexService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IssuedService issuedService;

//    @RequiresPerions("ADMIN:INDEX:MENU")
    @ApiOperation("系统菜单")
    @RequestMapping("/menu")
    public Object menu(){
        Map map = new HashMap();
        User user = ShiroUserHolder.currentUser();
        List<MenuVo> menuList = this.indexService.findMenu(user.getId());
        map.put("obj", menuList);
        map.put("userRole", user.getUserRole());
        SysConfig configs = this.configService.findSysConfigList();
        map.put("domain", configs.getDomain());
        return ResponseUtil.ok(map);
    }

    @ApiOperation("系统导航")
    @RequestMapping("/nav")
    public Object nav(){
        Map map = new HashMap();
        User user = ShiroUserHolder.currentUser();
        List<MenuVo> menuList = this.indexService.findMenu(user.getId());
        map.put("obj", menuList);
        SysConfig configs = this.configService.findSysConfigList();
        map.put("domain", configs.getDomain());
        return ResponseUtil.ok(map);
    }

    // 策略统计
    // 对象统计
    // 工单统计
    // 网元统计
    @RequestMapping("/task")
    public Object task(){
        List<Task> tasks = this.issuedService.query();
        return ResponseUtil.ok(tasks);
    }

}
