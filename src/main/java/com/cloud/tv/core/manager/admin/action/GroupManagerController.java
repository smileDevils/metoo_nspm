package com.cloud.tv.core.manager.admin.action;

import com.cloud.tv.core.manager.admin.tools.GroupTools;
import com.cloud.tv.core.manager.admin.tools.ShiroUserHolder;
import com.cloud.tv.core.service.IGroupService;
import com.cloud.tv.core.service.IUserService;
import com.cloud.tv.core.utils.ResponseUtil;
import com.cloud.tv.dto.GroupDto;
import com.cloud.tv.entity.Group;
import com.cloud.tv.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/admin/group")
@RestController
public class GroupManagerController {

    @Autowired
    private IGroupService groupService;
    @Autowired
    private GroupTools groupTools;
    @Autowired
    private IUserService userService;

    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestBody(required = false) GroupDto dto){
        User currentUser = ShiroUserHolder.currentUser();
        User user = this.userService.findByUserName(currentUser.getUsername());
        Group parent = this.groupService.queryObjById(user.getGroupId());
        if(parent != null){
            List<Group> branchList = new ArrayList<Group>();
            if(this.groupTools.genericGroup(parent).size() > 0){
                this.groupTools.genericGroup(parent);
            }
            branchList.add(parent);
            return ResponseUtil.ok(branchList);
        }
        return ResponseUtil.ok();
    }

    @PostMapping("/save")
    public Object save(@RequestBody GroupDto dto){
        if(dto.getParentId() == null || dto.getParentId().equals("")){
            return ResponseUtil.badArgument("请输入上级菜单");
        }
        if(dto.getBranchName() != null){
            return ResponseUtil.ok(this.groupService.save(dto));
        }
        return ResponseUtil.badArgument("请输入组名称");
    }

//    @RequestMapping("/del")
//    public Object del(@RequestBody GroupDto dto){
//        List<Group> groupList = this.groupService.queryChild(dto.getId());
//        if(groupList.size() <= 0){
//            if(this.groupService.del(dto.getId())){
//                for(Group group : groupList){
//                    this.groupService.del(group.getId());
//                }
//                return ResponseUtil.ok();
//            }
//            return ResponseUtil.error();
//        }
//        return ResponseUtil.badArgument("优先删除下级组织");
//    }
    @RequestMapping("/del")
    public Object del(@RequestBody GroupDto dto){
        Group group = this.groupService.queryObjById(dto.getId());
        if (this.delGroup(group.getId())){

            return ResponseUtil.ok();
        }
        return ResponseUtil.error();
    }

    public boolean delGroup(Long id){
        try {
            Group obj = this.groupService.queryObjById(id);
            if(obj != null){
                List<String> users = this.userService.getObjByLevel(obj.getLevel());
                if(users.size() > 0){
                    this.userService.deleteByLevel(obj.getLevel());
                }
                this.groupService.del(obj.getId());
                List<Group> groupList = this.groupService.queryChild(id);
                if (groupList.size() > 0){
                    for (Group group:groupList){
                        this.delGroup(group.getId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @GetMapping("/verify")
    public Object del(String id){
        Group group = this.groupService.queryObjById(Long.parseLong(id));
        if(group != null){
            List<String> users = this.userService.getObjByLevel(group.getLevel());
            if(users.size() > 0){
                User user = ShiroUserHolder.currentUser();
                if(users.contains(user.getUsername())){
                    return ResponseUtil.ok(2);
                }
                return ResponseUtil.ok(1);
            }
            List<Group> groupList = this.groupService.queryChild(Long.parseLong(id));
            if(groupList.size() > 0){
                return ResponseUtil.ok(1);
            }
            return ResponseUtil.ok(0);
        }
        return ResponseUtil.badArgument();
    }

    @RequestMapping("/parent")
    @ResponseBody
    public Object queryParent(@RequestBody(required = false) GroupDto dto){
        if(dto != null){
            List<Group> parent = this.groupService.queryChild(dto.getParentId());
            return ResponseUtil.ok(parent);
        }else{
            List<Group> parent = this.groupService.queryChild(null);
            return ResponseUtil.ok(parent);
        }
    }




}
