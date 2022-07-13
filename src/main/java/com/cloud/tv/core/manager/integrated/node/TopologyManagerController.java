package com.cloud.tv.core.manager.integrated.node;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.tv.core.manager.admin.tools.ShiroUserHolder;
import com.cloud.tv.core.manager.integrated.utils.RestTemplateUtil;
import com.cloud.tv.core.service.IPolicyService;
import com.cloud.tv.core.service.ISysConfigService;
import com.cloud.tv.core.service.IUserService;
import com.cloud.tv.core.utils.NodeUtil;
import com.cloud.tv.core.utils.ResponseUtil;
import com.cloud.tv.core.utils.httpclient.UrlConvertUtil;
import com.cloud.tv.dto.TopoNodeDto;
import com.cloud.tv.entity.SysConfig;
import com.cloud.tv.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("拓扑管理")
@RequestMapping("/nspm/topology")
@RestController
public class TopologyManagerController {

    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NodeUtil nodeUtil;
    @Autowired
    private IUserService userService;
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private IPolicyService policyService;
    @Autowired
    private UrlConvertUtil urlConvertUtil;

    public static void main(String[] args) {
        Double a = new Double(0);
        System.out.println(a);
    }

    // 获取健康度
    @ApiOperation("获取图层设备健康度")
    @GetMapping("/device/score")
    public Object getGrade(String deviceUuid){
        Double grade = this.policyService.HealthScore(deviceUuid);
        return ResponseUtil.ok(grade.intValue());
    }

