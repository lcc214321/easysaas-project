package org.easymis.easysaas.gateway.service.impl;

import org.easymis.easysaas.gateway.config.datasource.DataSourceType;
import org.easymis.easysaas.gateway.config.datasource.EasymisDataSource;
import org.easymis.easysaas.gateway.entitys.mybatis.dto.Resource;
import org.easymis.easysaas.gateway.entitys.mybatis.mapper.ResourceMapper;
import org.easymis.easysaas.gateway.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	ResourceMapper mapper;
	@EasymisDataSource(DataSourceType.Master)
	public Resource getOne(String requestURI) {
		// TODO Auto-generated method stub
		return null;
	}

	@EasymisDataSource(DataSourceType.Master)
	public Resource findByEndPoint(String endPoint) {
		// TODO Auto-generated method stub
		return mapper.findByEndPoint(endPoint);
	}

}
