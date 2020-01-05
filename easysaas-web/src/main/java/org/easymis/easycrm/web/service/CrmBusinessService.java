package org.easymis.easycrm.web.service;

import java.util.List;

import org.easymis.easycrm.web.entitys.mybatis.dto.CrmBusiness;
import org.easymis.easycrm.web.entitys.mybatis.vo.CrmBusinessVo;

import com.github.pagehelper.PageInfo;

public interface CrmBusinessService {
	CrmBusiness findById(String id);
	List findByList(CrmBusinessVo vo);
	PageInfo findByPage(CrmBusinessVo vo);
	Boolean save(CrmBusiness bean);
	Boolean update(CrmBusiness bean);	
	Boolean delete(List<String> ids);	

}
