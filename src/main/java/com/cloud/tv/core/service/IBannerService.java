package com.cloud.tv.core.service;

import com.cloud.tv.dto.BannerDto;
import com.cloud.tv.entity.Banner;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface IBannerService {

    Banner findObjById(Long id);

    List<Banner> findObjByMap(Map params);

    Page<Banner> query(BannerDto dto);

    boolean save(BannerDto dto);

    boolean update(BannerDto dto);

    boolean delete(Long id);
}
