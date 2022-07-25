package com.cloud.tv.core.service.zabbix.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.tv.core.service.zabbix.ZabbixHistoryService;
import com.cloud.tv.core.service.zabbix.ZabbixHostService;
import com.cloud.tv.core.service.zabbix.ZabbixItemService;
import com.cloud.tv.core.service.zabbix.ZabbixService;
import com.cloud.tv.core.utils.ResponseUtil;
import com.cloud.tv.core.utils.net.IpV4Util;
import com.cloud.tv.dto.zabbix.HistoryDTO;
import com.cloud.tv.dto.zabbix.HostDTO;
import com.cloud.tv.dto.zabbix.ItemDTO;
import org.apache.commons.collections.ArrayStack;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ZabbixServiceImpl implements ZabbixService {


    @Autowired
    private ZabbixHostService zabbixHostService;
    @Autowired
    private ZabbixItemService zabbixItemService;
    @Autowired
    private ZabbixHistoryService historyService;
    @Autowired
    private IpV4Util ipV4Util;

    @Override
    public Object getUsage(String ip, List itemName) {
        if(StringUtils.isNotEmpty(ip)){
            HostDTO dto = new HostDTO();
            Map map = new HashMap();
            map.put("ip", Arrays.asList(ip));
            dto.setFilter(map);
            Object object = this.zabbixHostService.getHost(dto);
            JSONObject jsonObject = JSONObject.parseObject(object.toString());
            if(jsonObject.get("result") != null){
                JSONArray arrays = JSONArray.parseArray(jsonObject.getString("result"));
                if(arrays.size() > 0){
                    JSONObject host = JSONObject.parseObject(arrays.get(0).toString());
                    Integer hostid = host.getInteger("hostid");
                    if(hostid != null){
                        ItemDTO itemDto = new ItemDTO();
                        itemDto.setHostids(Arrays.asList(hostid));
                        Map filterMap = new HashMap();
                        filterMap.put("name", itemName);
                        itemDto.setFilter(filterMap);
                        Object itemObejct = this.zabbixItemService.getItem(itemDto);
                        Object result = this.parseUsage(itemObejct);
                        return result;
                    }
                }

            }
        }
        return null;
    }

    @Override
    public Object getDeviceInfo(String ip, List itemName, Integer limit, Long time_till, Long time_from) {
        if(StringUtils.isNotEmpty(ip)){
            HostDTO dto = new HostDTO();
            Map map = new HashMap();
            map.put("ip", Arrays.asList(ip));
            dto.setFilter(map);
            Object object = this.zabbixHostService.getHost(dto);
            JSONObject jsonObject = JSONObject.parseObject(object.toString());
            if(jsonObject.get("result") != null){
                JSONArray arrays = JSONArray.parseArray(jsonObject.getString("result"));
                if(arrays.size() > 0){
                    JSONObject host = JSONObject.parseObject(arrays.get(0).toString());
                    Integer hostid = host.getInteger("hostid");
                    if(hostid != null){
                        ItemDTO itemDto = new ItemDTO();
                        itemDto.setHostids(Arrays.asList(hostid));
                        Map filterMap = new HashMap();
                        filterMap.put("name", itemName);
                        itemDto.setFilter(filterMap);
                        Object itemObejct = this.zabbixItemService.getItem(itemDto);
                        Object result = this.parseHistory(itemObejct, limit, time_till, time_from);
                        return result;
                    }
                }

            }
        }
        return null;
    }

    @Override
    public Object refresh(String itemids, Integer limit) {
        Object object = this.getHistory(Arrays.asList(itemids), limit);
        return object;
    }

    @Override
    public Object getInterfaceInfo(String ip) {
        Object object = this.getItem(ip);
        // 解析item 信息
        Object inteface = this.parseItemDevice(object);
        return inteface;
    }

    @Override
    public Object getInterfaceHistory(String ip, Integer limit, Long time_till, Long time_from) {
        Object object = this.getItem(ip);
        // 解析item 信息
        Object inteface = this.parseHistory2(object, time_till, time_from);
        return inteface;
    }


    @Override
    public Object flow(String ip, String name) {
        Object object = this.getItem(ip);
        // 解析item 信息
        Object inteface = this.parseItemFlow(object, name);
        return inteface;
    }

    public Object getItem(String ip){
        if(StringUtils.isNotEmpty(ip)){
            HostDTO dto = new HostDTO();
            Map map = new HashMap();
            map.put("ip", Arrays.asList(ip));
            dto.setFilter(map);
            Object object = this.zabbixHostService.getHost(dto);
            JSONObject jsonObject = JSONObject.parseObject(object.toString());
            if(jsonObject.get("result") != null){
                JSONArray arrays = JSONArray.parseArray(jsonObject.getString("result"));
                if(arrays.size() > 0){
                    JSONObject host = JSONObject.parseObject(arrays.get(0).toString());
                    Integer hostid = host.getInteger("hostid");
                    if(hostid != null){
                        ItemDTO itemDto = new ItemDTO();
                        itemDto.setHostids(Arrays.asList(hostid));
                        Map filterMap = new HashMap();
                        itemDto.setFilter(filterMap);
                        itemDto.setMonitored(true);
                        Object itemObejct = this.zabbixItemService.getItem(itemDto);
                        return itemObejct;
                    }
                }
            }
        }
        return null;
    }

    public Object parseUsage(Object object){
        JSONObject itemJSON = JSONObject.parseObject(object.toString());
        Map data = new HashMap();
        if(itemJSON.get("result") != null){
            JSONArray itemArray = JSONArray.parseArray(itemJSON.getString("result"));
            if(itemArray.size() > 0){
                data.put("flag", 0);
                for(Object array : itemArray){
                    JSONObject item = JSONObject.parseObject(array.toString());
                    data.put(item.getString("name"), item.getString("lastvalue"));
                    data.put("flag", this.verifyThresholdValue(data));
                }
            }
        }
        return data;
    }

    public Object parseHistory(Object object, Integer limit, Long time_till, Long time_from){
        JSONObject itemJSON = JSONObject.parseObject(object.toString());
        Map data = new HashMap();
        if(itemJSON.get("result") != null){
            JSONArray itemArray = JSONArray.parseArray(itemJSON.getString("result"));
            if(itemArray.size() > 0){
                for(Object array : itemArray){
                    JSONObject item = JSONObject.parseObject(array.toString());
                    data.put(item.getString("name"), item.getString("lastvalue"));
                    // 获取 CPU 图形
                    if(item.getString("name").equals("CpuUsage")){
                        Object historys =  this.getHistory(Arrays.asList(item.getInteger("itemid")), limit, time_till, time_from);
                        data.put("CpuUsage", historys);
                    }
                    // 获取 Mem 图形
                    if(item.getString("name").equals("MemUsage")){
                        Object historys =  this.getHistory(Arrays.asList(item.getInteger("itemid")), limit, time_till, time_from);
                        data.put("MemUsage", historys);
                    }

                }
            }
        }
        return data;
    }


    public Object parseHistory2(Object object, Long time_till, Long time_from){
        JSONObject item = JSONObject.parseObject(object.toString());
        if(item.get("result") != null){
            JSONArray result = JSONArray.parseArray(item.get("result").toString());
            Map<Integer, Map<String, String>> map = new HashMap();
            for (Object array : result){
                JSONObject element = JSONObject.parseObject(array.toString());
                // 获取索引
                Integer index = null;
                String interfaceName = "";
                String name = element.getString("name");
                String lastvalue = element.getString("lastvalue");
                String itemid = element.getString("itemid");
                if(name.contains("Interface") && name.contains("sent")
                        || name.contains("Interface") && name.contains("received")
                        || name.contains("Interface") && name.contains("Speed")){
                    int i = name.indexOf(" ") + 1;
                    int ii = name.indexOf(" ", i + 1);
                    String sequence = name.substring(i, ii);
                    index = Integer.parseInt(sequence);
                    int start = name.indexOf(" ", i + 1) + 1;
                    int last = name.indexOf(":");
                    System.out.println(name);
                    interfaceName = name.substring(start, last);
                }
                if(index != null){
                    Map eleMap = map.get(index);
                    if(eleMap == null){
                        eleMap = new HashMap();
                        map.put(index, eleMap);
                    }
                    if(StringUtils.isNotEmpty(interfaceName)){
                        eleMap.put("name", interfaceName);
                    }
                    if(name.contains("sent")){
                        Object obj = this.getHistory(Arrays.asList(itemid), 10, time_till, time_from);
                        JSONObject json = JSONObject.parseObject(obj.toString());
                        eleMap.put("sentHistory", json.get("result"));
                    }
                    if(name.contains("received")){
                        Object obj = this.getHistory(Arrays.asList(itemid), 10, time_till, time_from);
                        JSONObject json = JSONObject.parseObject(obj.toString());
                        eleMap.put("receivedHistory", json.get("result"));
                    }
                    if(name.contains("Speed")){
                        eleMap.put("speed", lastvalue);
                    }
                }
            }
            if(!map.isEmpty()){
                List list = new ArrayList();
                for (Integer key : map.keySet()){
                    list.add(map.get(key));
                }
                return list;
            }
            return map;
        }
        return null;
    }

    public Object parseHistory3(Object object, Long time_till, Long time_from){
        JSONObject item = JSONObject.parseObject(object.toString());
        if(item.get("result") != null){
            JSONArray result = JSONArray.parseArray(item.get("result").toString());
            Map<Integer, Map<String, String>> map = new HashMap();
            for (Object array : result){
                JSONObject element = JSONObject.parseObject(array.toString());
                // 获取索引
                Integer index = null;
                String interfaceName = "";
                String name = element.getString("name");
                String lastvalue = element.getString("lastvalue");
                String itemid = element.getString("itemid");
                if(name.contains("Interface") && name.contains("sent")
                        || name.contains("Interface") && name.contains("received")
                        || name.contains("Interface") && name.contains("Speed")){
                    int i = name.indexOf(" ") + 1;
                    int ii = name.indexOf(" ", i + 1);
                    String sequence = name.substring(i, ii);
                    index = Integer.parseInt(sequence);
                    int start = name.indexOf(" ", i + 1) + 1;
                    int last = name.indexOf(":");
                    System.out.println(name);
                    interfaceName = name.substring(start, last);
                }
                if(index != null){
                    Map eleMap = map.get(index);
                    if(eleMap == null){
                        eleMap = new HashMap();
                        map.put(index, eleMap);
                    }
                    if(StringUtils.isNotEmpty(interfaceName)){
                        eleMap.put("name", interfaceName);
                    }
                    if(name.contains("sent")){
//                        Object obj = this.getHistory(Arrays.asList(itemid), 10, time_till, time_from);
//                        JSONObject json = JSONObject.parseObject(obj.toString());
                        eleMap.put("sentItemId", itemid);
                    }
                    if(name.contains("received")){
//                        Object obj = this.getHistory(Arrays.asList(itemid), 10, time_till, time_from);
//                        JSONObject json = JSONObject.parseObject(obj.toString());
//                        eleMap.put("receivedHistory", json.get("result"));
                        eleMap.put("receivedItemId", itemid);
                    }
                    if(name.contains("Speed")){
                        eleMap.put("speed", lastvalue);
                    }
                }
            }
            if(!map.isEmpty()){
                List list = new ArrayList();
                for (Integer key : map.keySet()){
                    // 遍历查询history
                    if(map.get(key) != null){
                        JSONObject valueMap = JSONObject.parseObject(JSON.toJSONString(map.get(key)));
                        String sent = "";
                        String received = "";
                        for (String obj : valueMap.keySet()){
                            received = valueMap.getString("receivedItemId");
                            sent = valueMap.getString("sentItemId");
                        }
                        Object obj = this.getHistory(Arrays.asList(sent, received), null, time_till, time_from);
                        JSONObject json = JSONObject.parseObject(obj.toString());
                        valueMap.put("history", json.get("result"));
                        list.add(valueMap);
                    }
                }
                return list;
            }
            return map;
        }
        return null;
    }

    public Object parseItemDevice(Object object){
        JSONObject item = JSONObject.parseObject(object.toString());
        if(item.get("result") != null){
            JSONArray result = JSONArray.parseArray(item.get("result").toString());
            Map<Integer, Map<String, String>> map = new HashMap<Integer, Map<String, String>>();
            for (Object array : result){
                JSONObject element = JSONObject.parseObject(array.toString());
                // 获取索引
                Integer index = null;
                String name = element.getString("name");
                String ip = "";
                String mask = "";
                String status = "";
                String interfaceName = "";
                String description = "";
                String lastvalue = element.getString("lastvalue");
                if(name.contains("Interface") && name.contains("Description")){
                    int i = name.indexOf(" ") + 1;
                    int ii = name.indexOf(" ", i + 1);
                    String sequence = name.substring(i, ii);
                    index = Integer.parseInt(sequence);
                    int start = name.indexOf(" ", i + 1) + 1;
                    int last = name.indexOf(":");
                    System.out.println(name);
                    interfaceName = name.substring(start, last);
                    description = lastvalue;

                }
                if(name.contains("Operational status")){
                    int i = name.indexOf(" ") + 1;
                    int ii = name.indexOf(" ", i + 1);
                    String sequence = name.substring(i, ii);
                    index = Integer.parseInt(sequence);
                    switch (lastvalue){
                        case "1":
                            status = "up";
                            break;
                        case "2":
                            status = "down";
                            break;
                        default:
                            status = "unknown";
                    }
                }
                if(name.contains("Ipaddress") && name.contains("IPMASK")){
                    int i = name.indexOf(" ") + 1;
                    int ii = name.indexOf(" ", i + 1);
                    String sequence = name.substring(i, ii);
                    index = Integer.parseInt(sequence);

                    int start = name.indexOf(" ", i + 1) + 1;
                    int last = name.indexOf(":");
                    ip = name.substring(start, last).trim();
                    // 转换掩码位
                    if(StringUtils.isNotEmpty(lastvalue)){
                        mask = String.valueOf(this.ipV4Util.getMaskBitByMask(lastvalue));
                    }
                }
                if(index != null){
                    Map eleMap = map.get(index);
                    if(eleMap == null){
                        eleMap = new HashMap();
                        map.put(index, eleMap);
                    }
                    if(StringUtils.isNotEmpty(interfaceName)){
                        eleMap.put("name", interfaceName);
                    }
                    if(StringUtils.isNotEmpty(mask)){
                        eleMap.put("mask", mask);
                    }
                    if(StringUtils.isNotEmpty(ip)){
                        eleMap.put("ip", ip);
                    }
                    if(StringUtils.isNotEmpty(status)){
                        eleMap.put("status", status);
                    }
                    if(StringUtils.isNotEmpty(description)){
                        eleMap.put("description", description);
                    }
                }
            }
//            return map;
            if(!map.isEmpty()){
                List list = new ArrayList();
                for (Integer key : map.keySet()){
                    list.add(map.get(key));
                }
                return list;
            }
        }
        return null;
    }

    public class MapKeyComparator implements Comparable<Integer>{

        @Override
        public int compareTo(@NotNull Integer o) {

            return 0;
        }
    }


    public Object parseItemFlow(Object object, String portName){
        JSONObject item = JSONObject.parseObject(object.toString());
        if(item.get("result") != null){
            JSONArray result = JSONArray.parseArray(item.get("result").toString());
            Map<Integer, Map<String, String>> map = new HashMap<Integer, Map<String, String>>();
            for (Object array : result){
                JSONObject element = JSONObject.parseObject(array.toString());
                // 获取索引
                Integer index = null;
                String name = element.getString("name");
                System.out.println("name: " + name + " portName: " + portName);
               if(name.contains(portName)){
                   String interfaceName = "";
                   Long speed = null;
                   String status = "";
                   String lastvalue = element.getString("lastvalue");
                   if(name.contains("Interface") && name.contains("received") || name.contains("sent") || name.contains("Speed")){
                       int i = name.indexOf(" ") + 1;
                       int ii = name.indexOf(" ", i + 1);
                       String sequence = name.substring(i, ii);
                       try {
                           index = Integer.parseInt(sequence);
                       } catch (NumberFormatException e) {
                           e.printStackTrace();
                           index = null;
                       }
                       int start = name.indexOf(" ", i + 1) + 1;
                       int last = name.indexOf(":");
                       interfaceName = name.substring(start, last);
                       if(name.contains("Speed")){
                           speed = element.getLong("lastvalue");
                       }
                   }

                   if(name.contains("Operational status")){
                       int i = name.indexOf(" ") + 1;
                       int ii = name.indexOf(" ", i + 1);
                       String sequence = name.substring(i, ii);
                       index = Integer.parseInt(sequence);
                       switch (lastvalue){
                           case "1":
                               status = "up";
                               break;
                           case "2":
                               status = "down";
                               break;
                           default:
                               status = "unknown";
                       }
                   }

                   if(index != null){
                       Map eleMap = map.get(index);
                       if(eleMap == null){
                           eleMap = new HashMap();
                           map.put(index, eleMap);
                       }
                       Integer i = null;
                       if(name.contains("received")){
                           i = 1;
                       }
                       if(name.contains("sent")){
                           i = 2;
                       }
                       if(i != null && StringUtils.isNotEmpty(lastvalue)){
                           eleMap.put(i != null && i == 1 ? "received" : "sent", lastvalue);
                       }
                       if(StringUtils.isNotEmpty(interfaceName)){
                           eleMap.put("name", interfaceName);
                       }
                       if(null != speed){
                           eleMap.put("speed", speed);
                       }
                       if(StringUtils.isNotEmpty(status)){
                           eleMap.put("status", status);
                       }
                   }
               }
            }
            return map;
        }
        return null;
    }


    public Object getHistory(List itemid, Integer limit){
        HistoryDTO dto = new HistoryDTO();
        dto.setItemids(itemid);
        dto.setLimit(limit);
        Object historys = this.historyService.getHistory(dto);
//        JSONObject json = JSONObject.parseObject(historys.toString());
//        return json.get("result");
        return historys;
    }

    public Object getHistory(List itemid, Integer limit, Long time_till, Long time_from){
        HistoryDTO dto = new HistoryDTO();
        dto.setItemids(itemid);
        dto.setLimit(limit);
        dto.setTime_from(time_from);
        dto.setTime_till(time_till);
        Object historys = this.historyService.getHistory(dto);
//        JSONObject json = JSONObject.parseObject(historys.toString());
//        return json.get("result");
        return historys;
    }

    public int verifyThresholdValue(Map data){
        int lastValue = 0;
        for (Object key : data.keySet()){
            if(key.equals("CpuUsage") || key.equals("MemUsage")){
                if(Integer.parseInt(data.get(key).toString()) >= 10){
                    lastValue = 1;
                }
            }
        }

        return lastValue;
    }
}
