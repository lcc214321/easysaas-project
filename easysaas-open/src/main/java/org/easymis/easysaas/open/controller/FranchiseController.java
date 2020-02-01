package org.easymis.easysaas.open.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(description = "商业特许经营")
@RequestMapping("franchise")
@RestController
@Validated
public class FranchiseController {

}