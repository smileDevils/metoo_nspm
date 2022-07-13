package com.cloud.tv.core.manager.monitor;

import com.cloud.tv.core.manager.admin.tools.ShiroUserHolder;
import com.cloud.tv.core.service.IMonitorService;
import com.cloud.tv.core.utils.ResponseUtil;
import com.cloud.tv.core.utils.query.PageInfo;
import com.cloud.tv.dto.MonitorDto;
import com.cloud.tv.entity.LiveRoom;
import com.cloud.tv.entity.Monitor;
import com.cloud.tv.entity.User;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("监控管理器")
@RequiresPermissions("LK:MONITOR:MANAGER")
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @Autowired
    private IMonitorService monitorService;

    @ApiOperation("监控列表")
    @RequestMapping("/list")
    public Object mointor(@RequestBody MonitorDto dto){
        if(dto == null){
            dto = new MonitorDto();
        }
        User user = ShiroUserHolder.currentUser();
        Page<Monitor> page = this.monitorService.query(dto);
        if(page.getResult().size() > 0){
            return ResponseUtil.ok(new PageInfo<LiveRoom>(page));
        }
        return ResponseUtil.ok();
    }

    @ApiOperation("监控日志-添加")
    @RequestMapping("/save")
    public Object save(@RequestBody MonitorDto dto){
        if(dto != null){
            // 查询AppId是否存在 省略该步骤；直接记录应用信息
            // 1, 根据签名查询该记录是否已记录,无：直接记录直播信息 有：关闭直播
       /*     Monitor monitor = this.monitorService.getObjBySign(dto.getSign());
            if(monitor != null){
                // 直播结束
                monitor.setStatus(0);
                monitor.setEndTime(new Date());
                this.monitorService.update(dto);
            }else{

            }*/
            this.monitorService.save(dto);
            return ResponseUtil.ok();
        }
        return ResponseUtil.badArgument();
    }
}
