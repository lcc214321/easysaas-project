package org.easymis.easysaas.crm.service.impl;

import java.util.HashMap;
import java.util.List;

import org.easymis.easysaas.common.result.RestResult;
import org.easymis.easysaas.crm.common.FormTypeEnum;
import org.easymis.easysaas.crm.entitys.dto.CrmField;
import org.easymis.easysaas.crm.entitys.dto.CrmFieldSort;
import org.easymis.easysaas.crm.entitys.vo.ColumnHeadVo;
import org.easymis.easysaas.crm.entitys.vo.CrmFieldVo;
import org.easymis.easysaas.crm.mapper.CrmFieldMapper;
import org.easymis.easysaas.crm.mapper.CrmFieldSortMapper;
import org.easymis.easysaas.crm.service.CrmFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CrmFieldServiceImpl implements CrmFieldService{
	@Autowired
	private CrmFieldMapper mapper;
	@Autowired
	private CrmFieldSortMapper fieldSortMapper;

	@Override
	public List<CrmField> list(String orgId,String label, String categoryId) {
		CrmField crmField = new CrmField();
		crmField.setOrgId(orgId);
		crmField.setLabel(label);
        List<CrmField> recordList = mapper.getList(crmField);
        fieldToFormType(recordList);
        if (categoryId == null) {
            return recordList;
        }
        else
        	return null;
/*        FieldUtil fieldUtil = new FieldUtil(recordList);
        return fieldUtil.getRecordList();*/
    }
	@Override
	public boolean save(CrmField bean) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(CrmField bean) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CrmField findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo findByOrgId(String orgId,Integer pageNum, Integer pageSize) {
    	PageHelper.startPage(pageNum, pageSize);
    	List<CrmField> fieldList = mapper.findByOrgId(orgId);
		PageInfo<CrmField> p = new PageInfo<CrmField>(fieldList);
        return p;
	}

	@Override
	public RestResult deleteByIds(String ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ColumnHeadVo> queryListHead(CrmFieldSort fieldSort) {
        //查看userid是否存在于顺序表，没有则插入
        String staffId = "3";//BaseUtil.getMember().getMemberId();
        String orgId="orgId";//BaseUtil.getOrgId();
        Integer number = fieldSortMapper.getCount(orgId,staffId, fieldSort.getLabel());
        if (0 == number) {
/*            List<Record> fieldList;
            if (adminFieldSort.getLabel() == CrmEnum.CRM_CUSTOMER_POOL.getType()){
                fieldList = list(CrmEnum.CRM_CUSTOMER.getType());
            }else {
                fieldList = list(adminFieldSort.getLabel());
            }
            List<AdminFieldSort> sortList = new LinkedList<>();
            FieldUtil fieldUtil = new FieldUtil(sortList, userId, adminFieldSort.getLabel());
            if (null != fieldList) {
                for (Record record : fieldList) {
                    fieldUtil.add(record.getStr("field_name"), record.getStr("name"), record.getInt("type"), record.getInt("field_id"));
                }
            }
            if (CrmEnum.CRM_CUSTOMER.getType() == adminFieldSort.getLabel()){
                fieldUtil.add("dealStatus", "成交状态", 3)
                        .add("poolDay", "距进入公海客户天数", 5)
                        .add("lastTime", "最后跟进时间", 4)
                        .add("lastContent","最后跟进记录",1);
            }else if (CrmEnum.CRM_BUSINESS.getType() == adminFieldSort.getLabel()){
                fieldUtil.add("typeName", "商机状态组", 3).add("statusName", "商机阶段", 3);
            }else if (CrmEnum.CRM_CONTRACT.getType() == adminFieldSort.getLabel()){
                fieldUtil.add("receivedMoney","已收款金额",6).add("unreceivedMoney","未收款金额",6);
            }else if (CrmEnum.CRM_RECEIVABLES.getType() == adminFieldSort.getLabel()){
                fieldUtil.add("contractMoney","合同金额",6);
            }else if (CrmEnum.CRM_CUSTOMER_POOL.getType() == adminFieldSort.getLabel()){
                fieldUtil.add("dealStatus","成交状态",3)
                        .add("lastContent","最后跟进记录",1);
            }else if(CrmEnum.CRM_LEADS.getType() == adminFieldSort.getLabel()){
                fieldUtil.add("lastContent","最后跟进记录",1);
            }
            fieldUtil.add("updateTime", "更新时间",4).add("createTime", "创建时间",4)
                    .add("ownerUserName", "负责人",1).add("createUserName", "创建人",1);
            fieldUtil.getAdminFieldSortList().forEach(fieldSort -> {
                String fieldName = StrUtil.toCamelCase(fieldSort.getFieldName());
                fieldSort.setFieldName(fieldName);
                if ("customerId".equals(fieldSort.getFieldName())){
                    fieldSort.setFieldName("customerName");
                } else if ("categoryId".equals(fieldSort.getFieldName())){
                    fieldSort.setFieldName("categoryName");
                } else if ("contactsId".equals(fieldSort.getFieldName())){
                    fieldSort.setFieldName("contactsName");
                } else if ("companyUserId".equals(fieldSort.getFieldName())){
                    fieldSort.setFieldName("companyUserName");
                } else if ("businessId".equals(fieldSort.getFieldName())){
                    fieldSort.setFieldName("businessName");
                } else if ("contractId".equals(fieldSort.getFieldName())){
                    fieldSort.setFieldName("contractNum");
                } else if ("planId".equals(fieldSort.getFieldName())){
                    fieldSort.setFieldName("planNum");
                }
            });
            sortList = fieldUtil.getAdminFieldSortList();
            for (int i = 0; i < sortList.size(); i++) {
                AdminFieldSort newUserFieldSort = sortList.get(i);
                newUserFieldSort.setSort(i).save();
            }*/
        }

        List<ColumnHeadVo> recordList = mapper.getColumnHead(orgId,fieldSort.getLabel(), staffId);
        //List<ColumnHeadVo> recordList = Db.findByCache("field", "listHead:" + adminFieldSort.getLabel() + userId, Db.getSql("admin.field.queryListHead"), adminFieldSort.getLabel(), userId);
       // recordToFormType(recordList);
        return recordList;
	}

	@Override
	public RestResult queryFieldConfig(String orgId,String staffId,CrmFieldSort crmFieldSort) {
        //查出自定义字段，查看顺序表是否存在该字段，没有则插入，设为隐藏
        List<CrmFieldVo> fieldList = customFieldList(orgId,crmFieldSort.getLabel());
        for (CrmFieldVo record : fieldList) {
            String fieldName = record.getName();
            
            Integer number = fieldSortMapper.getNumber(staffId, crmFieldSort.getLabel(), fieldName);
			if (number.equals(0)) {
				CrmFieldSort newField = new CrmFieldSort();
				newField.setFieldName(fieldName);
				newField.setName(fieldName);
				newField.setLabel(crmFieldSort.getLabel());
				newField.setIsHide(1);
				newField.setStaffId(staffId);
				newField.setSort(1);
				fieldSortMapper.save(newField);
			}
        }
        
        List<HashMap> noHideList = fieldSortMapper.findNoHideList( crmFieldSort.getLabel(), staffId);
        List<HashMap> hideList = fieldSortMapper.findHideList(crmFieldSort.getLabel(), staffId);
        
        HashMap hashMap= new HashMap();
        hashMap.put("value", noHideList);
        hashMap.put("hide_value", hideList);        
        return RestResult.buildSuccess(hashMap);
    }

	@Override
	public RestResult fieldConfig(String orgId,String staffId,CrmFieldSort crmFieldSort) {
        String[] sortArr = crmFieldSort.getNoHideIds().split(",");
        if (sortArr.length < 2) {
        	return RestResult.buildError("至少显示2列");
        }
        for (int i = 0; i < sortArr.length; i++) {
        	CrmFieldSort bean= new CrmFieldSort();
        	bean.setSort(i + 1);
        	bean.setLabel(crmFieldSort.getLabel());
        	bean.setStaffId(staffId);
        	bean.setId(sortArr[i]);
        	fieldSortMapper.updateNoHide(bean);
        }
        if (null != crmFieldSort.getHideIds()) {
            String[] hideIdsArr = crmFieldSort.getHideIds().split(",");
            for (int i = 0; i < hideIdsArr.length; i++) {
                CrmFieldSort bean= new CrmFieldSort();
                bean.setLabel(crmFieldSort.getLabel());
                bean.setStaffId(staffId);
                bean.setId(hideIdsArr[i]);
                fieldSortMapper.updateHide(bean);
            }
        }
        //CaffeineCache.ME.remove("field", "listHead:" + adminFieldSort.getLabel() + userId);
        return RestResult.buildSuccess();
    }
	/**
    * 查询fieldType为0的字段
    */
   public List<CrmFieldVo> customFieldList(String orgId,String label){
       List <CrmFieldVo> recordList = mapper.customerFieldList(orgId,label);
       recordToFormType(recordList);
       return recordList;
   }
   public void recordToFormType(List<CrmFieldVo> recordList) {
       for (CrmFieldVo record : recordList) {
           Integer dataType = record.getFieldType();
           FormTypeEnum typeEnum = FormTypeEnum.parse(dataType);
           record.setFormType(typeEnum.getFormType());
           if(dataType == FormTypeEnum.CHECKBOX.getType()){
               recordValueToArray(record);
           }else if(dataType == FormTypeEnum.USER.getType()){
               //record.set("default_value", new ArrayList<>(0));
           }else if(dataType == FormTypeEnum.STRUCTURE.getType()){
              // record.set("default_value", new ArrayList<>(0));
           }
           if (FormTypeEnum.SELECT.getType() == dataType || FormTypeEnum.CHECKBOX.getType() == dataType) {
               if (record.getOptions() != null) {
                  // record.set("setting", record.getOptions().split(","));
               }
           } else {
              // record.set("setting", new String[]{});
           }
       }
   }
   public void fieldToFormType(List<CrmField> recordList) {
       for (CrmField record : recordList) {
           Integer dataType = record.getFieldType();
           FormTypeEnum typeEnum = FormTypeEnum.parse(dataType);
           record.setFormType(typeEnum.getFormType());
           if(dataType == FormTypeEnum.CHECKBOX.getType()){
        	   fieldValueToArray(record);
           }else if(dataType == FormTypeEnum.USER.getType()){
               //record.set("default_value", new ArrayList<>(0));
           }else if(dataType == FormTypeEnum.STRUCTURE.getType()){
              // record.set("default_value", new ArrayList<>(0));
           }
           if (FormTypeEnum.SELECT.getType() == dataType || FormTypeEnum.CHECKBOX.getType() == dataType) {
               if (record.getOptions() != null) {
                  // record.set("setting", record.getOptions().split(","));
               }
           } else {
              // record.set("setting", new String[]{});
           }
       }
   }
   private void recordValueToArray(CrmFieldVo record) {
       //record.set("default_value", StrUtil.isNotEmpty(record.get("default_value")) ? record.getStr("default_value").split(",") : new String[]{});
      // record.set("value", StrUtil.isNotEmpty(record.getStr("value")) ? record.getStr("value").split(",") : new String[]{});
   }
   private void fieldValueToArray(CrmField record) {
       //record.set("default_value", StrUtil.isNotEmpty(record.get("default_value")) ? record.getStr("default_value").split(",") : new String[]{});
      // record.set("value", StrUtil.isNotEmpty(record.getStr("value")) ? record.getStr("value").split(",") : new String[]{});
   }

}
