package org.easymis.easysaas.member.service.impl;

import java.util.List;

import org.easymis.easysaas.member.config.datasource.DataSourceType;
import org.easymis.easysaas.member.config.datasource.EasymisDataSource;
import org.easymis.easysaas.member.entitys.mybatis.dto.RoleResource;
import org.easymis.easysaas.member.entitys.mybatis.mapper.RoleResourceMapper;
import org.easymis.easysaas.member.service.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RoleResourceServiceImpl implements RoleResourceService {
	@Autowired
	RoleResourceMapper mapper;
	@EasymisDataSource(DataSourceType.Master)
	public List<RoleResource> list(String resourceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@EasymisDataSource(DataSourceType.Master)
	public List<RoleResource> findByResourceId(String resourceId) {
		// TODO Auto-generated method stub
		return mapper.findByResourceId(resourceId);
	}

}
