package com.cloud.tv.core.manager.admin.action;

import com.cloud.tv.core.service.ILiveRoomService;
import com.cloud.tv.core.service.ISysConfigService;
import com.cloud.tv.core.utils.CommUtils;
import com.cloud.tv.entity.LiveRoom;
import com.cloud.tv.entity.SysConfig;
import com.cloud.tv.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//rtmp://lk.soarmall.com:1935/hls
@RestController
@RequestMapping("/admin/config")
public class SysconfigManagerController {

    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private ILiveRoomService liveRoomService;

    @RequiresPermissions("LK:SYSCONFIG:MANAGER")
    @RequestMapping(value = "/list")
    public Object sysConfig(){
        SysConfig sysconfigList = this.sysConfigService.findSysConfigList();
        return sysconfigList;
    }

    @RequiresPermissions("LK:SYSCONFIG:MANAGER")
    @ApiOperation("系统配置更新")
    @RequestMapping("/update")
    public Object baseConfig(@RequestBody(required = false) SysConfig dto){
      if(dto != null){
          try {
              // SysConfig sysConfig = this.sysConfigService.findObjById(dto.getId());
              SysConfig sysConfig = this.sysConfigService.findSysConfigList();
              if(sysConfig != null){
                if(!sysConfig.getIp().equals(dto.getIp())){
                    List<LiveRoom> liveRoomList = this.liveRoomService.findAllLiveRoom();
                    // 后期改为批量更新
                    if(liveRoomList.size() > 0){
                        for(LiveRoom liveRoom : liveRoomList){
                            String bindCode = liveRoom.getBindCode();
                            SysConfig SysConfig = this.sysConfigService.findSysConfigList();
                            String rtmp = CommUtils.getRtmp(dto.getIp(), liveRoom.getBindCode());
                            String obsRtmp = CommUtils.getObsRtmp(dto.getIp());
                            liveRoom.setRtmp(rtmp);
                            liveRoom.setObsRtmp(obsRtmp);
                            this.liveRoomService.update(liveRoom);

                        }
                    }
                }
              }
              this.sysConfigService.modify(dto);
              return new Result(200, "Successfully");
          } catch (Exception e) {
              e.printStackTrace();
              return new Result(500, e.getMessage());
          }
      }
      return null;
    }
}
