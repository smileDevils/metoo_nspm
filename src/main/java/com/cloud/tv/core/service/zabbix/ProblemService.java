package com.cloud.tv.core.service.zabbix;

import com.cloud.tv.dto.zabbix.ProblemDTO;

public interface ProblemService {

    Object get(ProblemDTO dto);
}
