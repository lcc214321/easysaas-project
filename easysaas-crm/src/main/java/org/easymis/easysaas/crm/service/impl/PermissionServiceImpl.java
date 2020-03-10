package org.easymis.easysaas.crm.service.impl;

import java.util.List;

import org.easymis.easysaas.crm.config.datasource.DataSourceType;
import org.easymis.easysaas.crm.config.datasource.EasymisDataSource;
import org.easymis.easysaas.crm.entitys.dto.Permission;
import org.easymis.easysaas.crm.mapper.PermissionMapper;
import org.easymis.easysaas.crm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	PermissionMapper mapper;
	@EasymisDataSource(DataSourceType.Master)
	public Permission getOne(String requestURI) {
		// TODO Auto-generated method stub
		return null;
	}

	@EasymisDataSource(DataSourceType.Master)
	public Permission findByEndPoint(String endPoint) {
		// TODO Auto-generated method stub
		return mapper.findByEndPoint(endPoint);
	}

	@Override
	public List<Permission> findByMemberId(String memberId) {
		// TODO Auto-generated method stub
		return mapper.findByMemberId(memberId);
	}

}
