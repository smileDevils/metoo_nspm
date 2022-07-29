package com.cloud.tv.core.manager.zabbix.zabbixapi;

import com.alibaba.fastjson.JSONObject;
import com.cloud.tv.core.manager.zabbix.utils.ZabbixApiUtil;
import com.cloud.tv.core.service.zabbix.IHostGroupService;
import com.cloud.tv.core.utils.ResponseUtil;
import com.cloud.tv.dto.zabbix.HostCreateDTO;
import com.cloud.tv.dto.zabbix.HostDTO;
import io.github.hengyunabc.zabbix.api.DeleteRequest;
import io.github.hengyunabc.zabbix.api.Request;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Api("Host")
@RequestMapping("/zabbixapi/host")
@RestController
public class ZabbixHostManagerAction {

    @Autowired
    private ZabbixApiUtil zabbixApiUtil;
    @Autowired
    private IHostGroupService hostGroupService;

    @RequestMapping("/get")
    public Object get(@RequestBody(required = false) HostDTO dto){
        Request request = this.zabbixApiUtil.parseParam(dto, "host.get");
        return ResponseUtil.ok(zabbixApiUtil.call(request).get("result"));
    }

    @RequestMapping("/create")
    public Object create(@RequestBody(required = false) HostCreateDTO dto){
        // 设置默认主机组
        String groupId = this.hostGroupService.getHostGroupId();
        Map map  = new HashMap();
        map.put("groupid", groupId);
        dto.setGroups(Arrays.asList(map));
        Request request = this.zabbixApiUtil.parseParam(dto, "host.create");
        JSONObject result = zabbixApiUtil.call(request);
        JSONObject error = JSONObject.parseObject(result.getString("error"));
        if(error == null){
            return ResponseUtil.ok();
        }
        return ResponseUtil.error(error.getString("data"));
    }


    @RequestMapping("/update")
    public Object update(@RequestBody(required = false) HostCreateDTO dto){
        Request request = this.zabbixApiUtil.parseParam(dto, "host.update");
        JSONObject result = zabbixApiUtil.call(request);
        JSONObject error = JSONObject.parseObject(result.getString("error"));
        if(error == null){
            return ResponseUtil.ok();
        }
        return ResponseUtil.error(error.getString("data"));
    }

    @RequestMapping("/delete")
    public Object delete(@RequestBody(required = false) HostDTO dto){
        DeleteRequest request = this.zabbixApiUtil.parseArrayParam(dto, "host.delete");
        JSONObject result = zabbixApiUtil.call(request);
        JSONObject error = JSONObject.parseObject(result.getString("error"));
        if(error == null){
            return ResponseUtil.ok();
        }
        return ResponseUtil.error(error.getString("data"));
    }

}
