package org.easymis.easycrm.operation.security.service.impl;

import java.util.List;

import org.easymis.easycrm.operation.entitys.mybatis.mapper.RoleResourceMapper;
import org.easymis.easycrm.operation.security.service.RoleResourceService;
import org.easymis.easycrm.operation.security.userdetail.RoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RoleResourceServiceImpl implements RoleResourceService {
	@Autowired
	RoleResourceMapper mapper;
	@Override
	public List<RoleResource> list(String resourceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleResource> findByResourceId(String resourceId) {
		// TODO Auto-generated method stub
		return mapper.findByResourceId(resourceId);
	}

}
