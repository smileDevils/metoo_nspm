package com.cloud.tv.core.mapper;

import com.cloud.tv.entity.Credential;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CredentialMaaper {

    Credential getObjById(Long id);

    Credential getObjByName(String name);

    List<Credential> query();

    int save(Credential instance);

    int update(Credential instance);

    int delete(Long id);

    Page<Credential> getObjsByLevel(Credential instance);
}
