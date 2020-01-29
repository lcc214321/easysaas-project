package org.easymis.easysaas.portal.service;

import java.util.List;

import org.easymis.easysaas.portal.entitys.mybatis.dto.CompanyHuman;
import org.easymis.easysaas.portal.entitys.mybatis.dto.Human;



public interface HumanService extends IService<CompanyHuman>{
	List findByHumanInvestorIds(List humaninvestorIds);
	Human getById(String staffId);
	 List<Human> list(List<String> humaninvestorIds);
}
