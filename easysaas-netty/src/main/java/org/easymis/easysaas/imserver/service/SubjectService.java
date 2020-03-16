package org.easymis.easysaas.imserver.service;

import java.util.List;

import org.easymis.easysaas.common.result.RestResult;
import org.easymis.easysaas.imserver.entitys.mybatis.dto.Subject;

import com.github.pagehelper.PageInfo;

public interface SubjectService {
	public boolean save(Subject bean);

	public boolean update(Subject bean);

	public Subject findById(String id);

	List findByOrgId(String orgId);

	public PageInfo findByOrgId(String orgId, Integer pageNum, Integer pageSize);

	public RestResult deleteByIds(String ids);
}
