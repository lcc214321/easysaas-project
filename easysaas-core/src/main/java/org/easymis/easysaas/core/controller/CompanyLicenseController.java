package org.easymis.easysaas.core.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(description = "行政许可")
@Validated
@RequestMapping("/companyLicense")
@RestController
public class CompanyLicenseController {

}
