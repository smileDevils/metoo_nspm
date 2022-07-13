package com.cloud.tv.core.mapper;

import com.cloud.tv.entity.ProgramPlayback;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProgramPlaybackMapper {

    /**
     * 保存一个ProgramPlayback对象
     * @param instance
     * @return
     */
    int insert(ProgramPlayback instance);


}
