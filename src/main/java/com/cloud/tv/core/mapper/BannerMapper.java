package com.cloud.tv.core.mapper;

import com.cloud.tv.dto.BannerDto;
import com.cloud.tv.entity.Banner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BannerMapper {

    Banner findObjById(Long id);

    List<Banner> findObjByMap(Map params);


    List<Banner> query(BannerDto dto);

    boolean save(Banner dto);

    boolean update(Banner dto);

    boolean delete(Long id);
}
