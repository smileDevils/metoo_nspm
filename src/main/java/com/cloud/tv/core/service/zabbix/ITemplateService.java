package com.cloud.tv.core.service.zabbix;

import com.cloud.tv.dto.zabbix.TemplateDTO;

public interface ITemplateService {

    Object getTemplate(TemplateDTO dto);
}
