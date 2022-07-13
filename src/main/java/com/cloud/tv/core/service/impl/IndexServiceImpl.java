package com.cloud.tv.core.service.impl;

import com.cloud.tv.core.mapper.IndexMapper;
import com.cloud.tv.core.service.IIndexService;
import com.cloud.tv.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IndexServiceImpl implements IIndexService {

    @Autowired
    private IndexMapper indexMapper;

    @Override
    public List<MenuVo> findMenu(Long id) {
        return indexMapper.findMenu(id);
    }
}
