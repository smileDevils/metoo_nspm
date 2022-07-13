package com.cloud.tv.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.tv.core.manager.admin.tools.ShiroUserHolder;
import com.cloud.tv.core.mapper.CredentialMaaper;
import com.cloud.tv.core.service.ICredentialService;
import com.cloud.tv.core.service.ISysConfigService;
import com.cloud.tv.core.service.IUserService;
import com.cloud.tv.core.utils.NodeUtil;
import com.cloud.tv.dto.TopoCredentialDto;
import com.cloud.tv.entity.Credential;
import com.cloud.tv.entity.SysConfig;
import com.cloud.tv.entity.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class  CredentialServiceImpl implements ICredentialService {

    @Autowired
    private CredentialMaaper credentialMaaper;
    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private NodeUtil nodeUtil;
    @Autowired
    private IUserService userService;


    @Override
    public Credential getObjById(Long id) {
        return this.credentialMaaper.getObjById(id);
    }

    @Override
    public Credential getObjByName(String name) {
        return this.credentialMaaper.getObjByName(name);
    }

    @Override
    public List<Credential> query() {
        return null;
    }

    @Override
    public int save(Credential instance) {
        return this.credentialMaaper.save(instance);
    }

    @Override
    public int update(Credential instance) {
        return this.credentialMaaper.update(instance);
    }

    @Override
    public int delete(Long id) {
        return this.credentialMaaper.delete(id);
    }

    @Override
    public Map<String, String> getUuid(TopoCredentialDto dto) {
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/push/credential/getall";
            Object result = this.nodeUtil.postBody(dto, url, token);
            JSONObject json = JSONObject.parseObject(result.toString());
            if(json.get("content") != null){
                JSONObject content = JSONObject.parseObject(json.get("content").toString());
                if(content.get("list") != null) {
                    List list = new ArrayList();
                    JSONArray arrays = JSONArray.parseArray(content.get("list").toString());
                    for (Object array : arrays) {
                        JSONObject credential = JSONObject.parseObject(array.toString());
                        if(credential.get("uuid") != null && credential.get("name").equals(dto.getName())){
                            Map map = new HashMap();
                            map.put("uuid", credential.get("uuid").toString());
                            map.put("credentialId", credential.get("id").toString());
                            return map;
                        }
                    }
                }
            }

    }
     return null;
    }

    @Override
    public Page<Credential> getObjsByLevel(Credential instance) {
        if(instance.getBranchLevel() == null || instance.getBranchLevel().equals("")){
            User currentUser = ShiroUserHolder.currentUser();
            User user = this.userService.findByUserName(currentUser.getUsername());
            instance.setBranchLevel(user.getGroupLevel());
        }
        Page<Credential> page = PageHelper.startPage(instance.getCurrentPage(), instance.getPageSize());
        this.credentialMaaper.getObjsByLevel(instance);
        return page;
    }
}
