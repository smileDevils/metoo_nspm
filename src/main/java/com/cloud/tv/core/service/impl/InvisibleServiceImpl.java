package com.cloud.tv.core.service.impl;

import com.cloud.tv.core.mapper.InvisibleMapper;
import com.cloud.tv.core.service.InvisibleService;
import com.cloud.tv.entity.Invisible;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InvisibleServiceImpl implements InvisibleService {

    @Autowired
    private InvisibleMapper invisibleMapper;

    @Override
    public int update(Invisible instance) {
        return this.invisibleMapper.update(instance);
    }

    @Override
    public String getName() {
        List<Invisible> invisibles = this.invisibleMapper.query();
        if(invisibles.size() > 0){
            Invisible invisible = invisibles.get(0);
            return invisible.getName();
        }
        return null;
    }
}
