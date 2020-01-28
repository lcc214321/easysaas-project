package org.easymis.easysaas.portal.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.easymis.easysaas.common.result.SearchResult;
import org.easymis.easysaas.portal.entitys.mybatis.dto.CompanyLicense;
import org.easymis.easysaas.portal.service.CompanyLicenseEntpubService;
import org.easymis.easysaas.portal.service.CompanyLicenseInfoCreditchinaService;
import org.easymis.easysaas.portal.service.CompanyLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "历史行政许可【信用中国】")
@RequestMapping("licenseInfoCreditchina")
@RestController
@Validated
public class LicenseCreditchinaController {


    @Autowired
    private CompanyLicenseInfoCreditchinaService licenseInfoCreditchinaService;

    @Autowired
    private CompanyLicenseEntpubService companyLicenseEntpubService;

    @Autowired
    private CompanyLicenseService companyLicenseService;


    @ApiModelProperty("行政许可")
    @GetMapping("queryLicenseInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "企业id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "companyName", value = "企业名称", required = true, dataType = "string"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = false, dataType = "string"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, dataType = "string")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功", response = CompanyLicense.class)
    })
	public SearchResult getLicenseInfo(@NotNull Long companyId, String companyName, Integer pageNum, Integer pageSize) {
		pageNum = Objects.isNull(pageNum) ? 1 : pageNum;
		pageSize = Objects.isNull(pageSize) ? 10 : pageSize;
		Page page = new Page(pageNum, pageSize);
		LicenseInfoDTO dto = new LicenseInfoDTO();
		PageInfo licensePageInfo = companyLicenseService.getPage(page, companyId);
		List<CompanyLicense> licenseList = licensePageInfo.getList();
		PageInfo creditchinaPageInfo = licenseInfoCreditchinaService.getPage(page, companyName);
		List<CompanyLicenseInfoCreditchina> infoCreditchinaList = creditchinaPageInfo.getList();
		dto.setCompanyLicense(licenseList).setLicenseInfoCreditchinas(infoCreditchinaList);
		return SearchResult.buildSuccess(dto);
	}

}