    @ApiOperation("图层列表")
    @RequestMapping(value="/topology-layer/layerInfo/GET/listLayers")
    public Object listLayers(TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        if(token != null){
            User currentUser = ShiroUserHolder.currentUser();
            User user = this.userService.findByUserName(currentUser.getUsername());
            String url = "/topology-layer/layerInfo/GET/listLayers";
//            if(dto.getBranchLevel() == null || dto.getBranchLevel().equals("")){
//                dto.setBranchLevel(user.getGroupLevel());
//            }
            Object result = this.nodeUtil.getBody(dto, url, token);
            JSONObject results = JSONObject.parseObject(result.toString());
            JSONArray arrays = JSONArray.parseArray(results.get("rows").toString());
            List list = new ArrayList();
            for(Object array : arrays){
                JSONObject obj = JSONObject.parseObject(array.toString());
                if(obj.get("layerUuid") != null){
                    String photosUrl = "/topology-layer/" + obj.get("layerUuid") + ".png";
                    photosUrl = this.urlConvertUtil.convert(photosUrl);
                    try {
                        String photo = null;
                        photo = this.restTemplateUtil.getInputStream(photosUrl);
                        obj.put("photo", photo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                list.add(obj);
            }
            results.put("rows", list);
            return ResponseUtil.ok(results);
        }
        return ResponseUtil.error();
    }

//    @ApiOperation("图层列表")
//    @RequestMapping(value="/topology-layer/layerInfo/GET/listLayers")
//    public Object listLayers(TopoNodeDto dto){
//        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
//        String token = sysConfig.getNspmToken();
//        if(token != null){
//            User currentUser = ShiroUserHolder.currentUser();
//            User user = this.userService.findByUserName(currentUser.getUsername());
//            String url = "/topology-layer/layerInfo/GET/listLayers";
//            if(dto.getBranchLevel() == null || dto.getBranchLevel().equals("")){
//                dto.setBranchLevel(user.getGroupLevel());
//            }
//            Object result = this.nodeUtil.getBody(dto, url, token);
//            JSONObject results = JSONObject.parseObject(result.toString());
//            // 检测用户
//            List<String> users = this.userService.getObjByLevel(dto.getBranchLevel());
//            if(users == null || users.size() <= 0){
//                return ResponseUtil.ok();
//            }
//            JSONArray arrays = JSONArray.parseArray(results.get("rows").toString());
//            List list = new ArrayList();
//            for(Object array : arrays){
//                JSONObject obj = JSONObject.parseObject(array.toString());
//                String userName = obj.get("layerDesc").toString();
//                if(users.contains(userName)){
//                    if(obj.get("layerUuid") != null){
//                        String photosUrl = "/topology-layer/" + obj.get("layerUuid") + ".png";
//                        photosUrl = this.urlConvertUtil.convert(photosUrl);
//                try {
//                            String photo = null;
//                            photo = this.restTemplateUtil.getInputStream(photosUrl);
//                            obj.put("photo", photo);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    obj.put("userName", userName);
//                    list.add(obj);
//                }
//
//            }
//            results.put("rows", list);
//            return ResponseUtil.ok(results);
//        }
//        return ResponseUtil.error();
//    }


    @RequestMapping("upload")
    public Object upload(){
        String url = "https://img2.360buyimg.com/pop/s1180x940_jfs/t1/198549/9/21811/83119/625f7592E8cad4ada/8a771626b433d9fb.png";
//        String url = "https://192.168.5.100/topology-layer/5d519a8c1c5c4b59ad7596eefe0ec365.png";
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);// 设置密钥
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, byte[].class);
//        //获取entity中的数据
//        byte[] body = responseEntity.getBody();
//        //创建输出流  输出到本地
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\46075\\Desktop\\新建文件夹 (4)\\1.jpg"));
//            fileOutputStream.write(body);
//            //关闭流
//            fileOutputStream.close();
//            return new String(Base64.encodeBytes(body));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return responseEntity;
    }

    @RequestMapping("photo")
    public Object photo(){
        String url = "https://192.168.5.100/topology-layer/5d519a8c1c5c4b59ad7596eefe0ec365.png";
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);// 设置密钥
//        headers.setContentType(MediaType.);

        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, byte[].class);

//        //获取entity中的数据
//        byte[] body = responseEntity.getBody();
//        //创建输出流  输出到本地
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\46075\\Desktop\\新建文件夹 (4)\\1.jpg"));
//            fileOutputStream.write(body);
//            //关闭流
//            fileOutputStream.close();
//            return new String(Base64.encodeBytes(body));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return responseEntity;
    }

    @RequestMapping("/download")
    public void dowload(HttpServletResponse response){
        String a = "abc";
        byte[] bytes = a.getBytes();
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=picture.txt");
        try {
            OutputStream os = response.getOutputStream();
            // 将字节流传入到响应流里,响应到浏览器
            os.write(bytes);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 将流输出到指定文件夹
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\46075\\Desktop\\新建文件夹 (4)\\1.jpg"));
//            //            fileOutputStream.write(body);
//            fileOutputStream.write(bytes);
//            fileOutputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    @RequestMapping("generate")
    public void generate(String path, String photo){
        this.restTemplateUtil.generateImage(photo, "C:\\Users\\46075\\Desktop\\新建文件夹 (4)\\a.png");
    }



    @ApiOperation("默认图层")
    @RequestMapping(value="/topology-layer/layerInfo/GET/defaultLayer")
    public Object defaultLayer(){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/layerInfo/GET/defaultLayer";
            Object result = this.nodeUtil.getBody(null, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }


    @ApiOperation("图层编辑（修改拓扑名称）")
    @RequestMapping(value="/topology-layer/layerInfo/POST/editLayer")
    public Object editLayer(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/layerInfo/POST/editLayer";
            Object result = this.nodeUtil.postFormDataBody(dto  , url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("新建画布")
    @RequestMapping("/topology-layer/layerInfo/POST/saveLayer")
    public Object saveLayer(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/layerInfo/POST/saveLayer";
            if(dto.getBranchLevel() == null || dto.getBranchLevel().equals("")){
                User currentUser = ShiroUserHolder.currentUser();
                User user = this.userService.findByUserName(currentUser.getUsername());
                dto.setBranchLevel(user.getGroupLevel());
            }
            Object result = this.nodeUtil.postBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }
//
//    @ApiOperation("新建画布")
//    @RequestMapping("/topology-layer/layerInfo/POST/saveLayer")
//    public Object saveLayer(@RequestBody(required = false) TopoNodeDto dto){
//        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
//        String token = sysConfig.getNspmToken();
//        if(token != null){
//            User currentUser = ShiroUserHolder.currentUser();
//            User user = this.userService.findByUserName(currentUser.getUsername());
//            dto.setDesc(user.getUsername());
//            String url = "/topology-layer/layerInfo/POST/saveLayer";
//            Object result = this.nodeUtil.postBody(dto, url, token);
//            return ResponseUtil.ok(result);
//        }
//        return ResponseUtil.error();
//    }

    @ApiOperation("删除画布")
    @RequestMapping("/topology-layer/layerInfo/DELETE/layers")
    public Object DELETE(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/layerInfo/DELETE/layers";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("设置默认图层")
    @RequestMapping("/topology-layer/layerInfo/PUT/defaultLayer")
    public Object defaultLayer(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/layerInfo/PUT/defaultLayer";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }


    @ApiOperation("拓扑详情")
    @RequestMapping("/topology-layer/layerInfo/GET/getLayerGradeByUuid")
    public Object getLayerByUgetLayerGradeByUuiduid(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/layerInfo/GET/getLayerByUuid";
            Object object = this.nodeUtil.getBody(dto, url, token);
            JSONObject result = JSONObject.parseObject(object.toString());
            if(result.get("content") != null){
                JSONObject content = JSONObject.parseObject(result.get("content").toString());
                if(content.get("layout") != null){
                    JSONObject layout = JSONObject.parseObject(content.get("layout").toString());
                    Map map = new HashMap();
                    for (Map.Entry<String,Object> entry : layout.entrySet()){
                        JSONObject value = JSONObject.parseObject(entry.getValue().toString());
                        if(value.get("nodeType").toString().equals("router") || value.get("nodeType").toString().equals("firewall")){
                            Double grade = this.policyService.HealthScore(entry.getKey());
                            if(grade != null){
                                map.put(entry.getKey(), grade);
                            }
                        }
                    }
                    return ResponseUtil.ok(map);
                }
            }
            return ResponseUtil.ok();
        }
        return ResponseUtil.error();
    }

    @ApiOperation("拓扑详情")
    @RequestMapping("/topology-layer/layerInfo/GET/getLayerByUuid")
    public Object getLayerByUuid(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/layerInfo/GET/getLayerByUuid";
            Object object = this.nodeUtil.getBody(dto, url, token);
            JSONObject result = JSONObject.parseObject(object.toString());
            if(result.get("content") != null){
                JSONObject content = JSONObject.parseObject(result.get("content").toString());
                if(content.get("layout") != null){
                    JSONObject layout = JSONObject.parseObject(content.get("layout").toString());
                    Map map = new HashMap();
                    for (Map.Entry<String,Object> entry : layout.entrySet()){
//                        double grade = this.policyService.HealthScore(entry.getKey());
                        JSONObject value = JSONObject.parseObject(entry.getValue().toString());
                        value.put("grade", 0);
                        map.put(entry.getKey(), value);
                    }
                    content.put("layout", map);
                }
                result.put("content", content);
            }
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("复制图层")
    @RequestMapping("/topology-layer/layerInfo/POST/copyLayer")
    public Object copyLayer(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/layerInfo/POST/copyLayer";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("移动")
    @RequestMapping("/topology-layer/layerInfo/POST/editLayerBranch")
    public Object editLayerBranch(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/layerInfo/POST/editLayerBranch";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("源子网查询")
    @RequestMapping("/topology-layer/whale/GET/subnets")
    public Object subnets(@RequestBody TopoNodeDto dto){
        String ipAddr = dto.getIp4Addr();
        String srcIpaddr = null;
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();
        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/GET/subnets";
            Object object = this.nodeUtil.getBody(dto, url, token);
            JSONObject result = JSONObject.parseObject(object.toString());
            if(result.get("success").toString().equals("false")){
              return ResponseUtil.error(result.get("message").toString());
            }
            JSONArray arrays = JSONArray.parseArray(result.get("data").toString());
            if(arrays.get(0) != null){
                JSONObject data = JSONObject.parseObject(arrays.get(0).toString());
                srcIpaddr = data.get("ip4BaseAddress").toString();
                srcIpaddr = this.subIp(srcIpaddr);
                ipAddr = this.subIp(ipAddr);
            }
            if(ipAddr == null || ipAddr.equals("")){
                return ResponseUtil.ok(result);
            }else{
                if(srcIpaddr.equals(ipAddr)){
                    return ResponseUtil.ok(result);
                }else{
                    return ResponseUtil.badArgument();
                }
            }

        }
        return ResponseUtil.error();
    }

    public String subIp(String ipAddr){
        if(ipAddr != null && !ipAddr.equals("")){
            int index = ipAddr.indexOf(".");
            return ipAddr.substring(0,index);
        }
        return null;
    }
    @ApiOperation("子网（相关设备）")
    @GetMapping("/topology-layer/whale/GET/subnet/linkedDevice")
    public Object linkedDevice(TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/GET/subnet/linkedDevice";
            Object result = this.nodeUtil.getBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("子网拆分（二层设备）记录")
    @RequestMapping("/topology-layer/whale/POST/topo/action/all-split-subnet-summary")
    public Object subnetSimmary(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/POST/topo/action/all-split-subnet-summary";
            Object result = this.nodeUtil.postBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("二层设备(保存)")
    @RequestMapping("/topology-layer/whale/PUT/topo/action/splitSubnet")
    public Object topoSplitSubnet(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/PUT/topo/action/splitSubnet";
            Object result = this.nodeUtil.putBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }




    @ApiOperation("撤销（设备接入或子网拆分）")
    @RequestMapping("/topology-layer/whale/PUT/topo/action/undo/splitSubnet")
    public Object splitSubnet(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/PUT/topo/action/undo/splitSubnet";
            Object result = this.nodeUtil.putFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("VPN")
    @RequestMapping("/topology-layer/whale/GET/topo/action/linkVpn")
    public Object linkVpn(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/GET/topo/action/linkVpn";
            Object result = this.nodeUtil.getBody(null, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("VPN（设备）|二层设备（设备）")
    @RequestMapping("/topology-layer/whale/GET/devices/summary")
    public Object devicesSummary(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/GET/devices/summary";
            Object result = this.nodeUtil.getBody(null, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("VPN-选择设备-接口")
    @RequestMapping("/topology-layer/whale/GET/vpn/subnet")
    public Object vpnSubnet(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/GET/vpn/subnet";
            Object result = this.nodeUtil.getBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("VPN-保存")
    @RequestMapping("/topology-layer/whale/PUT/topo/action/linkVpn")
    public Object putLingVpn(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/PUT/topo/action/linkVpn";
            Object result = this.nodeUtil.putBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("撤销")
    @RequestMapping("/topology-layer/whale/DELETE/topo/action/linkVpn")
    public Object deleteLinkVpn(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/DELETE/topo/action/linkVpn";
            Object result = this.nodeUtil.deleteBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("一键更新")
    @RequestMapping("/topology-layer/layerInfo/POST/updateLayerStatus")
    public Object updateLayerStatus(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/layerInfo/POST/updateLayerStatus";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("路径")
    @RequestMapping("/topology/queryRoutesByLayerUuid.action")
    public Object queryRoutesByLayerUuid(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology/queryRoutesByLayerUuid.action";
            Object result = this.nodeUtil.getBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("路径备份")
    @RequestMapping("/topology/addRoute.action")
    public Object addRoute(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology/addRoute.action";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }
    @ApiOperation("路径细节")
    @RequestMapping("/topology-layer/whale/GET/detailedPath/run")
    public Object run(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/GET/detailedPath/run";
            Object result = this.nodeUtil.postBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("关联子网")
    @PostMapping("/topology-layer/whale/GET/device/subnets")
    public Object deviceSubnets(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/GET/device/subnets";
            Object result = this.nodeUtil.postBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("设备-所属逻辑域")
    @PostMapping("/risk/api/alarm/zone/listLogicZoneAndSubnets")
    public Object listLogicZoneAndSubnets(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/risk/api/alarm/zone/listLogicZoneAndSubnets";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("设备-防火墙安全域")
    @RequestMapping("/topology-layer/whale/GET/device/zones")
    public Object zones(TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-layer/whale/GET/device/zones";
            Object result = this.nodeUtil.getBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("子网-关联主机")
    @PostMapping("/risk/api/danger/hostComputerSoftware/hostComputerList")
    public Object hostComputerList(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/risk/api/danger/hostComputerSoftware/hostComputerList";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }



    @ApiOperation("资产组列表")
    @PostMapping("/risk/api/danger/hostComputerSoftware/assetGroupList")
    public Object assetGroupList(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/risk/api/danger/hostComputerSoftware/assetGroupList";
            Object result = this.nodeUtil.postBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("资产管理-关联子网")
    @PostMapping("/risk/api/danger/assetHost/getSubnetByAssetGroup")
    public Object getSubnetByAssetGroup(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/risk/api/danger/assetHost/getSubnetByAssetGroup";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("资产管理-主机列表")
    @PostMapping("/risk/api/danger/assetHost/pageList")
    public Object pageList(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/risk/api/danger/assetHost/pageList";
            Object result = this.nodeUtil.postBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("域-业务区域树")
    @PostMapping("/risk/api/danger/businessZone/businessZoneTree")
    public Object businessZoneTree(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/risk/api/danger/businessZone/businessZoneTree";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("域-关联子网")
    @PostMapping("/risk/api/alarm/zone/listLogicZoneSubnetWithPage")
    public Object listLogicZoneSubnetWithPage(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/risk/api/alarm/zone/listLogicZoneSubnetWithPage";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("域-主机列表")
    @PostMapping("/risk/api/danger/businessZone/pageList")
    public Object businessZone(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/risk/api/danger/businessZone/pageList";
            Object result = this.nodeUtil.postBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("原始日志")
    @PostMapping("/combing/api/hit/rawlog/findList")
    public Object findList(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/combing/api/hit/rawlog/findList";
            Object result = this.nodeUtil.postFormDataBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("安全域")
    @PostMapping("/risk/api/alarm/zone/listLogicZone")
    public Object listLogicZone(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/risk/api/alarm/zone/listLogicZone";
            Object result = this.nodeUtil.postBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }

    @ApiOperation("设备策略")
    @PostMapping("/topology-policy/pathAnaly/external/deviceDetail")
    public Object deviceDetail(@RequestBody(required = false) TopoNodeDto dto){
        SysConfig sysConfig = this.sysConfigService.findSysConfigList();

        String token = sysConfig.getNspmToken();
        if(token != null){
            String url = "/topology-policy/pathAnaly/external/deviceDetail";
            Object result = this.nodeUtil.postBody(dto, url, token);
            return ResponseUtil.ok(result);
        }
        return ResponseUtil.error();
    }


}
